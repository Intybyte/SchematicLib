package me.vaan.schematiclib.file.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.schematic.Schematic;
import me.vaan.schematiclib.file.schematic.FileSchematic;

import java.lang.reflect.Type;
import java.util.List;

public class SchematicAdapter implements JsonSerializer<Schematic>, JsonDeserializer<Schematic> {
    public static final TypeToken<List<IBlock>> typeToken = new TypeToken<List<IBlock>>(){};

    @Override
    public JsonElement serialize(Schematic src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.positions());
    }

    @Override
    public Schematic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<IBlock> positions = context.deserialize(json, typeToken.getType());
        return new FileSchematic(positions);
    }
}
