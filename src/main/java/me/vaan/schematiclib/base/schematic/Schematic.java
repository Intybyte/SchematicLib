package me.vaan.schematiclib.base.schematic;

import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;
import me.vaan.schematiclib.file.block.FileCoord;
import me.vaan.schematiclib.file.schematic.FileSchematic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;

public interface Schematic extends Iterable<IBlock> {
    Collector<IBlock, FileSchematic, FileSchematic> COLLECTOR = Collector.of(
        FileSchematic::new,
        (schem, obj) -> schem.positions().add(obj),
        (m1, m2) -> {
            ArrayList<IBlock> output = new ArrayList<>(m1.positions());
            output.addAll(m2.positions());
            return new FileSchematic(output);
        }
    );

    List<IBlock> positions();

    default Iterator<IBlock> iterator() {
        return positions().iterator();
    }
}
