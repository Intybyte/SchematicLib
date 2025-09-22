package me.vaan.schematiclib.base.block;

import me.vaan.schematiclib.file.block.FileBlock;

public interface IBlock extends ICoord {
    //keys
    BlockKey key();

    default boolean matches(IBlock block) {
        if (this.x() != block.x()) return false;
        if (this.y() != block.y()) return false;
        if (this.z() != block.z()) return false;

        return this.key().full().equals(block.key().full());
    }

    default IBlock add(ICoord coord) {
        return new FileBlock(
            x() + coord.x(),
            y() + coord.y(),
            z() + coord.z(),
            key()
        );
    }
}
