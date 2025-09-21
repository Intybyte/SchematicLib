package me.vaan.schematiclib.base.namespace;

import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.schematic.OffsetSchematic;

import java.util.UUID;

public interface NamespaceHandler {
    default boolean matches(IBlock block, UUID world) {
        IBlock worldBlock = get(block.x(), block.y(), block.z(), world);
        return worldBlock.matches(block);
    }

    void place(IBlock block, UUID world);

    IBlock get(int x, int y, int z, UUID world);
}
