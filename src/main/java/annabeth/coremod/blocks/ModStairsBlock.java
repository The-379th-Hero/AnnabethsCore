package annabeth.coremod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraftforge.common.ToolType;

public class ModStairsBlock extends StairsBlock {
	public ModStairsBlock(Block block) {
		super(() -> block.defaultBlockState(), Properties.copy(block));
	}
	
	public ModStairsBlock(Block block, ToolType harvestTool, int harvestLevel) {
		super(() -> block.defaultBlockState(), Properties.copy(block).harvestTool(harvestTool).harvestLevel(harvestLevel));
	}
}