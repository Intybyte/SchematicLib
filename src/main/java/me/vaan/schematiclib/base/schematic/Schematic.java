package me.vaan.schematiclib.base.schematic;

import me.vaan.schematiclib.base.block.IBlock;

import java.util.Iterator;
import java.util.List;

public interface Schematic extends Iterable<IBlock> {
    List<IBlock> positions();

    default Iterator<IBlock> iterator() {
        return positions().iterator();
    }
}
