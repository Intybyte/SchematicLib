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
        NamespaceRegistry reg = registry();
        for (IBlock real : schematic.realBlocks()) {
            if (real == null) {
                continue;
            }

            if (!reg.matches(real, world)) {
                return false;
            }
        }

        return true;
    }

    default void place(OffsetSchematic schematic, UUID world) {
        NamespaceRegistry reg = registry();
        for (IBlock real : schematic.realBlocks()) {
            if (real == null) {
                continue;
            }

            reg.setBlock(real, world);
        }
    }

    default void destroy(OffsetSchematic schematic, UUID world) {
        NamespaceRegistry reg = registry();
        for (IBlock real : schematic.realBlocks()) {
            if (real == null) {
                continue;
            }

            reg.removeBlock(real.x(), real.y(), real.z(), world);
        }
    }

    default void breakNaturally(OffsetSchematic schematic, UUID world) {
        NamespaceRegistry reg = registry();
        for (IBlock real : schematic.realBlocks()) {
            if (real == null) {
                continue;
            }

            reg.breakBlock(real.x(), real.y(), real.z(), world);
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
