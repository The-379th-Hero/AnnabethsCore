package annabeth.coremod.crafting;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WallHeight;
import net.minecraft.state.properties.BlockStateProperties;

public class BlockStateConverting {
	private static BlockState slab(BlockState from, BlockState to) {
		return to.setValue(SlabBlock.TYPE, from.getValue(SlabBlock.TYPE));
	}
	
	private static BlockState stairs(BlockState from, BlockState to) {
		return to.setValue(StairsBlock.FACING, from.getValue(StairsBlock.FACING))
				.setValue(StairsBlock.HALF, from.getValue(StairsBlock.HALF))
				.setValue(StairsBlock.SHAPE, from.getValue(StairsBlock.SHAPE));
	}
	
	private static BlockState wall(BlockState from, BlockState to) {
		return to.setValue(WallBlock.UP, from.getValue(WallBlock.UP))
				.setValue(WallBlock.NORTH_WALL, from.getValue(WallBlock.NORTH_WALL))
				.setValue(WallBlock.SOUTH_WALL, from.getValue(WallBlock.SOUTH_WALL))
				.setValue(WallBlock.WEST_WALL, from.getValue(WallBlock.WEST_WALL))
				.setValue(WallBlock.EAST_WALL, from.getValue(WallBlock.EAST_WALL));
	}
	
	private static BlockState fourwayblock(BlockState from, BlockState to) {
		return to.setValue(FourWayBlock.NORTH, from.getValue(FourWayBlock.NORTH))
				.setValue(FourWayBlock.SOUTH, from.getValue(FourWayBlock.SOUTH))
				.setValue(FourWayBlock.WEST, from.getValue(FourWayBlock.WEST))
				.setValue(FourWayBlock.EAST, from.getValue(FourWayBlock.EAST));
	}
	
	private static BlockState fenceGate(BlockState from, BlockState to) {
		return to.setValue(FenceGateBlock.IN_WALL, from.getValue(FenceGateBlock.IN_WALL))
				.setValue(FenceGateBlock.FACING, from.getValue(FenceGateBlock.FACING))
				.setValue(FenceGateBlock.POWERED, from.getValue(FenceGateBlock.POWERED))
				.setValue(FenceGateBlock.OPEN, from.getValue(FenceGateBlock.OPEN));
	}
	
	private static BlockState rotatedPillarBlock(BlockState from, BlockState to) {
		return to.setValue(RotatedPillarBlock.AXIS, from.getValue(RotatedPillarBlock.AXIS));
	}
	
	private static BlockState button(BlockState from, BlockState to) {
		return to.setValue(AbstractButtonBlock.POWERED, from.getValue(AbstractButtonBlock.POWERED))
				.setValue(AbstractButtonBlock.FACE, from.getValue(AbstractButtonBlock.FACE))
				.setValue(AbstractButtonBlock.FACING, from.getValue(AbstractButtonBlock.FACING));
	}
	
	private static BlockState pressurePlate(BlockState from, BlockState to) {
		return to.setValue(PressurePlateBlock.POWERED, from.getValue(PressurePlateBlock.POWERED));
	}
	
	private static BlockState trapdoor(BlockState from, BlockState to) {
		return to.setValue(TrapDoorBlock.HALF, from.getValue(TrapDoorBlock.HALF))
				.setValue(TrapDoorBlock.FACING, from.getValue(TrapDoorBlock.FACING))
				.setValue(TrapDoorBlock.OPEN, from.getValue(TrapDoorBlock.OPEN))
				.setValue(TrapDoorBlock.POWERED, from.getValue(TrapDoorBlock.POWERED));
	}
	
	private static BlockState fourwaytowall(BlockState from, BlockState to) {
		return to.setValue(WallBlock.NORTH_WALL, from.getValue(FourWayBlock.NORTH) ? WallHeight.LOW : WallHeight.NONE)
				.setValue(WallBlock.SOUTH_WALL, from.getValue(FourWayBlock.SOUTH) ? WallHeight.LOW : WallHeight.NONE)
				.setValue(WallBlock.WEST_WALL, from.getValue(FourWayBlock.WEST) ? WallHeight.LOW : WallHeight.NONE)
				.setValue(WallBlock.EAST_WALL, from.getValue(FourWayBlock.EAST) ? WallHeight.LOW : WallHeight.NONE);
	}
	
	private static BlockState walltofourway(BlockState from, BlockState to) {
		WallHeight north = from.getValue(WallBlock.NORTH_WALL);
		WallHeight south = from.getValue(WallBlock.SOUTH_WALL);
		WallHeight west = from.getValue(WallBlock.WEST_WALL);
		WallHeight east = from.getValue(WallBlock.EAST_WALL);
		
		return to.setValue(FourWayBlock.NORTH, (north == WallHeight.LOW || north == WallHeight.TALL))
				.setValue(FourWayBlock.SOUTH, (south == WallHeight.LOW || south == WallHeight.TALL))
				.setValue(FourWayBlock.WEST, (west == WallHeight.LOW || west == WallHeight.TALL))
				.setValue(FourWayBlock.EAST, (east == WallHeight.LOW || east == WallHeight.TALL));
	}
	
	private static BlockState horizontalBlock(BlockState from, BlockState to) {
		return to.setValue(HorizontalBlock.FACING, from.getValue(HorizontalBlock.FACING));
	}
	
	private static BlockState directionalBlock(BlockState from, BlockState to) {
		return to.setValue(DirectionalBlock.FACING, from.getValue(DirectionalBlock.FACING));
	}
	
	private static BlockState waterlogged(BlockState from, BlockState to) {
		return from.setValue(BlockStateProperties.WATERLOGGED, to.getValue(BlockStateProperties.WATERLOGGED));
	}
	
	public static BlockState convert(BlockState in, Block from, Block to) {
		BlockState out = to.defaultBlockState();
		
		if (from instanceof IWaterLoggable && to instanceof IWaterLoggable) {
			waterlogged(in, out);
		}
		
		if (from instanceof SlabBlock && to instanceof SlabBlock) {
			slab(in, out);
		} else if (from instanceof StairsBlock && to instanceof StairsBlock) {
			stairs(in, out);
		} else if (from instanceof WallBlock && to instanceof WallBlock) {
			wall(in, out);
		} else if (from instanceof FourWayBlock && to instanceof FourWayBlock) {
			fourwayblock(in, out);
		} else if (from instanceof FenceGateBlock && to instanceof FenceGateBlock) {
			fenceGate(in, out);
		} else if (from instanceof RotatedPillarBlock && to instanceof RotatedPillarBlock) {
			rotatedPillarBlock(in, out);
		} else if (from instanceof AbstractButtonBlock && to instanceof AbstractButtonBlock) {
			button(in, out);
		} else if (from instanceof PressurePlateBlock && to instanceof PressurePlateBlock) {
			pressurePlate(in, out);
		} else if (from instanceof TrapDoorBlock && to instanceof TrapDoorBlock) {
			trapdoor(in, out);
		} else if (from instanceof FourWayBlock && to instanceof WallBlock) {
			fourwaytowall(in, out);
		} else if (from instanceof WallBlock && to instanceof FourWayBlock) {
			walltofourway(in, out);
		} else if (in.hasProperty(HorizontalBlock.FACING) && out.hasProperty(HorizontalBlock.FACING)) {
			horizontalBlock(in, out);
		} else if (in.hasProperty(DirectionalBlock.FACING) && out.hasProperty(DirectionalBlock.FACING)) {
			directionalBlock(in, out);
		}
		
		return out;
	}
}