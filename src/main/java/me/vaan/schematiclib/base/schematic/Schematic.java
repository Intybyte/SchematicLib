package me.vaan.schematiclib.base.schematic;

import me.vaan.schematiclib.base.block.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;
import me.vaan.schematiclib.file.block.FileBlock;
import me.vaan.schematiclib.file.block.FileCoord;
import me.vaan.schematiclib.file.schematic.FileSchematic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Schematic extends Iterable<IBlock> {
    List<IBlock> positions();
    Map<FileCoord, BlockKey> blockMap();

    default Iterator<IBlock> iterator() {
        return positions().iterator();
    }

    default FileCoord getMin() {
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

    default FileCoord getMax() {
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

    default boolean contains(FileCoord coord) {
        return blockMap().containsKey(coord);
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

    static HashMap<FileCoord, BlockKey> toBlockMap(Collection<IBlock> iBlocks) {
        HashMap<FileCoord, BlockKey> blockMap = new HashMap<>(iBlocks.size());

        for (IBlock ib : iBlocks) {
            FileCoord fileCoord = new FileCoord(ib.x(), ib.y(), ib.z());
            blockMap.put(fileCoord, ib.key());
        }

        return blockMap;
    }

    static ArrayList<IBlock> toBlockList(Map<FileCoord, BlockKey> map) {
        ArrayList<IBlock> blocks = new ArrayList<>(map.size());

        for (Map.Entry<FileCoord, BlockKey> entry : map.entrySet()) {
            FileCoord coord = entry.getKey();

            blocks.add(
                new FileBlock(
                    coord.x(),
                    coord.y(),
                    coord.z(),
                    entry.getValue()
                )
            );
        }

        return blocks;
    }
}
