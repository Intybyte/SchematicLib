package me.vaan.schematiclib.base.formats;

import me.vaan.schematiclib.base.schematic.Schematic;

import java.io.File;

public interface SchematicLoader {
    Schematic load(File file) throws Throwable;
}
