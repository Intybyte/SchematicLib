package me.vaan.schematiclib.file.schematic;

import lombok.Getter;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.schematic.Schematic;
import me.vaan.schematiclib.file.block.FileCoord;

import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class FileSchematic implements Schematic {
    protected final List<IBlock> positions;
    protected final Map<FileCoord, BlockKey> blockMap;

    public FileSchematic(List<IBlock> positions) {
        this.positions = positions;
        this.blockMap = Schematic.toBlockMap(positions);
    }

    public FileSchematic(Map<FileCoord, BlockKey> blockMap) {
        this.positions = Schematic.toBlockList(blockMap);
        this.blockMap = blockMap;
    }

    public FileSchematic(Schematic schematic) {
        this.positions = schematic.positions();
        this.blockMap = schematic.blockMap();
    }
}
