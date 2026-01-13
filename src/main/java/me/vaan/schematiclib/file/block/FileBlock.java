package me.vaan.schematiclib.file.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class FileBlock implements IBlock {
    private final int x, y, z;
    private final BlockKey key;

    @Override
    public IBlock addClone(int x, int y, int z) {
        return new FileBlock(
                this.x + x,
                this.y + y,
                this.z + z,
                key
        );
    }
}
