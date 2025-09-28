package me.vaan.schematiclib.base.schematic;

import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;
import me.vaan.schematiclib.file.block.FileCoord;
import me.vaan.schematiclib.file.schematic.FileSchematic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface Schematic extends Iterable<IBlock> {
    List<IBlock> positions();

    default Iterator<IBlock> iterator() {
        return positions().iterator();
    }

    default ICoord getMin() {
        List<IBlock> blocks = positions();
        if (blocks.isEmpty()) return null;

        int minX = blocks.get(0).x();
        int minY = blocks.get(0).y();
        int minZ = blocks.get(0).z();

        for (int i = 1; i < blocks.size(); i++) {
            IBlock b = blocks.get(i);
            if (b.x() < minX) minX = b.x();
            if (b.y() < minY) minY = b.y();
            if (b.z() < minZ) minZ = b.z();
        }

        return new FileCoord(minX, minY, minZ);
    }

    default ICoord getMax() {
        List<IBlock> blocks = positions();
        if (blocks.isEmpty()) return null;

        int maxX = blocks.get(0).x();
        int maxY = blocks.get(0).y();
        int maxZ = blocks.get(0).z();

        for (int i = 1; i < blocks.size(); i++) {
            IBlock b = blocks.get(i);
            if (b.x() > maxX) maxX = b.x();
            if (b.y() > maxY) maxY = b.y();
            if (b.z() > maxZ) maxZ = b.z();
        }

        return new FileCoord(maxX, maxY, maxZ);
    }

    default boolean contains(ICoord coord) {
        for (IBlock blocks : this) {
            if (blocks.matches(coord)) return true;
        }

        return false;
    }

    default Schematic moveOrigin(ICoord coord) {
        return moveOrigin(coord.x(), coord.y(), coord.z());
    }

    default Schematic moveOrigin(int x, int y, int z) {
        ICoord coord = this.getMin();
        int diffX = coord.x() - x;
        int diffY = coord.y() - y;
        int diffZ = coord.z() - z;
        ICoord difference = new FileCoord(diffX, diffY, diffZ);

        List<IBlock> newSchematic = new ArrayList<>();
        for (IBlock block : this) {
            newSchematic.add(block.add(difference));
        }

        return new FileSchematic(
            newSchematic
        );
    }
}
