import implementation.SchematicWorldProcessorDummy;
import me.vaan.schematiclib.base.Rotation;
import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.schematic.OffsetSchematic;
import me.vaan.schematiclib.base.schematic.OffsetSchematicImpl;
import me.vaan.schematiclib.base.world.SchematicWorldProcessor;
import me.vaan.schematiclib.file.block.FileBlock;
import me.vaan.schematiclib.file.block.FileCoord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovementTests {

    @Test
    void movement() {
        SchematicWorldProcessor swp = new SchematicWorldProcessorDummy();

        BlockKey key = BlockKey.mc("nada");
        List<IBlock> blockList = list(
            new FileBlock(0, 0, 0, key),
            new FileBlock(1, 0, 0, key),
            new FileBlock(2, 0, 0, key)
        );

        OffsetSchematic schematic = new OffsetSchematicImpl(blockList);
        OffsetSchematic schemMoved = swp.move(schematic, new FileCoord(5, 5, 5), null);

        assertTrue(schemMoved.contains(new FileCoord(5, 5, 5))); // 0,0,0 -> 5,5,5

        //blocksReal.forEach(it -> System.out.println(" Coords: " + it.x() + " " + it.y() + " " + it.z()));
    }

    @Test
    void rotate() {
        SchematicWorldProcessor swp = new SchematicWorldProcessorDummy();

        BlockKey key = BlockKey.mc("nada");
        List<IBlock> blockList = list(
            new FileBlock(0, 0, 0, key),
            new FileBlock(1, 0, 0, key),
            new FileBlock(2, 0, 0, key)
        );

        OffsetSchematic schematic = new OffsetSchematicImpl(blockList);
        OffsetSchematic schemMoved = swp.rotate(
            schematic,
            new FileCoord(0, 0, 0),
            Rotation.LEFT,
            null
        );

        schemMoved.realBlocks().forEach(it -> System.out.println(" Coords: " + it.x() + " " + it.y() + " " + it.z()));
    }

    @SafeVarargs
    final <T> List<T> list(T... args) {
        ArrayList<T> list = new ArrayList<>(args.length);
        list.addAll(Arrays.asList(args));

        return list;
    }
}
