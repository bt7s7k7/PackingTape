# Packing tape

![Short clip showcasing packing up a double chest filled with items and moving it somewhere else](https://github.com/bt7s7k7/PackingTape/blob/master/docs/showcase.webp?raw=true)

Take your chest, tanks, or whatever NBT containing block you want with you, by packing it up with packing tape! Using this mod, you can turn any block into an item and place them somewhere else. Useful for things like moving bases or moving out project supplies.

## Features
- **Pick up any block entity:** Wide compatibility by building atop Minecraft's standard pick block with data functionality
- **Balanced usage:** A packing tape item is consumed for each packing operation, which disincentivizes using this mod as an alternative to other portable storage blocks
- **Data loss prevention:** Even with this mod uninstalled, the packed up blocks can be placed back into the world and their contents will be preserved
- **Configurability:** A whitelist and a blacklist for blocks can be configured, including wildcard support to exclude whole mods

## Limitations
- When the packing tape is used on a container which has loot that wasn't generated yet, the generation will happen and all items will be dropped on the ground
- Using the packing tape on some multiblock machines or machines with special functionality can lead to item duplication, but this can be remedied by adding them to the blacklist in the mod config
- The packed up blocks are not different items, meaning they can be accidentally used in recipes, which will lead to their content being lost

## Attribution

The packing tape item texture is derived from [GregTech: Refreshed](https://modrinth.com/resourcepack/gregtech-refreshed) (original file: `duct_tape.png`), which is distributed under the MIT license.
