package me.vaan.schematiclib.base.world;

import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.namespace.NamespaceHandler;
import me.vaan.schematiclib.base.namespace.NamespaceRegistry;
import me.vaan.schematiclib.base.schematic.OffsetSchematic;
import me.vaan.schematiclib.base.schematic.OffsetSchematicImpl;
import me.vaan.schematiclib.base.schematic.Schematic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface SchematicWorldProcessor {
    NamespaceRegistry registry();

    default boolean matches(OffsetSchematic schematic, UUID world) {
        for (IBlock block : schematic) {
            IBlock real = schematic.of(block);
            NamespaceHandler handler = registry().getNamespaceHandler(block.key().namespace());
            if (!handler.matches(real, world)) {
                return false;
            }
        }

        return true;
    }

    default void place(OffsetSchematic schematic, UUID world) {
        for (IBlock block : schematic) {
            IBlock real = schematic.of(block);
            NamespaceHandler handler = registry().getNamespaceHandler(block.key().namespace());
            handler.place(real, world);
        }
    }

    default Schematic schematicOf(int xA, int yA, int zA, int xB, int yB, int zB, UUID world) {
        List<IBlock> positions = new ArrayList<>();
        int xmin = Math.min(xA, xB);
        int ymin = Math.min(yA, yB);
        int zmin = Math.min(zA, zB);

        int xmax = Math.max(xA, xB);
        int ymax = Math.max(yA, yB);
        int zmax = Math.max(zA, zB);

        for (int x = xmin; x <= xmax; x++) {
            for (int y = ymin; y <= ymax; y++) {
                for (int z = zmin; z <= zmax; z++) {
                    IBlock worldBlock = registry().getBlock(x, y, z, world);
                    if (worldBlock != null) positions.add(worldBlock);
                }
            }
        }

        return new OffsetSchematicImpl(positions);
    }
}
