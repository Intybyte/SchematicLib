package me.vaan.schematiclib.base.schematic;

import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.file.block.FileBlock;

public interface OffsetSchematic extends Schematic {
    //offsets
    int x();
    int y();
    int z();

    default IBlock of(IBlock block) {
        return new FileBlock(block.x() + this.x(), block.y() + this.y(), block.z() + this.z(), block.key());
    }
}
