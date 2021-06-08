package annabeth.coremod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraftforge.common.ToolType;

public class ModSlabBlock extends SlabBlock {
	public ModSlabBlock(Block block) {
		super(Properties.copy(block));
	}
	
	public ModSlabBlock(Block block, ToolType harvestTool, int harvestLevel) {
		super(Properties.copy(block).harvestTool(harvestTool).harvestLevel(harvestLevel));
	}
}