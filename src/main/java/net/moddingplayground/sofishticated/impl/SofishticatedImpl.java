package net.moddingplayground.sofishticated.impl;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;
import net.moddingplayground.sofishticated.api.entity.variant.SeahorseVariants;
import net.moddingplayground.sofishticated.api.entity.variant.ShrimpVariants;
import net.moddingplayground.sofishticated.api.item.SofishticatedItemGroups;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;
import net.moddingplayground.sofishticated.api.sound.SofishticatedSoundEvents;

public final class SofishticatedImpl implements Sofishticated, ModInitializer {
	private final InitializationLogger initializer;

	public SofishticatedImpl() {
		this.initializer = new InitializationLogger(LOGGER, MOD_NAME);
	}

	@Override
	public void onInitialize() {
		this.initializer.start();

		// initialize in separate calls to ensure item registry order
		this.initialize(
			SofishticatedRegistry.class, ShrimpVariants.class, SeahorseVariants.class,
			SofishticatedEntityType.class, SofishticatedSoundEvents.class, SofishticatedItemGroups.class, SofishticatedItems.class
		);

		this.initializer.finish();
	}

	@SuppressWarnings("UnstableApiUsage")
	private void initialize(Class<?>... classes) {
		for (Class<?> clazz : classes) Reflection.initialize(clazz);
	}
}
