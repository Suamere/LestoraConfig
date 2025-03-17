package com.lestora.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class RLAmount {
    private final ResourceLocation resource;
    private final Block blockType;
    private final int amount;

    public RLAmount(ResourceLocation resource, int amount) {
        this.resource = resource;
        this.amount = amount;
        this.blockType = ForgeRegistries.BLOCKS.getValue(resource);
        if (this.blockType == null || this.blockType == Blocks.AIR) {
            System.err.println("Resource " + resource + " resolved to Blocks.AIR.  If it wasn't air on purpose... this means your config file has something odd in it");
        }
    }

    public ResourceLocation getResource() { return resource; }
    public int getAmount() { return amount; }
    public Block getBlockType() { return blockType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RLAmount that)) return false;
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
