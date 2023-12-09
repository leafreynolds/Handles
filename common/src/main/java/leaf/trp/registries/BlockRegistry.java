package leaf.trp.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import leaf.trp.TRPMod;
import leaf.trp.blocks.FezBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class BlockRegistry
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(TRPMod.MOD_ID, Registry.BLOCK_REGISTRY);

	public static final RegistrySupplier<FezBlock> FEZ = register("fez", () -> new FezBlock(BlockBehaviour.Properties.of(Material.BAMBOO).noOcclusion().strength(1000, 1000).sound(SoundType.BAMBOO)), true, true);


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
