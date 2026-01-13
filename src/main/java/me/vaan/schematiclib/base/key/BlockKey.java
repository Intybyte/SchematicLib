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
        this.namespace = namespace;
        this.key = key;

        if (this.namespace == null || this.namespace.isEmpty()) {
            throw new UnsupportedOperationException("Namespace must not be empty");
        }

        if (this.key == null || this.key.isEmpty()) {
            throw new UnsupportedOperationException("Key must not be empty");
        }
    }

    public static BlockKey mc(String key) {
        return new BlockKey("minecraft", key);
    }

    public static BlockKey fromString(String fullKey) {
        int index = fullKey.indexOf(':');
        if (index == -1) {
            throw new UnsupportedOperationException("Invalid NS keys, didn't find any ':'");
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
