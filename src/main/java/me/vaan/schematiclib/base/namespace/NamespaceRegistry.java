package me.vaan.schematiclib.base.namespace;

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
}
