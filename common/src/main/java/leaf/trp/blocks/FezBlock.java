package leaf.trp.blocks;

import leaf.trp.blockEntities.FezTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;


public class FezBlock extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock
{
	protected static final VoxelShape NORTH_AABB;
	protected static final VoxelShape SOUTH_AABB;
	protected static final VoxelShape WEST_AABB;
	protected static final VoxelShape EAST_AABB;
	protected static final VoxelShape UP_AABB;
	protected static final VoxelShape DOWN_AABB;

	public FezBlock(BlockBehaviour.Properties settings)
	{
		super(settings);
		this.registerDefaultState(
				this.stateDefinition
						.any()
						.setValue(FACING, Direction.NORTH)
						.setValue(FACE, AttachFace.WALL)
		);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new FezTile(blockPos, blockState);
	}

	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
	{
		return switch (blockState.getValue(FACE))
		{
			default -> DOWN_AABB;//roof
			case FLOOR -> UP_AABB;//floor
			case WALL -> switch (blockState.getValue(FACING))
			{
				case EAST -> EAST_AABB;
				case WEST -> WEST_AABB;
				case SOUTH -> SOUTH_AABB;
				default -> NORTH_AABB;
			};
		};
	}


	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(FACE, FACING);
	}

	static
	{
		NORTH_AABB = Block.box(4.0, 4.0, 6.0, 12.0, 12.0, 16.0);
		SOUTH_AABB = Block.box(4.0, 4.0, 0.0, 12.0, 12.0, 10.0);
		WEST_AABB = Block.box(6.0, 4.0, 4.0, 16.0, 12.0, 12.0);
		EAST_AABB = Block.box(0.0, 4.0, 4.0, 10.0, 12.0, 12.0);
		UP_AABB = Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0);//placed on ground
		DOWN_AABB = Block.box(4.0, 6.0, 4.0, 12.0, 16.0, 12.0);//placed on ceiling
	}
}
