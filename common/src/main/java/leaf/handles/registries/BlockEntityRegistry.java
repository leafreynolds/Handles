package leaf.handles.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import leaf.handles.HandlesMod;
import leaf.handles.blockEntities.TardisPeripheralTile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry
{
	public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(HandlesMod.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
	public static final RegistrySupplier<BlockEntityType<TardisPeripheralTile>> TARDIS_PERIPHERAL_TILE =
			BLOCK_ENTITIES.register(
					"tardis_peripheral_tile",
					() -> BlockEntityType.Builder.of(
							TardisPeripheralTile::new,
							BlockRegistry.FEZ.get(),
							BlockRegistry.ANTENNA.get()
					).build(null));

}
