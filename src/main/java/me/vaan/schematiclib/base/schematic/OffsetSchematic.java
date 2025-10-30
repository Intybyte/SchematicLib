package me.vaan.schematiclib.base.schematic;

import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;
import me.vaan.schematiclib.file.block.FileBlock;
import me.vaan.schematiclib.file.block.FileCoord;
import me.vaan.schematiclib.file.schematic.FileSchematic;

import java.util.ArrayList;
import java.util.List;

public interface OffsetSchematic extends Schematic, ICoord {
    default IBlock of(IBlock block) {
        if (block == null) return null;

        return new FileBlock(block.x() + this.x(), block.y() + this.y(), block.z() + this.z(), block.key());
    }

    /**
     * Please override this or not use it directly if you need to perform many checks
     */
    default Schematic realBlocks() {
        List<IBlock> current = this.positions();
        List<IBlock> blocks = new ArrayList<>(current.size());

        for (IBlock block : current) {
            blocks.add(of(block));
        }

        return new FileSchematic(blocks);
    }

    default boolean areRealBlocksCached() {
        return false;
    }

    @Override
    default boolean contains(FileCoord coord) {
        FileCoord toCheck = new FileCoord(
            coord.x() - this.x(),
            coord.y() - this.y(),
            coord.z() - this.z()
        );
        return Schematic.super.contains(toCheck);
    }
}
