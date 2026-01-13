package me.vaan.schematiclib.base.namespace;

import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NamespaceRegistry {
    protected Map<String, NamespaceHandler> registry = new HashMap<>();
    private final String defaultNamespace;
    protected final NamespaceHandler defaultHandler;

    public NamespaceRegistry(String defaultNamespace, NamespaceHandler defaultHandler) {
        this.defaultNamespace = defaultNamespace;
        this.defaultHandler = defaultHandler;
    }

    public void registerNamespaceHandler(String namespace, NamespaceHandler predicate) {
        namespace = namespace.toLowerCase();
        if (namespace.equalsIgnoreCase(defaultNamespace)) return;
        registry.put(namespace, predicate);
    }

    public NamespaceHandler getNamespaceHandler(String namespace) {
        namespace = namespace.toLowerCase();
        if (namespace.equalsIgnoreCase(defaultNamespace)) {
            return defaultHandler;
        }

        return registry.get(namespace);
    }

    public IBlock getBlock(int x, int y, int z, UUID world) {
        for (NamespaceHandler handler : registry.values()) {
            IBlock block = handler.get(x, y, z, world);
            if (block != null) return block;
        }

        return defaultHandler.get(x, y, z, world);
    }

    public boolean matches(IBlock target, UUID world) {
        IBlock worldBlock = getBlock(target.x(), target.y(), target.z(), world);
        return target.matches(worldBlock);
    }

    public void removeBlock(int x, int y, int z, UUID world) {
        IBlock old = getBlock(x, y, z, world);
        NamespaceHandler oldHandler = getNamespaceHandler(old.key().namespace());
        oldHandler.destroy(old, world);
    }

    public void breakBlock(int x, int y, int z, UUID world) {
        IBlock old = getBlock(x, y, z, world);
        NamespaceHandler oldHandler = getNamespaceHandler(old.key().namespace());
        oldHandler.breakNaturally(old, world);
    }

    public void setBlock(IBlock block, UUID world) {
        removeBlock(block.x(), block.y(), block.z(), world);

        NamespaceHandler newHandler = getNamespaceHandler(block.key().namespace());
        newHandler.place(block, world);
    }

    public BlockKey toMaterial(BlockKey key) {
        // nothing to do
        String namespace = key.namespace();
        if (namespace.equals(defaultNamespace)) return key;

        NamespaceHandler namespaceHandler = getNamespaceHandler(namespace);
        return namespaceHandler.toMaterial(key);
    }
}
