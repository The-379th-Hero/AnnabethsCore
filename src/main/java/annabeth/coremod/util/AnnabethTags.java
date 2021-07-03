package annabeth.coremod.util;

import annabeth.coremod.CoreMain;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class AnnabethTags {
	public static class BlockTagList {
		public static final IOptionalNamedTag<Block> OVERWORLD_PLANKS = create("overworld_planks");
		public static final IOptionalNamedTag<Block> OVERWORLD_STAIRS = create("overworld_stairs");
		public static final IOptionalNamedTag<Block> OVERWORLD_SLABS = create("overworld_slabs");
		public static final IOptionalNamedTag<Block> OVERWORLD_FENCES = create("annabethscore:overworld_fences");
		public static final IOptionalNamedTag<Block> OVERWORLD_FENCE_GATES = create("overworld_fence_gates");
		public static final IOptionalNamedTag<Block> OVERWORLD_DOORS = create("overworld_doors");
		public static final IOptionalNamedTag<Block> OVERWORLD_TRAPDOORS = create("overworld_trapdoors");
		public static final IOptionalNamedTag<Block> COLORED_TERRACOTTA = create("colored_terracotta");
		public static final IOptionalNamedTag<Block> GLAZED_TERRACOTTA = create("glazed_terracotta");
		
		public static IOptionalNamedTag<Block> create(String name) {
			return create(CoreMain.MODID, name);
		}
		
		public static IOptionalNamedTag<Block> create(String modid, String name) {
			return BlockTags.createOptional(new ResourceLocation(modid, name));
		}
	}
	
	public static class ItemTagList {
		public static final IOptionalNamedTag<Item>  OVERWORLD_PLANKS = create("overworld_planks");
		public static final IOptionalNamedTag<Item>  OVERWORLD_STAIRS = create("overworld_stairs");
		public static final IOptionalNamedTag<Item>  OVERWORLD_SLABS = create("overworld_slabs");
		public static final IOptionalNamedTag<Item>  OVERWORLD_FENCES = create("overworld_fences");
		public static final IOptionalNamedTag<Item>  OVERWORLD_FENCE_GATES = create("overworld_fence_gates");
		public static final IOptionalNamedTag<Item>  OVERWORLD_DOORS = create("overworld_doors");
		public static final IOptionalNamedTag<Item>  OVERWORLD_TRAPDOORS = create("overworld_trapdoors");
		public static final IOptionalNamedTag<Item> COLORED_TERRACOTTA = create("colored_terracotta");
		public static final IOptionalNamedTag<Item> GLAZED_TERRACOTTA = create("glazed_terracotta");
		public static final IOptionalNamedTag<Item> AXES = create("forge", "axes");
		public static final IOptionalNamedTag<Item> HOES = create("forge", "hoes");
		public static final IOptionalNamedTag<Item> PICKAXES = create("forge", "pickaxes");
		public static final IOptionalNamedTag<Item> SHOVELS = create("forge", "shovels");
		public static final IOptionalNamedTag<Item> SWORDS = create("forge", "swords");
		
		public static IOptionalNamedTag<Item> create(String name) {
			return create(CoreMain.MODID, name);
		}
		
		public static IOptionalNamedTag<Item> create(String modid, String name) {
			return ItemTags.createOptional(new ResourceLocation(modid, name));
		}
	}
}