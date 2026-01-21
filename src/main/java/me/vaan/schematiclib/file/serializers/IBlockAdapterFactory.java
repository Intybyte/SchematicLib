package me.vaan.schematiclib.file.serializers;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import me.vaan.schematiclib.base.block.IBlock;

public class IBlockAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!IBlock.class.isAssignableFrom(type.getRawType())) {
            return null;
        }

        return (TypeAdapter<T>) gson.getDelegateAdapter(
            this,
            TypeToken.get(IBlock.class)
        );
    }
}