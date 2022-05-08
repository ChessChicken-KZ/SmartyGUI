# SmartyGUI - ModLoader version!

SmartyGUI — a modification that adds a bunch of useful things to the game!

Currently adds:
- Block/Entity Viewer HUD.
- Armor Status HUD.
- Tooltip.

## Installation (Players/Users)

Requires ModLoader, does not change any base classes. Put `SmartyGUI-ML-X.X.X.jar` inside `mods` folder.

Built packages can be accessed here, **be sure to choose ML-X.X version**: [Release tags list](https://github.com/ChessChicken-KZ/SmartyGUI/tags).

## Build (Developers/Code explorers)

1. Install MCP v4.3 ([1.7.3-LTS](https://github.com/ModificationStation/1.7.3-LTS) or [RetroMCP](https://github.com/MCPHackers/RetroMCP-Java) is recommended).
2. Prepare workspace with ModLoader preinstalled as MCP's instruction says, fix all necessary issues.
3. Copy sources from this github repository into your MCP folder.
4. Enjoy exploring/editing the code.

## License
The ML version of the project is under [Apache License 2.0](https://raw.githubusercontent.com/ChessChicken-KZ/SmartyGUI/modloader/src/minecraft/smartygui/LICENSE.txt). Feel free to look at it.

## Changelog
```
ML-2.1:
    Bug fixes:
    — Fixed a crash whereas the mod was trying to get null properties and eventually was throwing NullPointerException (names and icons).
    — Fixed a strange gradient renderer bug, where it could use wrong quads.
    — Fixed a bug related with Armor status HUD renderer (could render it incorrectly / reversed order).
    — In-Game Tooltip uses its own timer and doesn't break down in lots of moments.
    — Block Viewer HUD uses additional string drawing method to avoid unnecessary “conflict” with Cyrillic modifications.

    Removed content:
    — As for being useless, the specific module that provides additional information in main menu was fully removed.
    — The full code of the furnace additional information module also was removed for the ML version due to some unsolved issues.

    New content:
    — A huger flexibility for Block/Entity Viewer HUD.
        |— It's not locked to specific coordinates only, but can be moved anywhere.
            |— Uses X, Y and ANCHOR values system.
            |— Settings contain some presets.
        |— Entity model rendering implementation.
    — Added a GUI implementation of the config file! Can be accessed via "Numpad 0".
        |— A small credits screen.
        |— Two icon themes.
        |— Dark theme mode for Config GUI.
    — Transparency mode for Block/Entity Viewer HUD.

    Code changes:
    — The modification has moved to Apache License 2.0. Be careful.
    — A huge modification's code refactor, as there were some flexibility issues.
    — Most of the code should be readable, though has some foreign-language terminology.

ML-2.0:
    - Updated to the recent CF-like version (CF-1.0.1).
```
