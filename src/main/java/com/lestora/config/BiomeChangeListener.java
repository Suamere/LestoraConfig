package com.lestora.config;

import net.minecraft.resources.ResourceLocation;

// Define the subscriber interface.
@FunctionalInterface
public interface BiomeChangeListener {
    void onChange(ResourceLocation key, Float oldValue, Float newValue);
}
