package implementation;

import me.vaan.schematiclib.base.namespace.NamespaceRegistry;
import me.vaan.schematiclib.base.world.SchematicWorldProcessor;

public class SchematicWorldProcessorDummy implements SchematicWorldProcessor {
    private final NamespaceRegistry registry = new NamespaceRegistry(
        "minecraft", new NamespaceHandlerDummy()
        );

    @Override
    public NamespaceRegistry registry() {
        return registry;
    }
}
