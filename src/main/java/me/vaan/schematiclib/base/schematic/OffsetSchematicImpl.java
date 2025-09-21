package me.vaan.schematiclib.base.schematic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.file.block.FileBlock;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class OffsetSchematicImpl implements OffsetSchematic {
    protected int x, y, z;
    protected List<IBlock> positions;

    public OffsetSchematicImpl(List<IBlock> blocks) {
        IBlock first = blocks.get(0);
        this.x = first.x();
        this.y = first.y();
        this.z = first.z();

        for (IBlock entry : blocks) {
            this.x = Math.min(this.x, entry.x());
            this.y = Math.min(this.y, entry.y());
            this.z = Math.min(this.z, entry.z());
        }

        this.positions = new ArrayList<>();
        for (IBlock entry : blocks) {
            positions.add(
                new FileBlock(entry.x() - x, entry.y() - y, entry.z() - z, entry.key())
            );
        }
    }
}
