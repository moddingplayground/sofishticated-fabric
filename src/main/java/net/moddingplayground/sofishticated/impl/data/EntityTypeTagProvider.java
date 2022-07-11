package net.moddingplayground.sofishticated.impl.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.EntityTypeTags;

import static net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType.*;

class EntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTypeTagProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generateTags() {
        this.getOrCreateTagBuilder(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(SHRIMP);
    }
}
