<div style="align-content: center;">
  <img src="assets/banner_1170x500.png" alt="A banner with Stancements' wide logo. Taken in the Mellomedley [1.21.1] instance."/>
</div>

<sup>*For NeoForge 26.1.2*</sup>

***Stancements*** is a mod that adds many miscellaneous but relatively useful features for *Minecraft*.

Many features from this mod were and will be taken from my other mods, since I plan to merge all "slightly useful but not random" features to reduce clutter and increase maintainability.

### Original mods
This is a list of the mods I've pulled ideas from, including some that I might add in the future:
- ***Melony Additions***: Some blocks, like the missing blocks and *Classic* wool colors will be added with more features;
- ***Melony Lib***: Various feature backports and tags (still need to set a boundary between features for this mod and ***Stackports***);
- ***Decorativelary***: Some blocks, and the pre-existing shelves;
- [***My Other Stuff (Others)***](https://github.com/isabellawoods/My-Other-Stuff): Many random but cool-looking blocks (I really like the new textures I made, and don't want them to go to waste);
- ***JTW Labs***: The crop pots, as that's basically the only feature from that mod.

## Using debugging flags
*Stancements* adds **debugging flags** for testing new content during development. These flags can be enabled by adding them to the "VM options" field on the default "Application" run configurations.

This mod currently adds **1** flag:
- `-Dstdebug.logging`:
  - Enables debug logs for when the [vinyl modifier](https://github.com/isabellawoods/Informational-Mod-Features/blob/main/Stancements/Docs/Vinyl%20Modifier.md) pipeline runs. These will be logged under `VinylModifier/namespace:modifier_id`:
    - available strategies: *\[strategies]*, current: *\[current strategy]*
    - acts on *\[track]*? true/false
    - modifies copies: *\[modifies copies]*; copying song: *\[copying song]*
  - It also controls whether [album definitions](https://github.com/isabellawoods/Informational-Mod-Features/blob/main/Stancements/Docs/Album.md) print their track listing to the logs.
