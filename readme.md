# Lestora Dynamic Lights

This is a utility mod that other Lestora mods depend on to load shared config files, allowing you to pick and choose between which Lestora mods to install without directly depending on one another.

## Features
- **Usage:** There isn't much direct use for this mod, though you can list out configurations if you want to follow the commands.
- This mod is set up (or trying to be set up) to work in Single Player, or in Multi-Player and Dedicated Server, where servers are Authoritative over Local Configuration.

## LightConfig
- Configuration saved in your config folder as `lestora-lighting.toml`
- Configuration hard-codes a few checks, like checking if an item is "Lit", or if Pickles are "Waterlogged", and if cave_vines have berries.  But beyond the name, you must sometimes specify a BlockState "amount".  For example, with Candles, there is a different light level for 1 candle versus 4 candles.  So specify them like so:
- `"minecraft:candle(3)=9", "minecraft:torch", etc`
- Feel free to request other Block States be set up for specific lighting needs.

## BiomeConfig
- Configuration saved in your config folder as `lestora-biome.toml`
- This is currently used to specify the biome's temperature from -1.0 (Coldest) up to 2.0 (Hottest).
- Many of the default configurations match what you'll find online, but others are fixed to be more correct.
- `"minecraft:snowy_taiga=-0.7", "minecraft:lush_caves=0.4", etc`

## Manual Installation
1. Download the mod JAR from CurseForge.
2. Place the JAR file into your `mods` folder.
3. Launch Minecraft with the Forge profile.

## Commands
- Use the command `/lestora config lighting list`: to print the current in-memory understanding of light level configurations to your player chat.
- Use the command `/lestora config lighting whatAmIHolding`: This command will tell you what you're holding in each hand, in case you need the official name in order to add it to the config file.

## Compatibility
- **Minecraft Version:** 1.21.4
- **Forge Version:** 54.1.0

## Troubleshooting
If you run into issues (e.g., crashes or unexpected behavior), check the logs in your `crash-reports` or `logs` folder. You can also open an issue on the mod’s GitHub repository.

## Contributing
Contributions are welcome! Please submit pull requests or open issues if you have suggestions or bug reports.

## License
This project is licensed under the MIT License.
