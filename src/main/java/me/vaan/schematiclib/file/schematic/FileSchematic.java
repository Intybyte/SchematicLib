package me.vaan.schematiclib.file.schematic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.schematic.Schematic;

import java.util.ArrayList;
import java.util.List;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class FileSchematic implements Schematic {
    private List<IBlock> positions;

    public FileSchematic() {
        this(new ArrayList<>());
    }
}
