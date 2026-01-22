package me.vaan.schematiclib.base.key;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class BlockKey {
    private final String namespace;
    private final String key;

    public BlockKey(String namespace, String key) {
        if (namespace == null || namespace.isEmpty()) {
            throw new IllegalArgumentException("Namespace must not be empty");
        }

        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key must not be empty");
        }

        if (namespace.contains("[") || namespace.contains("]") || key.contains("[") || key.contains("]")) {
            throw new IllegalArgumentException("Key must not have '[' or ']' characters");
        }

        this.namespace = namespace;
        this.key = key;
    }

    public static BlockKey mc(String key) {
        return new BlockKey("minecraft", key);
    }

    public static BlockKey fromString(String fullKey) {
        int index = fullKey.indexOf(':');
        if (index == -1) {
            throw new IllegalArgumentException("Invalid NS keys, didn't find any ':'");
        }

        String namespace = fullKey.substring(0, index);
        String key = fullKey.substring(index + 1);

        return new BlockKey(namespace, key);
    }

    public String full() {
        return namespace + ":" + key;
    }

    public boolean matches(String s) {
        return this.full().equals(s);
    }
}
