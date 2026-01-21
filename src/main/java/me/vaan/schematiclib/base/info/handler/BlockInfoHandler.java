package me.vaan.schematiclib.base.info.handler;

import me.vaan.schematiclib.base.info.BlockInfo;

import java.util.function.BiFunction;

public interface BlockInfoHandler<T> extends BiFunction<BlockInfo, T, BlockInfo> {
    Class<T> type();
}
