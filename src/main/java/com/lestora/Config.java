package com.lestora;

import net.minecraft.resources.ResourceLocation;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

/**
 * Shared configuration for light levels.
 * <p>
 * This class holds a static map of light levels that both client and server can access.
 * The server is authoritative and pushes updates via packets.
 * </p>
 */
public class Config {
    private static boolean isServerAuthoritative;
    // Use ConcurrentHashMap for thread safety when updating or reading the map concurrently.
    private static final Map<ResourceLocation, Integer> lightLevelsMap = new ConcurrentHashMap<>();

    /**
     * Retrieves the light level for the given ResourceLocation.
     *
     * @param rl the resource location key
     * @return the light level, or null if not present
     */
    public static Integer getLightLevel(ResourceLocation rl) {
        return lightLevelsMap.get(rl);
    }

    /**
     * Returns a defensive copy of the light levels map with string keys.
     *
     * @return a new map where keys are ResourceLocation strings and values are light levels
     */
    public static Map<String, Integer> getLightLevels() {
        Map<String, Integer> stringMap = new HashMap<>();
        for (Map.Entry<ResourceLocation, Integer> entry : lightLevelsMap.entrySet()) {
            stringMap.put(entry.getKey().toString(), entry.getValue());
        }
        return stringMap;
    }

    /**
     * Updates the light levels map based on a list of strings.
     * Each string should be in the format "resourceLocation = integer".
     * Entries that fail parsing or have invalid resource locations are skipped.
     *
     * @param stringMap a list of strings representing the mapping
     */
    public static void setLightLevelsMap(List<String> stringMap, String from) {
        if (from.equals("CLIENT") && isServerAuthoritative){
            System.err.println("Tried to update config from Client, but server is authoritative.");
        }
        else if (from.equals("SERVER")){
            System.err.println("\u001B[34m" + "Server is Authoritative" + "\u001B[0m");
            isServerAuthoritative = true;
        }
        else if (from.equals("CLIENT")){
            System.err.println("\u001B[34m" + "Client is Authoritative" + "\u001B[0m");
        }
        else {
            System.err.println("\u001B[34m" + "'DAFUQ' is Authoritative" + "\u001B[0m");
        }

        // Clear current settings before applying new ones.
        lightLevelsMap.clear();
        for (String entry : stringMap) {
            // Expecting each entry to be in "key = value" format.
            String[] split = entry.split("=");
            if (split.length == 2) {
                String itemId = split[0].trim();
                try {
                    int level = Integer.parseInt(split[1].trim());
                    ResourceLocation loc = ResourceLocation.tryParse(itemId);
                    if (loc != null) {
                        lightLevelsMap.put(loc, level);
                    } else {
                        System.err.println("Invalid ResourceLocation: " + itemId);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse light level for entry: " + entry);
                }
            } else {
                System.err.println("Entry is not in expected 'key = value' format: " + entry);
            }
        }
    }
}
