# Lestora Dynamic Lights

This is a utility mod that other Lestora mods depend on to load shared config files, allowing you to pick and choose between which Lestora mods to install without directly depending on one another.

## Features
- **Usage:** There isn't much direct use for this mod, though you can list out configurations if you want to follow the commands.
- As of version 1.0.0, this is only to store shared configuration of light levels in `lestora-lighting.toml`
- More configuration will be added as Lestora grows.
- This mod is set up (or trying to be set up) to work in Single Player, or in Multi-Player and Dedicated Server, where servers are Authoritative over Local Configuration.

## Config
- Configuration hard-codes a few checks, like checking if an item is "Lit", or if Pickles are "Waterlogged", and if cave_vines have berries.  But beyond the name, you must sometimes specify a BlockState "amount".  For example, with Candles, there is a different light level for 1 candle versus 4 candles.  So specify them like so:
- "minecraft:candle(3)=9", "minecraft:torch", etc
- Feel free to request other Block States be set up for specific lighting needs.

## Manual Installation
1. Download the mod JAR from CurseForge.
2. Place the JAR file into your `mods` folder.
3. Launch Minecraft with the Forge profile.

## Commands
- Use the command `/lestora config lighting list`: to print the current in-memory understanding of light level configurations to your player chat.
- Use the command `/lestora config whatAmIHolding`: This command will tell you what you're holding in each hand, in case you need the official name in order to add it to the config file.

## Compatibility
- **Minecraft Version:** 1.21.4
- **Forge Version:** 54.1.0

## Troubleshooting
If you run into issues (e.g., crashes or unexpected behavior), check the logs in your `crash-reports` or `logs` folder. You can also open an issue on the mod’s GitHub repository.

## Contributing
Contributions are welcome! Please submit pull requests or open issues if you have suggestions or bug reports.

## License
This project is licensed under the MIT License.
