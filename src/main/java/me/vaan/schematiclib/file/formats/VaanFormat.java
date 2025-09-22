package me.vaan.schematiclib.file.formats;

import com.google.gson.FormattingStyle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.vaan.schematiclib.base.block.BlockKey;
import me.vaan.schematiclib.base.block.IBlock;
import me.vaan.schematiclib.base.formats.SchematicLoader;
import me.vaan.schematiclib.base.formats.SchematicSaver;
import me.vaan.schematiclib.base.schematic.Schematic;
import me.vaan.schematiclib.file.block.FileCoord;
import me.vaan.schematiclib.file.serializers.BlockKeyAdapter;
import me.vaan.schematiclib.file.serializers.IBlockAdapter;
import me.vaan.schematiclib.file.serializers.SchematicAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/*
 * Base schematic backed by a zipped json list of IBlocks (x,y,z,key)
 */
public class VaanFormat implements SchematicLoader, SchematicSaver {
    public static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(BlockKey.class, new BlockKeyAdapter())
        .registerTypeAdapter(IBlock.class, new IBlockAdapter())
        .registerTypeAdapter(Schematic.class, new SchematicAdapter())
        .setFormattingStyle(FormattingStyle.COMPACT)
        .create();


    public Schematic load(File file) throws Throwable {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(file.toPath()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(file.getName())) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = zis.read(buffer)) != -1) {
                        baos.write(buffer, 0, read);
                    }
                    String json = baos.toString("UTF-8");
                    return GSON.fromJson(json, Schematic.class);
                }

                zis.closeEntry();
            }
        }

        throw new FileAlreadyExistsException("End with fail");
    }

    public void save(File file, Schematic schematic) throws Throwable {
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(file.toPath()))) {
            //move to 0,0,0 and save
            String json = GSON.toJson(schematic.moveOrigin(new FileCoord()), Schematic.class);
            byte[] data = json.getBytes(StandardCharsets.UTF_8);

            ZipEntry entry = new ZipEntry(file.getName());
            zos.putNextEntry(entry);
            zos.write(data);
            zos.closeEntry();
        }
    }
}