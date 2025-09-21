package me.vaan.schematiclib.base.formats;

import me.vaan.schematiclib.base.schematic.Schematic;

import java.io.File;

public interface SchematicSaver {
    void save(File file, Schematic schematic);
}
