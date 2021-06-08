package annabeth.coremod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraftforge.common.ToolType;

public class ModWallBlock extends WallBlock {
	public ModWallBlock(Block block) {
		super(Properties.copy(block));
	}
	
	public ModWallBlock(Block block, ToolType harvestTool, int harvestLevel) {
		super(Properties.copy(block).harvestTool(harvestTool).harvestLevel(harvestLevel));
	}
}