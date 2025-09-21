package me.vaan.schematiclib.file.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import me.vaan.schematiclib.base.block.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.file.block.FileBlock;

import java.lang.reflect.Type;

public class IBlockAdapter implements JsonSerializer<IBlock>, JsonDeserializer<IBlock> {
    @Override
    public JsonElement serialize(IBlock block, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        json.addProperty("x", block.x());
        json.addProperty("y", block.y());
        json.addProperty("z", block.z());
        json.add("key", context.serialize(block.key()));

        return json;
    }

    @Override
    public IBlock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        int x = json.get("x").getAsInt();
        int y = json.get("y").getAsInt();
        int z = json.get("z").getAsInt();
        BlockKey key = context.deserialize(json.get("key"), BlockKey.class);

        return new FileBlock(x, y, z, key);
    }
}
