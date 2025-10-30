package me.vaan.schematiclib.base.schematic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.block.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.file.block.FileBlock;
import me.vaan.schematiclib.file.block.FileCoord;
import me.vaan.schematiclib.file.schematic.FileSchematic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Accessors(fluent = true)
/**
 * Do not use getPosition and add entries, it will lose of consistency, consider this a constant
 */
public class OffsetSchematicImpl implements OffsetSchematic {
    protected final int x, y, z;
    protected final List<IBlock> positions;
    protected FileSchematic realBlocks = null;
    protected final Map<FileCoord, BlockKey> blockMap;

    public OffsetSchematicImpl(int x, int y, int z, List<IBlock> positions) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.positions = positions;
        this.blockMap = Schematic.toBlockMap(positions);
    }

    public OffsetSchematicImpl(int x, int y, int z, Map<FileCoord, BlockKey> blockMap) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.positions = Schematic.toBlockList(blockMap);
        this.blockMap = blockMap;
    }

    public OffsetSchematicImpl(int x, int y, int z, Schematic schem) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.positions = schem.positions();
        this.blockMap = schem.blockMap();
    }

    public OffsetSchematicImpl(List<IBlock> blocks) {
        int z1;
        int y1;
        int x1;
        IBlock first = blocks.get(0);
        x1 = first.x();
        y1 = first.y();
        z1 = first.z();

        for (IBlock entry : blocks) {
            x1 = Math.min(x1, entry.x());
            y1 = Math.min(y1, entry.y());
            z1 = Math.min(z1, entry.z());
        }

        this.z = z1;
        this.y = y1;
        this.x = x1;
        this.positions = new ArrayList<>();
        for (IBlock entry : blocks) {
            positions.add(
                new FileBlock(entry.x() - x, entry.y() - y, entry.z() - z, entry.key())
            );
        }
        this.blockMap = Schematic.toBlockMap(positions);
    }

    @Override
    public Schematic realBlocks() {
        if (realBlocks == null) {
            List<IBlock> blocks = new ArrayList<>(positions.size());

            for (IBlock block : positions) {
                blocks.add(of(block));
            }

            realBlocks = new FileSchematic(blocks);
        }

        return realBlocks;
    }

    @Override
    public boolean areRealBlocksCached() {
        return realBlocks != null;
    }
}
