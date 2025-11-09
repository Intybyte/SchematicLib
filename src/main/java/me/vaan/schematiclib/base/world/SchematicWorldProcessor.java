package me.vaan.schematiclib.base.world;

import me.vaan.schematiclib.base.Rotation;
import me.vaan.schematiclib.base.block.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.block.ICoord;
import me.vaan.schematiclib.base.namespace.NamespaceHandler;
import me.vaan.schematiclib.base.namespace.NamespaceRegistry;
import me.vaan.schematiclib.base.schematic.OffsetSchematic;
import me.vaan.schematiclib.base.schematic.OffsetSchematicImpl;
import me.vaan.schematiclib.base.schematic.Schematic;
import me.vaan.schematiclib.file.block.FileBlock;
import me.vaan.schematiclib.file.schematic.FileSchematic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface SchematicWorldProcessor {
    NamespaceRegistry registry();

    default boolean matches(OffsetSchematic schematic, UUID world) {
        NamespaceRegistry reg = registry();

        if (schematic.areRealBlocksCached()) {
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

        // only compute if needed
        for (IBlock toProcess : schematic.positions()) {
            if (toProcess == null) {
                continue;
            }

            IBlock real = schematic.of(toProcess);
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

    default OffsetSchematic move(OffsetSchematic schematic, ICoord movement) {
        NamespaceRegistry reg = registry();

        int movX = movement.x();
        int movY = movement.y();
        int movZ = movement.z();
        for (IBlock real : schematic.realBlocks()) {
            BlockKey key = real.key();
            NamespaceHandler handler = reg.getNamespaceHandler(key.namespace());
            if (handler == null) continue;

            IBlock newBlock = new FileBlock(
                real.x() + movX,
                real.y() + movY,
                real.z() + movZ,
                key
            );

            handler.move(real, newBlock, key);
        }

        return new OffsetSchematicImpl(
            movX + schematic.x(),
            movY + schematic.y(),
            movZ + schematic.z(),
            schematic
        );
    }

    // all rotations are along Y
    default OffsetSchematic rotate(OffsetSchematic schematic, ICoord center, Rotation rotation) {
        if (rotation == null) {
            return schematic;
        }

        NamespaceRegistry reg = registry();

        int cx = center.x();
        int cy = center.y();
        int cz = center.z();

        List<IBlock> rotated = new ArrayList<>(schematic.positions().size());
        for (IBlock real : schematic.realBlocks()) {
            BlockKey key = real.key();
            NamespaceHandler handler = reg.getNamespaceHandler(key.namespace());
            if (handler == null) continue;

            // Translate block so center is origin
            int relX = real.x() - cx;
            int relZ = real.z() - cz;
            int relY = real.y() - cy; // Y unchanged for horizontal rotation

            int newX, newZ;
            switch (rotation) {
                case LEFT:  // 90° counterclockwise
                    newX = -relZ;
                    newZ = relX;
                    break;
                case RIGHT: // 90° clockwise
                    newX = relZ;
                    newZ = -relX;
                    break;
                case FLIP: // 180°
                    newX = -relX;
                    newZ = -relZ;
                    break;
                default:
                    newX = relX;
                    newZ = relZ;
            }

            // Translate back to world coords
            int finalX = cx + newX;
            int finalY = cy + relY;
            int finalZ = cz + newZ;

            IBlock newBlock = new FileBlock(finalX, finalY, finalZ, key);
            rotated.add(newBlock);
            handler.move(real, newBlock, key);
        }

        // Rotation does not change offset directly unless schematic stores rotation state
        return new OffsetSchematicImpl(
            rotated
        );
    }

    default Schematic parseToMaterial(Schematic schematic) {
        List<IBlock> positions = new ArrayList<>();
        NamespaceRegistry reg = registry();
        for (IBlock block : schematic) {
            if (block == null) {
                continue;
            }

            BlockKey key = reg.toMaterial(block.key());
            positions.add(
                new FileBlock(block.x(), block.y(), block.z(), key)
            );
        }

        return new FileSchematic(positions);
    }

    default List<BlockKey> parseToMaterialList(Schematic schematic) {
        List<IBlock> positions = schematic.positions();
        List<BlockKey> materials = new ArrayList<>(positions.size());
        NamespaceRegistry reg = registry();

        for (IBlock block : positions) {
            if (block == null) {
                continue;
            }

            BlockKey key = reg.toMaterial(block.key());
            materials.add(key);
        }

        return materials;
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
