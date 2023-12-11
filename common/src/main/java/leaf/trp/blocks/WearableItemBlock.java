package leaf.trp.blocks;

import dev.architectury.extensions.ItemExtension;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class WearableItemBlock extends BlockItem implements ItemExtension
{
	private final EquipmentSlot slot;

	public WearableItemBlock(Block block, Properties properties, EquipmentSlot slot)
	{
		super(block, properties);
		this.slot = slot;
	}

	@Nullable
	@Override
	public EquipmentSlot getCustomEquipmentSlot(ItemStack stack) {
		return slot;
	}
}
