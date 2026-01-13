package implementation;

import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;
import me.vaan.schematiclib.base.namespace.NamespaceHandler;
import me.vaan.schematiclib.file.block.FileBlock;

import java.util.UUID;

public class NamespaceHandlerDummy implements NamespaceHandler {
    @Override
    public void place(IBlock block, UUID world) {

    }

    @Override
    public IBlock get(int x, int y, int z, UUID world) {
        return new FileBlock(x, y, z, BlockKey.mc("moved_generic"));
    }

    @Override
    public void destroy(ICoord coord, UUID world) {

    }

    @Override
    public void breakNaturally(IBlock block, UUID world) {

    }

    @Override
    public BlockKey toMaterial(BlockKey custom) {
        return null;
    }
}
