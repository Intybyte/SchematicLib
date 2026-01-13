package me.vaan.schematiclib.base.namespace;

import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;

import java.util.UUID;

public interface NamespaceHandler {
    /**
     * Place the relative block and process internal custom defined blockstate if necessary
     */
    void place(IBlock block, UUID world);

    /**
     * Gets the block
     */
    IBlock get(int x, int y, int z, UUID world);

    /**
     * Removes the block without dropping anything, meant for commands
     */
    void destroy(ICoord coord, UUID world);

    /**
     * Removes the block without dropping the relative block
     */
    void breakNaturally(IBlock block, UUID world);

    /**
     * For custom blocks gets their material key if any
     */
    BlockKey toMaterial(BlockKey custom);
}
