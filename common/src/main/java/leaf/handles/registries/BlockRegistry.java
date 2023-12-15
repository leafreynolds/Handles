package leaf.handles.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import leaf.handles.HandlesMod;
import leaf.handles.blocks.AntennaBlock;
import leaf.handles.blocks.FezBlock;
import leaf.handles.blocks.WearableItemBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class BlockRegistry
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(HandlesMod.MOD_ID, Registry.BLOCK_REGISTRY);

	public static final RegistrySupplier<FezBlock> FEZ =
			register("fez",
					() ->
							new FezBlock(BlockBehaviour.Properties
									.of(Material.BAMBOO)
									.noOcclusion()
									.strength(1)
									.sound(SoundType.BAMBOO)),
					true,
					false);

	public static final RegistrySupplier<BlockItem> FEZ_ITEM =
			ItemRegistry.ITEMS.register("fez", () ->
			new WearableItemBlock(
					FEZ.get(),
					new Item.Properties().tab(ItemRegistry.MAIN_TAB),
					EquipmentSlot.HEAD)
			);
	public static final RegistrySupplier<AntennaBlock> ANTENNA =
			register("antenna",
					() -> new AntennaBlock(BlockBehaviour.Properties
									.of(Material.METAL)
									.noOcclusion()
									.strength(1)
									.sound(SoundType.METAL)),
					true,
					false);

	public static final RegistrySupplier<BlockItem> ANTENNA_ITEM =
			ItemRegistry.ITEMS.register("antenna", () ->
			new WearableItemBlock(
					ANTENNA.get(),
					new Item.Properties().tab(ItemRegistry.MAIN_TAB),
					EquipmentSlot.HEAD)
			);

	private static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> blockSupplier, boolean addToTab, boolean registerItem)
	{
		RegistrySupplier<T> registryObject = BLOCKS.register(id, blockSupplier);
		if (registerItem)
		{
			if (addToTab)
			{
				ItemRegistry.ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties().tab(ItemRegistry.MAIN_TAB)));
			}
			else
			{
				ItemRegistry.ITEMS.register(id, () -> new BlockItem(registryObject.get(), new Item.Properties()));
			}
		}
		return registryObject;
	}
}
