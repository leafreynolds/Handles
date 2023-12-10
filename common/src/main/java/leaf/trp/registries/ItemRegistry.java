package leaf.trp.registries;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import leaf.trp.TRPMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemRegistry
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(TRPMod.MOD_ID, Registry.ITEM_REGISTRY);

	// Registering a new creative tab
	public static final CreativeModeTab MAIN_TAB =
			CreativeTabRegistry.create(
					new ResourceLocation(TRPMod.MOD_ID, "main_tab"),
					() -> new ItemStack(BlockRegistry.FEZ.get())
			);
}
