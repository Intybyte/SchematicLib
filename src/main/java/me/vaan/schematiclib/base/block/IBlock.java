package me.vaan.schematiclib.base.block;

import me.vaan.schematiclib.base.key.BlockKeyHolder;

public interface IBlock extends ICoord, BlockKeyHolder {

    default boolean matches(IBlock block) {
        if (this.x() != block.x()) return false;
        if (this.y() != block.y()) return false;
        if (this.z() != block.z()) return false;

        return this.key().full().equals(block.key().full());
    }

    default boolean matches(ICoord coord) {
        if (this.x() != coord.x()) return false;
        if (this.y() != coord.y()) return false;
        if (this.z() != coord.z()) return false;

        return true;
    }

    default boolean sameType(IBlock block) {
        return key().equals(block.key());
    }

    default IBlock addClone(ICoord coord) {
        return addClone(
            coord.x(),
            coord.y(),
            coord.z()
        );
    }

    IBlock addClone(int x, int y, int z);
}
