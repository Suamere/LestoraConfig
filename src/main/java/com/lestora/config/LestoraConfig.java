package com.lestora.config;

import net.minecraft.resources.ResourceLocation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

public class LestoraConfig {
    static boolean isServerAuthoritative;
    static final Map<RLAmount, Integer> lightLevelsMap = new ConcurrentHashMap<>();

    static final Map<ResourceLocation, Integer> minCache = new ConcurrentHashMap<>();
    static final Map<ResourceLocation, Integer> maxCache = new ConcurrentHashMap<>();

    public static Integer getLightLevel(ResourceLocation rl, int amount) {
        return lightLevelsMap.get(new RLAmount(rl, amount));
    }

    public static Integer getMinLightLevel(ResourceLocation rl) {
        cacheMinAndMax(rl);
        return minCache.get(rl);
    }

    public static Integer getMaxLightLevel(ResourceLocation rl) {
        cacheMinAndMax(rl);
        return maxCache.get(rl);
    }

    private static void cacheMinAndMax(ResourceLocation rl) {
        if (minCache.containsKey(rl) && maxCache.containsKey(rl)) {
            return;
        }

        Integer min = null;
        Integer max = null;
        for (Map.Entry<RLAmount, Integer> entry : lightLevelsMap.entrySet()) {
            RLAmount key = entry.getKey();
            if (key.getResource().equals(rl)) {
                int lightLevel = entry.getValue();
                if (min == null || lightLevel < min) {
                    min = lightLevel;
                }
                if (max == null || lightLevel > max) {
                    max = lightLevel;
                }
            }
        }
        if (min != null) {
            minCache.put(rl, min);
        }
        if (max != null) {
            maxCache.put(rl, max);
        }
    }

    public static Map<RLAmount, Integer> getLightLevels() {
        return new HashMap<>(lightLevelsMap);
    }

    static void setLightLevelsMap(List<String> stringMap, String from) {
        if (from.equals("CLIENT") && isServerAuthoritative) return;
        else if (from.equals("SERVER")) isServerAuthoritative = true;

        minCache.clear();
        maxCache.clear();
        lightLevelsMap.clear();
        for (String entry : stringMap) {
            String[] split = entry.split("=");
            if (split.length == 2) {
                String itemId = split[0].trim();
                try {
                    int level = Integer.parseInt(split[1].trim());
                    int amount = 1;
                    if (itemId.contains("(")) {
                        int start = itemId.indexOf('(');
                        int end = itemId.indexOf(')', start);
                        amount = Integer.parseInt(itemId.substring(start + 1, end));
                        itemId = itemId.substring(0, start);
                    }
                    ResourceLocation loc = ResourceLocation.tryParse(itemId);
                    if (loc != null) {
                        lightLevelsMap.put(new RLAmount(loc, amount), level);
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