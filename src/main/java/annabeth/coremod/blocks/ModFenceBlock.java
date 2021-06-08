package annabeth.coremod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraftforge.common.ToolType;

public class ModFenceBlock extends FenceBlock {
	public ModFenceBlock(Block block) {
		super(Properties.copy(block));
	}
	
	public ModFenceBlock(Block block, ToolType harvestTool, int harvestLevel) {
		super(Properties.copy(block).harvestTool(harvestTool).harvestLevel(harvestLevel));
	}
}