package leaf.handles.registries;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import leaf.handles.HandlesMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemRegistry
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(HandlesMod.MOD_ID, Registry.ITEM_REGISTRY);

	// Registering a new creative tab
	public static final CreativeModeTab MAIN_TAB =
			CreativeTabRegistry.create(
					new ResourceLocation(HandlesMod.MOD_ID, "main_tab"),
					() -> new ItemStack(BlockRegistry.FEZ.get())
			);
}
