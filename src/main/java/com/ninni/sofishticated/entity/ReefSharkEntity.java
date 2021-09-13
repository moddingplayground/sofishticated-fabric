package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.entity.base.TiltingFishEntity;
import com.ninni.sofishticated.init.SofishticatedItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ReefSharkEntity extends TiltingFishEntity {
    public ReefSharkEntity(EntityType<? extends TiltingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(SofishticatedItems.REEF_SHARK_BUCKET);
    }
}
