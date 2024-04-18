# Cardinal Classes Mod

## Description

Cardinal Classes is a simple and modular mod for Minecraft that adds a class mechanics to the game.
The mod itself doesn't add any classes, but provides a framework for other mods to add their own classes.
It's built on the Fabric modding platform and uses the Cardinal Components API.

## Features

### Simple Class System
The mod adds a simple class system to the game, allowing players
to acquire a class by brewing and drinking a class potion.
Each class has four passive skills, four active skills, and two perks.

### Skill System
Skills and Perks can be upgraded using the skill altar.
An altar needs to have a shard holder to its north/south/east/west side with an air block in between.
By placing a shard on a holder, and charging the altar with the relevant item, the player can step on it and upgrade skills and perks.
Skills can be upgraded three times from level 0 to level 3, while only one of the two perk can be ascended once.

#### Charge Items

- Shard Holder: Skill Shard - Consumed upgrade skills and perks.
- Skill Altar: Magma Block - Used to upgrade active skills.
- Skill Altar: Amethyst Block - Used to upgrade passive skills.
- Skill Shard: Sage's Emerald - Used to ascend perks.

Consuming a class potion allows a player to reset their class and will
refund all their Skill Shards - but not any Sage's Emeralds.

### Modularity & Customization
The mod is designed to be as customizable as possible,
allowing modders to create their own classes and users to
build and balance their own modpacks by selecting only the classes they want to include.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/).
2. Download the latest version of Cardinal Classes from [Modrinth](https://github.com/yourusername/CardinalClasses/releases).
3. Place the downloaded .jar file into your `mods` folder.
4. Launch the game using the Fabric profile.

## Building

To build the mod, run the following command in the project root directory:

```bash
./gradlew build
```

## Texture Credits
The following textures have been taken from [malcomriley](https://github.com/malcolmriley)'s [unused-textures](https://github.com/malcolmriley/unused-textures)
and are licensed under the Creative Commons Attribution 4.0 International License:
- Skill Shard
- Skill Fragment
- Sage's Emerald