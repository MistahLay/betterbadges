package com.lay.betterbadges;

import com.lay.betterbadges.datagen.ModItemTagProvider;
import com.lay.betterbadges.datagen.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BetterBadgesDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModModelProvider::new);
	}

	public void registerBadge(){

	}
}
