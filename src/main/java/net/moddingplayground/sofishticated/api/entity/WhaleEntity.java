package net.moddingplayground.sofishticated.api.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.world.World;

public class WhaleEntity extends DolphinEntity {
    public WhaleEntity(EntityType<? extends DolphinEntity> entityType, World world) {
        super(entityType, world);
    }
}
