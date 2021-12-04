# SmartyGUI

SmartyGUI — a modification for ModLoader/CursedFabric that adds a bunch of useful things to the game!

Currently adds:

    Main Menu info.
    Show Version.
    Show Block.
    Armor Status HUD.
    Tooltip.
    Furnace Info.
    Player List. (Provided with SmartyStation addon.)

## Setup (Players/Users)

### Fabric Version (CF-X.X.X)

The modification provides two jar files:
- SmartyGUI — main modification, only requires Cursed-Fabric-Loader.
- SmartyStation — addon for SmartyGUI, requires StationAPI. Includes player list, some data tracking stuff for normal server->client entity health provider.

If you are unsure with choosing, it is highly recommended to use just `SmartyGUI-CF-X.X.X.jar`.

For working player list, `SmartyStation-CF-X.X.X.jar` have to be installed into client and server and have StationAPI included.

### ModLoader Version (ML-X.X.X)

Requires ModLoader, does not change any base classes. Put `SmartyGUI-ML-X.X.X.jar` inside `mods` folder.


## Build (Developers)

For setup/build instructions, please refer to [stationapi-example-mod page](https://github.com/calmilamsy/stationapi-example-mod).

There is no Fabric-API for beta 1.7.3, so an addon of SmartyGUI (SmartyStation) uses [StationAPI](https://github.com/ModificationStation/StationAPI).

## License
The project is under [MIT License](https://raw.githubusercontent.com/ChessChicken-KZ/SmartyGUI/local/LICENSE). Feel free to look at it.
