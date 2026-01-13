package me.vaan.schematiclib.base.schematic;

import lombok.Getter;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;
import me.vaan.schematiclib.file.block.FileBlock;
import me.vaan.schematiclib.file.block.FileCoord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
@Deprecated
public class ConstantOffsetSchematic implements IConstantOffsetSchematic {
    protected final int x, y, z;
    protected final List<IBlock> positions;
    protected final Map<FileCoord, BlockKey> blockMap;
    protected final Schematic realBlocks;

    protected final ICoord max, min;

    public ConstantOffsetSchematic(int x, int y, int z, List<IBlock> positions) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.positions = positions;

        //calculate constants
        this.realBlocks = IConstantOffsetSchematic.super.realBlocks();
        this.blockMap = Schematic.toBlockMap(positions);
        this.max = realBlocks.getMax();
        this.min = realBlocks.getMin();
    }

    public ConstantOffsetSchematic(List<IBlock> blocks) {
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

        //calculate constants
        this.realBlocks = IConstantOffsetSchematic.super.realBlocks();
        this.blockMap = Schematic.toBlockMap(positions);
        this.max = realBlocks.getMax();
        this.min = realBlocks.getMin();
    }


    @Override
     public boolean contains(FileCoord coord) {
        // optimize search as we already know max and min
        if (!coord.within(min, max)) return false;

        return IConstantOffsetSchematic.super.contains(coord);
    }

    public List<IBlock> positions() {
        return Collections.unmodifiableList(positions);
    }
}
