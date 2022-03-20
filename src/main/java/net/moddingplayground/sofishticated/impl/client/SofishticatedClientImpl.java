package net.moddingplayground.sofishticated.impl.client;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.client.model.SofishticatedEntityModelLayers;

@Environment(EnvType.CLIENT)
public final class SofishticatedClientImpl implements Sofishticated, ClientModInitializer {
    private final InitializationLogger initializer;

    public SofishticatedClientImpl() {
        this.initializer = new InitializationLogger(LOGGER, MOD_NAME, EnvType.CLIENT);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitializeClient() {
        this.initializer.start();

        Reflection.initialize(SofishticatedEntityModelLayers.class);

        this.initializer.finish();
    }
}
