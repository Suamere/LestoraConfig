package com.lestora.config;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BiomeConfig {
    static boolean isServerAuthoritative;
    static final Map<ResourceLocation, Float> biomeTempMap = new HashMap<>();
    static final Map<ResourceLocation, Float> biomeTempUniqueMap = new HashMap<>();
    static final Map<ResourceLocation, Float> defaultBiomeTemps = new HashMap<>();

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    static final List<BiomeChangeListener> listenerSub = new ArrayList<>();
    static final List<Runnable> fullSub = new ArrayList<>();
    static boolean firstLoad = false;

    static {
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "end_barrens"), -1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "end_midlands"), -1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "the_end"), -1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "small_end_islands"), -1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "end_highlands"), 0.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "the_void"), -1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "ice_spikes"), -1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "frozen_peaks"), -1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "deep_frozen_ocean"), -1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "frozen_ocean"), -0.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "jagged_peaks"), -0.7f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "snowy_taiga"), -0.7f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "frozen_river"), -0.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "snowy_slopes"), -0.7f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "snowy_plains"), -0.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "deep_cold_ocean"), -0.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "snowy_beach"), -0.25f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "grove"), -0.2f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "cold_ocean"), -0.2f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "old_growth_spruce_taiga"), 0.25f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "taiga"), 0.25f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "old_growth_pine_taiga"), 0.3f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "lush_caves"), 0.4f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "dark_forest"), 0.4f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "pale_garden"), 0.4f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "cherry_grove"), 0.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "deep_ocean"), 0.3f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "meadow"), 0.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "ocean"), 0.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "river"), 0.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "warm_ocean"), 0.7f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "birch_forest"), 0.6f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "old_growth_birch_forest"), 0.6f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "deep_lukewarm_ocean"), 0.7f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "lukewarm_ocean"), 0.6f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "flower_forest"), 0.7f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "forest"), 0.7f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "beach"), 0.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "dripstone_caves"), 0.4f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "mangrove_swamp"), 0.9f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "plains"), 0.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "sunflower_plains"), 0.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "mushroom_fields"), 0.9f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "stony_peaks"), 1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "deep_dark"), 0.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "bamboo_jungle"), 1.1f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "sparse_jungle"), 1.1f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "swamp"), 1.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "jungle"), 1.2f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "eroded_badlands"), 1.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "savanna"), 1.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "savanna_plateau"), 1.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "windswept_savanna"), 1.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "wooded_badlands"), 1.5f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "badlands"), 1.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "desert"), 1.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "basalt_deltas"), 2.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "crimson_forest"), 1.8f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "nether_wastes"), 2.0f);
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "soul_sand_valley"), -0.5f); // Odd choice, but let's make it cold in a Soul Sand Valley?  Lol
        defaultBiomeTemps.put(ResourceLocation.fromNamespaceAndPath("minecraft", "warped_forest"), 1.8f);
    }

    public static boolean configFirstLoaded() {
        return firstLoad;
    }

    public static void subscribe(BiomeChangeListener listener) {
        listenerSub.add(listener);
    }

    public static void subscribe(Runnable onTemperatureChanged) {
        fullSub.add(onTemperatureChanged);
    }

    public static Map<ResourceLocation, Float> getUniqueBiomeTemps() { return new HashMap<>(biomeTempUniqueMap); }
    public static Map<ResourceLocation, Float> getDefaultBiomeTemps() { return new HashMap<>(defaultBiomeTemps); }
    public static Map<ResourceLocation, Float> getBiomeTemps() { return new HashMap<>(biomeTempMap); }

    public static Float getBiomeTemp(ResourceLocation rl) {
        lock.readLock().lock();
        try {
            return biomeTempMap.get(rl);
        } finally {
            lock.readLock().unlock();
        }
    }

    public static Float getUniqueBiomeTemp(ResourceLocation rl) {
        lock.readLock().lock();
        try {
            return biomeTempUniqueMap.get(rl);
        } finally {
            lock.readLock().unlock();
        }
    }

    static void setBiomeTempsMap(List<String> stringMap, String from) {
        lock.writeLock().lock();
        try {
            if (from.equals("CLIENT") && isServerAuthoritative) return;
            else if (from.equals("SERVER")) isServerAuthoritative = true;

            var changed = false;

            // Parse new values into a temporary map.
            Map<ResourceLocation, Float> newMap = new HashMap<>();
            for (String entry : stringMap) {
                String[] split = entry.split("=");
                if (split.length == 2) {
                    String itemId = split[0].trim();

                    float temperature;
                    try {
                        temperature = Float.parseFloat(split[1].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Failed to parse Biome Temperature for entry: " + entry);
                        continue;
                    }

                    ResourceLocation loc = ResourceLocation.tryParse(itemId);
                    if (loc == null) {
                        System.err.println("Invalid Biome ResourceLocation: " + itemId);
                        continue;
                    }

                    newMap.put(loc, temperature);
                } else {
                    System.err.println("Entry is not in expected 'key = value' format: " + entry);
                }
            }

            // Process additions and updates.
            for (var newEntry : newMap.entrySet()) {
                ResourceLocation key = newEntry.getKey();
                float newValue = newEntry.getValue();
                if (!biomeTempMap.containsKey(key)) {
                    biomeTempMap.put(key, newValue);
                    listenerSub.forEach(sub -> sub.onChange(key, null, newValue));
                    changed = true;
                } else {
                    float oldValue = biomeTempMap.get(key);
                    if (oldValue != newValue) {
                        biomeTempMap.put(key, newValue);
                        listenerSub.forEach(sub -> sub.onChange(key, oldValue, newValue));
                        changed = true;
                    }
                }

                if (!biomeTempUniqueMap.containsKey(key)) {
                    if (isDefault(key, newValue))
                        biomeTempUniqueMap.remove(key);
                    else
                        biomeTempUniqueMap.put(key, newValue);
                } else {
                    float oldValue = biomeTempUniqueMap.get(key);
                    if (oldValue != newValue)
                        biomeTempUniqueMap.put(key, newValue);
                }
            }

            // Process removals.
            var it = biomeTempMap.keySet().iterator();
            while (it.hasNext()) {
                ResourceLocation key = it.next();
                if (!newMap.containsKey(key)) {
                    float oldValue = biomeTempMap.get(key);
                    it.remove();
                    biomeTempUniqueMap.remove(key);
                    listenerSub.forEach(sub -> sub.onChange(key, oldValue, null));
                    changed = true;
                }
            }

            if (changed)
                fullSub.forEach(Runnable::run);

            firstLoad = true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    static boolean isDefault(ResourceLocation key, Float val) {
        var defaultVal = defaultBiomeTemps.get(key);
        return defaultVal != null && defaultVal.equals(val);
    }

    public static void setServerAuthoritative(boolean b) {
        isServerAuthoritative = b;
    }
}