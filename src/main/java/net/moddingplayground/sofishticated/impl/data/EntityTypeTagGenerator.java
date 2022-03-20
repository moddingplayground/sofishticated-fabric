package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;

import static net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType.*;

public class EntityTypeTagGenerator extends AbstractTagGenerator<EntityType<?>> {
    public EntityTypeTagGenerator(String modId) {
        super(modId, Registry.ENTITY_TYPE);
    }

    @Override
    public void generate() {
        this.add(EntityTypeTags.AXOLOTL_HUNT_TARGETS, SHRIMP);
    }
}
