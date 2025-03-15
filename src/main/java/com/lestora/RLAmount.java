package com.lestora;

import net.minecraft.resources.ResourceLocation;
import java.util.Objects;

public class RLAmount {
    private final ResourceLocation resource;
    private final int amount;

    public RLAmount(ResourceLocation resource, int amount) {
        this.resource = resource;
        this.amount = amount;
    }

    public ResourceLocation getResource() {
        return resource;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RLAmount)) return false;
        RLAmount that = (RLAmount) o;
        return amount == that.amount && Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, amount);
    }

    @Override
    public String toString() {
        return resource.toString() + (amount != 0 ? "(" + amount + ")" : "");
    }
}
