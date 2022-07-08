package net.creeperhost.chickens.fabric;

import net.creeperhost.chickens.Chickens;
import net.fabricmc.api.ModInitializer;

public class ChickensModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Chickens.init();
    }
}
