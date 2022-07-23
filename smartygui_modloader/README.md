# SmartyGUI - ModLoader version!

SmartyGUI — a Minecraft b1.7.3 modification for ModLoader/Fabric that adds a bunch of useful client-side things to the game!

Currently, it adds:
```
    - Block/Entity Viewer HUD.
    - Extended F3.
    - Armor Status HUD.
    - In-Game Tooltip.
```

## Build (Developers/Code explorers)

1. Install MCP v4.3 ([1.7.3-LTS](https://github.com/ModificationStation/1.7.3-LTS) or [RetroMCP](https://github.com/MCPHackers/RetroMCP-Java) is recommended).
2. Prepare workspace with ModLoader preinstalled as MCP's instruction says, fix all necessary issues.
3. Copy sources from this github repository into your MCP folder.
4. Enjoy exploring/editing the code.

## License
The ML version of the project is under [Apache License 2.0](https://raw.githubusercontent.com/ChessChicken-KZ/SmartyGUI/modloader/src/minecraft/smartygui/LICENSE.txt). Feel free to look at it.

## Changelog
```
ML-2.4:
    Bug fixes/General:
    	— Fixed an issue with config parsing between various SmartyGUI versions.
    	— Fixed a possible issue related to "backspace" input in some text fields.
    	— Render code is now a bit optimised.
    	— Config initialisation is a small bit optimised.
    	— Fixed some typos in the mod.

    New features:
    	— Added "MC Version" label module.
    	— New anchors for HUD (now 9 various types).

ML-2.3:
    Bug fixes/General:
    	— Fixed ModMenu not detecting the mod's description.
    	— Internal KeyBinding method change for better compatibility (in future).

    New features:
    	— A toggle keybind for turning on/off the mod's GUI part.
    	— Small localization for keybindings' names inside "Options" settings.

ML-2.2:
    Bug fixes/General:
    	— Shortened time for ButtonBase hints.
    	— Debug mode for BEVHUD while changing its' parameters.
       	— Generally micro optimizations and code cleanup.
    	— Some bytes became booleans, some ints became bytes.
    	— Some grammatical mistakes fix.

    Additions:
    	— Implementation of plugin system for the modification.
    	— A new plugin that shows block breaking percentage.
    	— A new plugin that enhances the modification usage.
	    	— Replaced broken block textures with their items in Block Viewer HUD.
	    	— Jukebox's music information.
	    	— Plant's grow stage information.
	    — Extension of "common" loader (making the modification less dependant on ModLoader).

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
