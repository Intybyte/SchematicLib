package me.vaan.schematiclib.file.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import me.vaan.schematiclib.base.block.BlockKey;

import java.lang.reflect.Type;

public class BlockKeyAdapter implements JsonSerializer<BlockKey>, JsonDeserializer<BlockKey> {

    @Override
    public JsonElement serialize(BlockKey src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.full());
    }

    @Override
    public BlockKey deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonPrimitive() || !json.getAsJsonPrimitive().isString()) {
            throw new JsonParseException("BlockKey must be a string in format namespace:key");
        }

        String fullKey = json.getAsString();
        String[] parts = fullKey.split(":");

        if (parts.length != 2) {
            throw new JsonParseException("Invalid BlockKey format: " + fullKey);
        }

        return new BlockKey(parts[0], parts[1]);
    }
}