package me.vaan.schematiclib.file.schematic;

import lombok.Getter;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.block.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.schematic.Schematic;
import me.vaan.schematiclib.file.block.FileCoord;

import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class FileSchematic implements Schematic {
    protected List<IBlock> positions;
    protected Map<FileCoord, BlockKey> blockMap;

    public FileSchematic(List<IBlock> positions) {
        this.positions = positions;
        this.blockMap = Schematic.toBlockMap(positions);
    }

    public FileSchematic(Map<FileCoord, BlockKey> blockMap) {
        this.blockMap = blockMap;
    }

    public FileSchematic(Schematic schematic) {
        this.positions = schematic.positions();
        this.blockMap = schematic.blockMap();
    }
}
