package me.vaan.schematiclib.base.info;

import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.base.key.BlockKeyHolder;

public interface BlockInfoHolder extends BlockKeyHolder {
    BlockInfo info();

    @Override
    default BlockKey key() {
        return info().key();
    }
}
