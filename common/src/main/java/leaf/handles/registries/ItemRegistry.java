package leaf.handles.registries;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import leaf.handles.HandlesMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemRegistry
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(HandlesMod.MOD_ID, Registries.ITEM);
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(HandlesMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

	// Registering a new creative tab
	public static final RegistrySupplier<CreativeModeTab> MAIN_TAB =
			TABS.register("main_tab", () ->
					CreativeTabRegistry.create(
							builder -> builder
									.title(Component.translatable("itemGroup.handles.main_tab"))
									.icon(() -> new ItemStack(BlockRegistry.FEZ.get()))
									.displayItems((params, output) -> {
										output.accept(BlockRegistry.FEZ.get());
										output.accept(BlockRegistry.ANTENNA.get());
									})
					)
			);
}
