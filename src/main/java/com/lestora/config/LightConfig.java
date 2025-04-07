package com.lestora.config;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LightConfig {
    private static final Logger LOGGER = LogManager.getLogger();
    static boolean isServerAuthoritative;
    static final Map<RLAmount, Integer> lightLevelsMap = new HashMap<>();
    static final Map<ResourceLocation, Integer> minCache = new HashMap<>();
    static final Map<ResourceLocation, Integer> maxCache = new HashMap<>();

    static final Map<RLAmount, Integer> lightLevelsUniqueMap = new HashMap<>();
    static final Map<ResourceLocation, Integer> minUniqueCache = new HashMap<>();
    static final Map<ResourceLocation, Integer> maxUniqueCache = new HashMap<>();

    static final Map<RLAmount, Integer> defaultLightLevels = new HashMap<>();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    static final List<LightChangeListener> listenerSub = new ArrayList<>();
    static final List<Runnable> fullSub = new ArrayList<>();
    static boolean firstLoad = false;

    static {
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 15), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 14), 14);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 13), 13);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 12), 12);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 11), 11);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 10), 10);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 9), 9);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 7), 7);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 6), 6);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 5), 5);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 4), 4);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 3), 3);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 2), 2);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "light"), 1), 1);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "sea_pickle"), 4), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "sea_pickle"), 3), 12);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "sea_pickle"), 2), 9);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "sea_pickle"), 1), 6);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "respawn_anchor"), 4), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "respawn_anchor"), 3), 11);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "respawn_anchor"), 2), 7);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "respawn_anchor"), 1), 3);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "candle"), 4), 12);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "candle"), 3), 9);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "candle"), 2), 6);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "candle"), 1), 3);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "campfire"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "redstone_lamp"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "soul_campfire"), 1), 10);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "blast_furnace"), 1), 13);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "furnace"), 1), 13);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "smoker"), 1), 13);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "deepslate_redstone_ore"), 1), 9);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "redstone_ore"), 1), 9);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "redstone_torch"), 1), 7);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "beacon"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "conduit"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "end_gateway"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "end_portal"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "fire"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "ochre_froglight"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "pearlescent_froglight"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "verdant_froglight"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "glowstone"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "jack_o_lantern"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "lantern"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "lava"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "lava_cauldron"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "sea_lantern"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "shroomlight"), 1), 15);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "cave_vines"), 1), 14);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "end_rod"), 1), 14);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "torch"), 1), 14);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "nether_portal"), 1), 11);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "crying_obsidian"), 1), 10);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "soul_fire"), 1), 10);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "soul_lantern"), 1), 10);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "soul_torch"), 1), 10);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "enchanting_table"), 1), 7);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "ender_chest"), 1), 7);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "glow_lichen"), 1), 7);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "sculk_catalyst"), 1), 6);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "amethyst_cluster"), 1), 5);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "large_amethyst_bud"), 1), 4);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "magma_block"), 1), 3);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "medium_amethyst_bud"), 1), 2);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "brewing_stand"), 1), 1);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "brown_mushroom"), 1), 1);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "dragon_egg"), 1), 1);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "end_portal_frame"), 1), 1);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "sculk_sensor"), 1), 1);
        defaultLightLevels.put(new RLAmount(ResourceLocation.fromNamespaceAndPath("minecraft", "small_amethyst_bud"), 1), 1);
    }

    public static boolean configFirstLoaded() {
        return firstLoad;
    }

    public static void subscribe(LightChangeListener listener) {
        listenerSub.add(listener);
    }

    public static void subscribe(Runnable onLevelsChanged) {
        fullSub.add(onLevelsChanged);
    }

    public static Map<RLAmount, Integer> getUniqueLightLevels() { return new HashMap<>(lightLevelsUniqueMap); }
    public static Map<RLAmount, Integer> getDefaultLightLevels() { return new HashMap<>(defaultLightLevels); }
    public static Map<RLAmount, Integer> getLightLevels() { return new HashMap<>(lightLevelsMap); }

    public static Integer getLightLevel(ResourceLocation rl, int amount) {
        lock.readLock().lock();
        try {
            return lightLevelsMap.get(new RLAmount(rl, amount));
        } finally {
            lock.readLock().unlock();
        }
    }

    public static Integer getMinLightLevel(ResourceLocation rl) {
        lock.readLock().lock();
        try {
            cacheMinAndMax(rl);
            return minCache.get(rl);
        } finally {
            lock.readLock().unlock();
        }
    }

    public static Integer getMaxLightLevel(ResourceLocation rl) {
        lock.readLock().lock();
        try {
            cacheMinAndMax(rl);
            return maxCache.get(rl);
        } finally {
            lock.readLock().unlock();
        }
    }

    public static Integer getUniqueLightLevel(ResourceLocation rl, int amount) {
        lock.readLock().lock();
        try {
            return lightLevelsUniqueMap.get(new RLAmount(rl, amount));
        } finally {
            lock.readLock().unlock();
        }
    }

    public static Integer getUniqueMinLightLevel(ResourceLocation rl) {
        lock.readLock().lock();
        try {
            cacheUniqueMinAndMax(rl);
            return minUniqueCache.get(rl);
        } finally {
            lock.readLock().unlock();
        }
    }

    public static Integer getUniqueMaxLightLevel(ResourceLocation rl) {
        lock.readLock().lock();
        try {
            cacheUniqueMinAndMax(rl);
            return maxUniqueCache.get(rl);
        } finally {
            lock.readLock().unlock();
        }
    }

    static void cacheUniqueMinAndMax(ResourceLocation rl) {
        if (minUniqueCache.containsKey(rl) && maxUniqueCache.containsKey(rl)) return;

        Integer min = null;
        Integer max = null;
        for (Map.Entry<RLAmount, Integer> entry : lightLevelsUniqueMap.entrySet()) {
            if (entry.getKey().getResource().equals(rl)) {
                int lightLevel = entry.getValue();
                if (min == null || lightLevel < min) min = lightLevel;
                if (max == null || lightLevel > max) max = lightLevel;
            }
        }
        if (min != null) minUniqueCache.put(rl, min);
        if (max != null) maxUniqueCache.put(rl, max);
    }

    static void cacheMinAndMax(ResourceLocation rl) {
        if (minCache.containsKey(rl) && maxCache.containsKey(rl)) return;

        Integer min = null;
        Integer max = null;
        for (Map.Entry<RLAmount, Integer> entry : lightLevelsMap.entrySet()) {
            if (entry.getKey().getResource().equals(rl)) {
                int lightLevel = entry.getValue();
                if (min == null || lightLevel < min) min = lightLevel;
                if (max == null || lightLevel > max) max = lightLevel;
            }
        }
        if (min != null) minCache.put(rl, min);
        if (max != null) maxCache.put(rl, max);
    }

    static void setLightLevelsMap(List<String> stringMap, String from) {
        lock.writeLock().lock();
        try {
            if (from.equals("CLIENT") && isServerAuthoritative) return;
            else if (from.equals("SERVER")) isServerAuthoritative = true;

//            System.err.println("setLightLevelsMap from " + from);
//            LOGGER.error("setLightLevelsMap from " + from);

            var changed = false;

            // Clear caches as we'll modify lightLevelsMap.
            minCache.clear();
            maxCache.clear();
            minUniqueCache.clear();
            maxUniqueCache.clear();

            // Parse new values into a temporary map.
            Map<RLAmount, Integer> newMap = new HashMap<>();
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
                            itemId = itemId.substring(0, start).trim();
                        }
                        ResourceLocation loc = ResourceLocation.tryParse(itemId);
                        if (loc != null) {
                            newMap.put(new RLAmount(loc, amount), level);
                        } else {
                            System.err.println("Invalid ResourceLocation: " + itemId);
                        }

                        if (itemId.toLowerCase().equals("minecraft:torch")) {
                            ResourceLocation loc2 = ResourceLocation.tryParse("minecraft:wall_torch");
                            if (loc2 != null) {
                                newMap.put(new RLAmount(loc2, amount), level);
                            } else {
                                System.err.println("Invalid ResourceLocation: " + "minecraft:wall_torch");
                            }
                        } else if (itemId.toLowerCase().equals("minecraft:redstone_torch")) {
                            ResourceLocation loc2 = ResourceLocation.tryParse("minecraft:redstone_wall_torch");
                            if (loc2 != null) {
                                newMap.put(new RLAmount(loc2, amount), level);
                            } else {
                                System.err.println("Invalid ResourceLocation: " + "minecraft:redstone_wall_torch");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Failed to parse light level for entry: " + entry);
                    }
                } else {
                    System.err.println("Entry is not in expected 'key = value' format: " + entry);
                }
            }

            // Process additions and updates.
            for (var newEntry : newMap.entrySet()) {
                RLAmount key = newEntry.getKey();
                int newValue = newEntry.getValue();
                if (!lightLevelsMap.containsKey(key)) {
                    lightLevelsMap.put(key, newValue);
                    listenerSub.forEach(sub -> sub.onChange(key, null, newValue));
                    changed = true;
                } else {
                    int oldValue = lightLevelsMap.get(key);
                    if (oldValue != newValue) {
                        lightLevelsMap.put(key, newValue);
                        listenerSub.forEach(sub -> sub.onChange(key, oldValue, newValue));
                        changed = true;
                    }
                }

                if (!lightLevelsUniqueMap.containsKey(key)) {
                    if (isDefault(key, newValue))
                        lightLevelsUniqueMap.remove(key);
                    else
                        lightLevelsUniqueMap.put(key, newValue);
                } else {
                    int oldValue = lightLevelsUniqueMap.get(key);
                    if (oldValue != newValue)
                        lightLevelsUniqueMap.put(key, newValue);
                }
            }

            // Process removals.
            var it = lightLevelsMap.keySet().iterator();
            while (it.hasNext()) {
                RLAmount key = it.next();
                if (!newMap.containsKey(key)) {
                    int oldValue = lightLevelsMap.get(key);
                    it.remove();
                    lightLevelsUniqueMap.remove(key);
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

    static boolean isDefault(RLAmount key, int val) {
        Integer defaultVal = defaultLightLevels.get(key);
        return defaultVal != null && defaultVal == val;
    }

    public static void setServerAuthoritative(boolean b) {
        isServerAuthoritative = b;
    }
}