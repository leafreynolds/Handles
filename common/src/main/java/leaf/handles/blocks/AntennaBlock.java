package leaf.handles.blocks;

import leaf.handles.blockEntities.TardisPeripheralTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;


public class AntennaBlock extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock, Wearable
{
	protected static final VoxelShape NORTH_AABB;
	protected static final VoxelShape SOUTH_AABB;
	protected static final VoxelShape WEST_AABB;
	protected static final VoxelShape EAST_AABB;
	protected static final VoxelShape UP_AABB;
	protected static final VoxelShape DOWN_AABB;

	public AntennaBlock(Properties settings)
	{
		super(settings);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		return new TardisPeripheralTile(blockPos, blockState);
	}

	public boolean isPathfindable(BlockState arg, BlockGetter arg2, BlockPos arg3, PathComputationType arg4)
	{
		return false;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		Direction direction = context.getClickedFace();
		Direction horizontalFacing = context.getPlayer().getDirection();
		return super.getStateForPlacement(context)
				.setValue(BlockStateProperties.HORIZONTAL_FACING, horizontalFacing.getOpposite())
				.setValue(BlockStateProperties.UP, direction.getOpposite() != Direction.DOWN)
				.setValue(BlockStateProperties.DOWN, direction.getOpposite() != Direction.UP);

	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		builder.add(FACE, HORIZONTAL_FACING, WATERLOGGED, UP, DOWN);
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

	static
	{
		//todo - replace these values with those better suited to the shape of the antenna
		NORTH_AABB = Block.box(4.0, 4.0, 6.0, 12.0, 12.0, 16.0);
		SOUTH_AABB = Block.box(4.0, 4.0, 0.0, 12.0, 12.0, 10.0);
		WEST_AABB = Block.box(6.0, 4.0, 4.0, 16.0, 12.0, 12.0);
		EAST_AABB = Block.box(0.0, 4.0, 4.0, 10.0, 12.0, 12.0);
		UP_AABB = Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0);//placed on ground
		DOWN_AABB = Block.box(4.0, 6.0, 4.0, 12.0, 16.0, 12.0);//placed on ceiling
	}
}
