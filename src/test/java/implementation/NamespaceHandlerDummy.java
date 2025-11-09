package implementation;

import me.vaan.schematiclib.base.block.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;
import me.vaan.schematiclib.base.namespace.NamespaceHandler;

import java.util.UUID;

public class NamespaceHandlerDummy implements NamespaceHandler {
    @Override
    public void place(IBlock block, UUID world) {

    }

    @Override
    public IBlock get(int x, int y, int z, UUID world) {
        return null;
    }

    @Override
    public void destroy(IBlock block, UUID world) {

    }

    @Override
    public void breakNaturally(IBlock block, UUID world) {

    }

    @Override
    public void move(ICoord from, ICoord to, BlockKey key) {

    }

    @Override
    public BlockKey toMaterial(BlockKey custom) {
        return null;
    }
}
