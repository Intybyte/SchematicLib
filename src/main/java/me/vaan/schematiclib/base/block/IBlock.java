package me.vaan.schematiclib.base.block;

public interface IBlock {
    //offsets
    int x();
    int y();
    int z();

    //keys
    BlockKey key();

    default boolean matches(IBlock block) {
        if (this.x() != block.x()) return false;
        if (this.y() != block.y()) return false;
        if (this.z() != block.z()) return false;

        return this.key().full().equals(block.key().full());
    }
}
