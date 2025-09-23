package me.vaan.schematiclib.base.block;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Locale;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class BlockKey {
    private final String namespace;
    private final String key;

    public BlockKey(String namespace, String key) {
        this.namespace = namespace;
        this.key = key;

        if (!isOneSeparator(this.full())) {
            throw new UnsupportedOperationException("Invalid NS keys, found more than 1 ':'");
        }
    }

    public static BlockKey mc(String key) {
        return new BlockKey("minecraft", key);
    }

    public static BlockKey fromString(String key) {
        if (!isOneSeparator(key)) {
            throw new UnsupportedOperationException("Invalid NS keys, found more than 1 ':'");
        }

        String[] split = key.split(":");
        return new BlockKey(split[0], split[1]);
    }

    public String full() {
        return namespace + ":" + key;
    }

    public boolean matches(String s) {
        return this.full().equals(s);
    }

    private static boolean isOneSeparator(String str) {
        return (str.length() - str.replace(":", "").length()) == 1;
    }
}
