package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.sofishticated.api.Sofishticated;

import static net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType.*;

public class EntityTypeTagGenerator extends AbstractTagGenerator<EntityType<?>> {
    public EntityTypeTagGenerator() {
        super(Sofishticated.MOD_ID, Registry.ENTITY_TYPE);
    }

    @Override
    public void generate() {
        this.add(EntityTypeTags.AXOLOTL_HUNT_TARGETS, SHRIMP);
    }
}
