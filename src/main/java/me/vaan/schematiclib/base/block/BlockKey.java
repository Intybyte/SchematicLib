package me.vaan.schematiclib.base.block;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Locale;
import java.util.regex.Pattern;

@Getter
@Accessors(fluent = true)
public class BlockKey {
    public static final Pattern NAMESPACED_KEY_PATTERN = Pattern.compile("^[a-z0-9._-]+:[a-z0-9._-]+$");
    private final String namespace;
    private final String key;

    public BlockKey(String namespace, String key) {
        this.namespace = namespace.toLowerCase(Locale.ROOT);
        this.key = key.toLowerCase(Locale.ROOT);

        if (!NAMESPACED_KEY_PATTERN.matcher(this.full()).matches()) {
            throw new IllegalArgumentException("Invalid NS key: " + this.full());
        }
    }

    public static BlockKey mc(String key) {
        return new BlockKey("minecraft", key);
    }

    public String full() {
        return namespace + ":" + key;
    }

    public boolean matches(String s) {
        return this.full().equals(s);
    }
}
