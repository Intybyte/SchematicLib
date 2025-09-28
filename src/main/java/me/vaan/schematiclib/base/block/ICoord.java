package me.vaan.schematiclib.base.block;

public interface ICoord {
    //offsets
    int x();
    int y();
    int z();

    default boolean within(ICoord min, ICoord max) {
        return this.x() >= min.x() && this.x() <= max.x()
            && this.y() >= min.y() && this.y() <= max.y()
            && this.z() >= min.z() && this.z() <= max.z();
    }
}
