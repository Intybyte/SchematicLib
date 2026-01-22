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
import java.util.Iterator;
import java.util.Map;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class BlockInfo implements BlockKeyHolder {
    private final BlockKey key;
    private final Map<String, String> info;

    public BlockInfo(BlockKey key) {
        this(key, null);
    }

    public BlockInfo(BlockKey key, Map<String, String> info) {
        this.key = key;
        this.info = info == null ? Collections.emptyMap() : Collections.unmodifiableMap(info);
    }

    /**
     *
     * @param fullData format of namespace:key[entry=value,entry2=value2]
     */
    public static BlockInfo of(String fullData) {
        int start = fullData.indexOf('[');
        int end = fullData.indexOf(']');

        // cases with no brackets
        if (start == -1 && end == -1) {
            return new BlockInfo(BlockKey.fromString(fullData));
        }

        HashMap<String, String> attributes = new HashMap<>();

        // invalid brackets
        if (start == -1 || end == -1 || end < start) {
            throw new IllegalArgumentException("Invalid block data format: " + fullData);
        }

        String keyString = fullData.substring(0, start);
        BlockKey key = BlockKey.fromString(keyString);

        String properties = fullData.substring(start + 1, end);
        String[] pairs = properties.split(",");

        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                attributes.put(kv[0], kv[1]);
            }
        }

        return new BlockInfo(key, attributes);
    }

    public BlockInfoBuilder toBuilder() {
        if (info.isEmpty()) {
            return new BlockInfoBuilder(key, new HashMap<>());
        }

        return new BlockInfoBuilder(key, new HashMap<>(info));
    }

    public static BlockInfoBuilder builder() {
        return new BlockInfoBuilder();
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(key.full());

        if (info == null || info.isEmpty()) {
            return sb.toString();
        }

        sb.append('[');
        Iterator<Map.Entry<String, String>> it = info.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            sb.append(entry.getKey())
                .append('=')
                .append(entry.getValue());

            if (it.hasNext()) {
                sb.append(',');
            }
        }

        sb.append(']');

        return sb.toString();
    }

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    public static class BlockInfoBuilder {
        private BlockKey key;
        private Map<String, String> info = null;

        BlockInfoBuilder() {
        }

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
