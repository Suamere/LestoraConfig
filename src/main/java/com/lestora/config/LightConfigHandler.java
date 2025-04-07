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
class LightConfigHandler {
    public static final ForgeConfigSpec LIGHTING_CONFIG;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> LIGHT_LEVELS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("light_levels");

        List<String> defaultLevels = new ArrayList<>();
        for (var defaultLL : LightConfig.getDefaultLightLevels().entrySet()) {
            defaultLevels.add(defaultLL.getKey().getResource().toString() + "(" + defaultLL.getKey().getAmount() + ")=" + defaultLL.getValue());
        }

        LIGHT_LEVELS = builder.comment("List of item=light level pairs")
                .defineList("items", defaultLevels, o -> o instanceof String && ((String)o).contains("="));
        builder.pop();
        LIGHTING_CONFIG = builder.build();
    }

    static List<String> getLightLevelConfig() {
        return new ArrayList<>(LIGHT_LEVELS.get());
    }

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == LIGHTING_CONFIG) {
            // If it's dedicated server, setting LightConfig is neither here nor there.  It's unused.
            // Otherwise, the initial "onLoad" runs on game boot near the Title Screen, irrespective of client/server, so load CLIENT
            LightConfig.setLightLevelsMap(getLightLevelConfig(), "CLIENT");
        }
    }

    @SubscribeEvent
    static void onReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == LIGHTING_CONFIG) {
            // Update the local config state (on the current system – singleplayer, LAN host, or dedicated server)
            LightConfig.setLightLevelsMap(getLightLevelConfig(), "CLIENT"); // or "SERVER" if that's what you intend for your integrated server

            // If running on a server (integrated server for LAN/dedicated), propagate the updated config to all connected players:
            // Get the server instance (this works on dedicated or integrated servers)
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                // Iterate over all connected players and send them the updated config packet
                for (var player : server.getPlayerList().getPlayers()) {
                    if (PlayerLoginEvent.ownerPlayer != null && PlayerLoginEvent.ownerPlayer.getUUID().equals(player.getUUID())) {
                        // The owner player, if there is one, is handled by the first line of this method where teh LightConfig is directly updated.
                        continue;
                    }
                    ModNetworking.CHANNEL.send(
                            new LightValuePacket(getLightLevelConfig()),
                            PacketDistributor.PLAYER.with(player)
                    );
                }
            }
        }
    }
}