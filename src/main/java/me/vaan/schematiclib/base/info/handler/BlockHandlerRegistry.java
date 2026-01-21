package me.vaan.schematiclib.base.info.handler;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockHandlerRegistry {
    @Getter
    private static final BlockHandlerRegistry instance = new BlockHandlerRegistry();

    private final HashMap<String, HashMap<Class<?>, BlockInfoHandler<?>>> registry = new HashMap<>();
    private final HashMap<Class<?>, List<BlockInfoHandler<?>>> clazzList = new HashMap<>();


    {
        add("facing", (RotationHandler) (blockInfo, rotation) -> {
            String value = blockInfo.info().get("facing");
            if (value == null) return blockInfo;

            RotationHandler.Direction dir = RotationHandler.Direction.fromString(value);
            if (dir == null) return blockInfo;

            RotationHandler.Direction rot = dir.rotate(rotation);

            return blockInfo.toBuilder()
                .add("facing", rot.lowerName())
                .build();
        });
    }

    public void add(String attribute, BlockInfoHandler<?> handler) {
        registry.putIfAbsent(attribute, new HashMap<>());
        registry.get(attribute).put(handler.type(), handler);

        clazzList.putIfAbsent(handler.type(), new ArrayList<>());
        clazzList.get(handler.type()).add(handler);
    }

    @SuppressWarnings("unchecked")
    public <T> BlockInfoHandler<T> get(String attribute, Class<T> clazz) {
        HashMap<Class<?>, BlockInfoHandler<?>> map = registry.get(attribute);
        if (map == null) return null;

        return (BlockInfoHandler<T>) map.get(clazz);
    }

    @SuppressWarnings("unchecked")
    public <T> List<BlockInfoHandler<T>> getAll(Class<T> clazz) {
        List<BlockInfoHandler<?>> list = clazzList.get(clazz);
        if (list == null) return Collections.emptyList();

        return (List<BlockInfoHandler<T>>) (List<?>) list;
    }
}
