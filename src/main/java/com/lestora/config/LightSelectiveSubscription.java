package com.lestora.config;

import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LightSelectiveSubscription {
    public final Set<ResourceLocation> keys;
    public final LightChangeListener listener;

    public LightSelectiveSubscription(Set<ResourceLocation> keys, LightChangeListener listener) {
        // Store a defensive copy
        this.keys = new HashSet<>(keys);
        this.listener = listener;
    }
}