# SchematicLib
Lightweight library based on Gson 2.11.0 for simple schematics for minecraft, doesn't save block data.

Meant for bukkit, but can be used for any platforms.

It has been designed to be modular at its core, to allow namespaces to modify the behaviour of playing and
destroying blocks, this allows you to define support for custom blocks defined by other plugins, maybe slimefun or
items adder, anything as long as they have a way to get custom block at a certain position and place custom blocks.