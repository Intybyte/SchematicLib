package me.vaan.schematiclib.file.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.info.BlockInfo;
import me.vaan.schematiclib.base.key.BlockKey;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class FileBlock implements IBlock {
    private final int x, y, z;
    private final BlockInfo info;

    public FileBlock(int x, int y, int z, BlockKey key) {
        this(x, y, z, new BlockInfo(key));
    }

    @Override
    public IBlock addClone(int x, int y, int z) {
        return new FileBlock(
                this.x + x,
                this.y + y,
                this.z + z,
                info
        );
    }
}
