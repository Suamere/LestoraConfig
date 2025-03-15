package com.lestora;

import net.minecraft.resources.ResourceLocation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

class LestoraConfig {
    static boolean isServerAuthoritative;
    static final Map<RLAmount, Integer> lightLevelsMap = new ConcurrentHashMap<>();

    public static Integer getLightLevel(ResourceLocation rl, int amount) {
        return lightLevelsMap.get(new RLAmount(rl, amount));
    }

    public static Map<RLAmount, Integer> getLightLevels() {
        return new HashMap<>(lightLevelsMap);
    }

    static void setLightLevelsMap(List<String> stringMap, String from) {
        if (from.equals("CLIENT") && isServerAuthoritative) return;
        else if (from.equals("SERVER")) isServerAuthoritative = true;

        lightLevelsMap.clear();
        for (String entry : stringMap) {
            String[] split = entry.split("=");
            if (split.length == 2) {
                String itemId = split[0].trim();
                try {
                    int level = Integer.parseInt(split[1].trim());
                    int amount = 0;
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