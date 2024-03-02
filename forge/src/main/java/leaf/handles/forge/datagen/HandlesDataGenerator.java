/*
 * File updated ~ 7 - 8 - 2023 ~ Leaf
 */

package leaf.handles.forge.datagen;

import leaf.handles.HandlesMod;
import leaf.handles.forge.datagen.patchouli.HandlesPatchouliGen;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = HandlesMod.MOD_ID, bus = Bus.MOD)
public class HandlesDataGenerator
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		generator.addProvider(true, new HandlesPatchouliGen(generator));

		//generator.addProvider(true, new HandlesEngLangGen(generator));
		//generator.addProvider(true, new HandlesItemModelsGen(generator, existingFileHelper));
		//generator.addProvider(true, new HandlesRecipeGen(generator));
		//generator.addProvider(true, new HandlesAdvancementGen(generator));
		//generator.addProvider(true, new HandlesTagProvider(generator, existingFileHelper));
	}

}