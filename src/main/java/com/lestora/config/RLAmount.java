package com.lestora.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
        if (o == null) return false;
        if (!(o instanceof RLAmount that)) return false;
        return Objects.equals(resource, that.resource) && amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, amount);
    }

    @Override
    public String toString() {
        return resource.toString() + (amount != 0 ? "(" + amount + ")" : "");
    }

    public boolean stateMatches(BlockState blockState) {
        Block block = blockState.getBlock();
        if (block != blockType) return false;

        // Campfire: ensure it is lit
        if (block == Blocks.CAMPFIRE) {
            return blockState.getValue(BlockStateProperties.LIT);
        }

        // Redstone Lamp: ensure it is lit
        if (block == Blocks.REDSTONE_LAMP) {
            return blockState.getValue(BlockStateProperties.LIT);
        }

        // Soul Campfire: ensure it is lit
        if (block == Blocks.SOUL_CAMPFIRE) {
            return blockState.getValue(BlockStateProperties.LIT);
        }

        // Blast Furnace, Furnace, Smoker: ensure they are lit
        if (block == Blocks.BLAST_FURNACE || block == Blocks.FURNACE || block == Blocks.SMOKER) {
            return blockState.getValue(BlockStateProperties.LIT);
        }

        // Deepslate Redstone Ore and Redstone Ore: check LIT property if available
        if (block == Blocks.DEEPSLATE_REDSTONE_ORE || block == Blocks.REDSTONE_ORE) {
            if (blockState.hasProperty(BlockStateProperties.LIT)) {
                return blockState.getValue(BlockStateProperties.LIT);
            }
            return false;
        }

        // Redstone Torch: now check the LIT property, as it can be unlit.
        if (block == Blocks.REDSTONE_TORCH) {
            if (blockState.hasProperty(BlockStateProperties.LIT)) {
                return blockState.getValue(BlockStateProperties.LIT);
            }
            return false;
        }

        // Light Block special case: assume a custom property LIGHT_LEVEL
        if (block == Blocks.LIGHT) {
            int lightLevel = blockState.getValue(LightBlock.LEVEL);
            return lightLevel == amount && amount > 0;
        }

        // Sea Pickle special case
        if (block instanceof SeaPickleBlock) {
            if (blockState.getValue(BlockStateProperties.WATERLOGGED))
                return amount == blockState.getValue(SeaPickleBlock.PICKLES);
            return false;
        }

        // Respawn Anchor: check the charge value (assumes charge is stored in CHARGE)
        if (block == Blocks.RESPAWN_ANCHOR) {
            int charge = blockState.getValue(RespawnAnchorBlock.CHARGE);
            return charge == amount;
        }

        // Candle: check the number of candles (assumes using BlockStateProperties.CANDLES)
        if (block == Blocks.CANDLE) {
            int candles = blockState.getValue(BlockStateProperties.CANDLES);
            return candles == amount;
        }

        return amount == 0;
    }
}
