package me.vaan.schematiclib.file.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.info.BlockInfo;
import me.vaan.schematiclib.base.key.BlockKey;
import me.vaan.schematiclib.file.block.FileBlock;

import java.lang.reflect.Type;
import java.util.Map;

public class IBlockAdapter implements JsonSerializer<IBlock>, JsonDeserializer<IBlock> {
    private static final Type mapType = new TypeToken<Map<String, String>>(){}.getType();

    @Override
    public JsonElement serialize(IBlock block, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        json.addProperty("x", block.x());
        json.addProperty("y", block.y());
        json.addProperty("z", block.z());
        json.add("key", context.serialize(block.key()));
        json.add("info", context.serialize(block.info().info(), mapType));

        return json;
    }

    @Override
    public IBlock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        int x = json.get("x").getAsInt();
        int y = json.get("y").getAsInt();
        int z = json.get("z").getAsInt();
        BlockKey key = context.deserialize(json.get("key"), BlockKey.class);

        if (!json.has("info")) {
            return new FileBlock(x, y, z, key);
        }

        Map<String, String> info = context.deserialize(json.get("info"), mapType);
        return new FileBlock(x, y, z, new BlockInfo(key, info));
    }
}
