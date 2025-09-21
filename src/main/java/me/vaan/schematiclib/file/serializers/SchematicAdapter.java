package me.vaan.schematiclib.file.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.schematic.Schematic;
import me.vaan.schematiclib.file.schematic.FileSchematic;

import java.lang.reflect.Type;
import java.util.List;

public class SchematicAdapter implements JsonSerializer<Schematic>, JsonDeserializer<Schematic> {
    @Override
    public JsonElement serialize(Schematic src, Type typeOfSrc, JsonSerializationContext context) {
        // Serialize the positions list as JSON array
        return context.serialize(src.positions());
    }

    @Override
    public Schematic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Deserialize JSON array back to List<IBlock>
        List<IBlock> positions = context.deserialize(json, new com.google.gson.reflect.TypeToken<List<IBlock>>(){}.getType());
        // Return a new FileSchematic with the positions
        return new FileSchematic(positions);
    }
}
