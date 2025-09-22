package me.vaan.schematiclib.file.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.block.ICoord;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class FileCoord implements ICoord {
    private final int x, y, z;

    public FileCoord() {
        this(0, 0, 0);
    }
}
