package me.vaan.schematiclib.base.info;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.base.key.BlockKeyHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@AllArgsConstructor
public class BlockInfo implements BlockKeyHolder {
    private final BlockKey key;
    private final Map<String, String> info;

    public BlockInfo(BlockKey key) {
        this(key, new HashMap<>());
    }

    public Map<String, String> info() {
        return Collections.unmodifiableMap(info);
    }

    public BlockInfoBuilder toBuilder() {
        return new BlockInfoBuilder(key, info);
    }

    public static BlockInfoBuilder builder() {
        return new BlockInfoBuilder();
    }

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    public static class BlockInfoBuilder {
        private BlockKey key;
        private Map<String, String> info = null;

        BlockInfoBuilder() {}

        public BlockInfoBuilder key(BlockKey key) {
            this.key = key;
            return this;
        }

        public BlockInfoBuilder add(String key, String value) {
            if (info == null) {
                info = new HashMap<>();
            }

            info.put(key, value);
            return this;
        }

        public BlockInfoBuilder info(Map<String, String> info) {
            this.info = info;
            return this;
        }

        public BlockInfo build() {
            return new BlockInfo(this.key, this.info);
        }
    }
}
