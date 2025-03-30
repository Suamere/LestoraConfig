package com.lestora.config;

// Define the subscriber interface.
@FunctionalInterface
public interface LightChangeListener {
    void onChange(RLAmount key, Integer oldValue, Integer newValue);
}
