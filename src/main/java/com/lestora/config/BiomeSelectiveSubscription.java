package com.lestora.config;

import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class BiomeSelectiveSubscription {
    public final Set<ResourceLocation> keys;
    public final BiomeChangeListener listener;

    public BiomeSelectiveSubscription(Set<ResourceLocation> keys, BiomeChangeListener listener) {
        // Create a defensive copy of the keys
        this.keys = new HashSet<>(keys);
        this.listener = listener;
    }
}