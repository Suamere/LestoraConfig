package com.lestora.config;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

// A class to handle config events on the server.
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
class BiomeConfigHandler {
    public static final ForgeConfigSpec BIOME_CONFIG;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BIOME_TEMPS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("biome_temps");

        List<String> defaultTemps = new ArrayList<>();
        for (var defaultTemp : BiomeConfig.getDefaultBiomeTemps().entrySet()) {
            defaultTemps.add(defaultTemp.getKey().toString() + "=" + defaultTemp.getValue());
        }

        BIOME_TEMPS = builder.comment("List of biome=temperature pairs. Temperature must be in range -1.0 to 2.0")
                .defineList("items", defaultTemps, o -> o instanceof String && ((String)o).contains("="));
        builder.pop();
        BIOME_CONFIG = builder.build();
    }

    static List<String> getBiomeTempConfig() {
        return new ArrayList<>(BIOME_TEMPS.get());
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == BIOME_CONFIG) {
            // If it's dedicated server, setting BiomeConfig is neither here nor there.  It's unused.
            // Otherwise, the initial "onLoad" runs on game boot near the Title Screen, irrespective of client/server, so load CLIENT
            BiomeConfig.setBiomeTempsMap(getBiomeTempConfig(), "CLIENT");
        }
    }

    @SubscribeEvent
    static void onReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == BIOME_CONFIG) {
            // Update the local config state (on the current system – singleplayer, LAN host, or dedicated server)
            BiomeConfig.setBiomeTempsMap(getBiomeTempConfig(), "CLIENT"); // or "SERVER" if that's what you intend for your integrated server

            // If running on a server (integrated server for LAN/dedicated), propagate the updated config to all connected players:
            // Get the server instance (this works on dedicated or integrated servers)
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                // Iterate over all connected players and send them the updated config packet
                for (var player : server.getPlayerList().getPlayers()) {
                    if (PlayerLoginEvent.ownerPlayer != null && PlayerLoginEvent.ownerPlayer.getUUID().equals(player.getUUID())) {
                        // The owner player, if there is one, is handled by the first line of this method where teh BiomeConfig is directly updated.
                        continue;
                    }
                    ModNetworking.CHANNEL.send(
                            new LightValuePacket(getBiomeTempConfig()),
                            PacketDistributor.PLAYER.with(player)
                    );
                }
            }
        }
    }
}