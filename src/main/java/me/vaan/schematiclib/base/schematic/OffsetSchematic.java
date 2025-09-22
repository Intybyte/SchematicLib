package me.vaan.schematiclib.base.schematic;

import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;
import me.vaan.schematiclib.file.block.FileBlock;

public interface OffsetSchematic extends Schematic, ICoord {
    default IBlock of(IBlock block) {
        if (block == null) return null;

        return new FileBlock(block.x() + this.x(), block.y() + this.y(), block.z() + this.z(), block.key());
    }

    default Schematic realBlocks() {
        return positions().stream().map(this::of).collect(Schematic.COLLECTOR);
    }
}
