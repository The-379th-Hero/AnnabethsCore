package annabeth.coremod.crafting;

import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ClickRecipe implements IRecipe<IInventory> {
	private final ResourceLocation id;
	
	private final Item outputItem;
	private final Ingredient inputItem;
	private final Set<Block> startBlocks;
	private final Block endBlock;
	private final SoundEvent soundToPlay;
	private final ClickType click;
	private final ConsumeType consume;
	private final int outputItemCount;
	private final ItemStack result;
	
	public ClickRecipe(ResourceLocation id, Set<Block> b1, Ingredient i1, Block b2, Item i2, int n, SoundEvent s, ClickType cl, ConsumeType con) {
		this.id = id;
		this.startBlocks = b1;
		this.inputItem = i1;
		this.endBlock = b2;
		this.outputItem = i2;
		this.outputItemCount = n;
		this.soundToPlay = s;
		this.click = cl;
		this.consume = con;
		this.result = i2 == Items.AIR ? new ItemStack(b2) : new ItemStack(i2, n);
	}
	
	@Override
	public ItemStack getToastSymbol() {
		return this.inputItem.getItems()[0];
	}
	
	public void playSound(World world, PlayerEntity player, BlockPos pos) {
		if (this.soundToPlay != null)
			world.playSound(player, pos, this.soundToPlay, SoundCategory.BLOCKS, 1.0f, 1.0f);
	}
	
	public Set<Block> getInputBlocks() {
		return this.startBlocks;
	}
	
	public Block getOutputBlock() {
		return this.endBlock;
	}
	
	public Ingredient getInputItem() {
		return this.inputItem;
	}
	
	public Item getOutputItem() {
		return this.outputItem;
	}
	
	public int getOutputCount() {
		return this.outputItemCount;
	}
	
	public ClickType getClick() {
		return this.click;
	}
	
	public ConsumeType getConsume() {
		return this.consume;
	}
	
	@Override
	public boolean matches(IInventory inv, World world) {
		return this.inputItem.test(inv.getItem(0));
	}
	
	@Override
	public ItemStack assemble(IInventory inv) {
		if (this.outputItem != Items.AIR) {
			return new ItemStack(this.outputItem, this.outputItemCount);
		} else {
			return new ItemStack(this.endBlock);
		}
	}
	
	@Override
	public boolean canCraftInDimensions(int i1, int i2) {
		return true;
	}
	
	@Override
	public ItemStack getResultItem() {
		return this.result;
	}
	
	@Override
	public ResourceLocation getId() {
		return this.id;
	}
	
	@Override
	public IRecipeSerializer<?> getSerializer() {
		return RecipeVars.CLICK_SERIALIZER;
	}
	
	@Override
	public IRecipeType<?> getType() {
		return RecipeVars.CLICK_RECIPE;
	}
	
	public static class ClickSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ClickRecipe> {
		@Override
		public ClickRecipe fromJson(ResourceLocation id, JsonObject json) {
			Set<Block> inBlocks = Sets.newHashSet();
			
			if (JSONUtils.isArrayNode(json, "inblock")) {
				for (JsonElement je : JSONUtils.getAsJsonArray(json, "inblock")) {
					inBlocks.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(je.getAsString())));
				}
			} else {
				JsonObject inBlock = JSONUtils.getAsJsonObject(json, "inblock");
				
				if (JSONUtils.isValidNode(inBlock, "block")) {
					inBlocks.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JSONUtils.getAsString(inBlock, "block"))));
				}
				
				if (JSONUtils.isValidNode(inBlock, "tag")) {
					ResourceLocation rl = new ResourceLocation(JSONUtils.getAsString(inBlock, "tag"));
					ITag<Block> tag = TagCollectionManager.getInstance().getBlocks().getTag(rl);
					
					if (tag == null) {
						throw new JsonSyntaxException("Unknown block tag " + rl + ".");
					}
					
					inBlocks.addAll(tag.getValues());
				}
			}
			
			Ingredient ingredient;
			if (JSONUtils.isArrayNode(json, "ingredient")) {
				ingredient = Ingredient.fromJson(JSONUtils.getAsJsonArray(json, "ingredient"));
			} else {
				ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
			}
			
			Block outBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JSONUtils.getAsString(json, "outblock")));
			
			Item resultItem;
			int i;
			
			if (JSONUtils.isValidNode(json, "result")) {
				JsonObject result = JSONUtils.getAsJsonObject(json, "result");
				resultItem = JSONUtils.getAsItem(result, "item");
				i = JSONUtils.getAsInt(result, "count");
			} else {
				resultItem = Items.AIR;
				i = 0;
			}
			
			ClickType click = ClickType.getClickTypeFor(JSONUtils.getAsString(json, "click", "right"));
			ConsumeType consume = ConsumeType.getConsumeTypeFor(JSONUtils.getAsString(json, "consume", "weak_consume"));
			
			SoundEvent sound = null;
			
			if (JSONUtils.isValidNode(json, "sound")) {
				sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(JSONUtils.getAsString(json, "sound")));
			}
			
			return new ClickRecipe(id, inBlocks, ingredient, outBlock, resultItem, i, sound, click, consume);
		}
		
		@Override
		public ClickRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
			Set<Block> inBlocks = Sets.newHashSet();
			int n = buffer.readInt();
			
			for (int i = 0; i < n; i ++) {
				inBlocks.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(buffer.readUtf(32767))));
			}
			
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			Block outBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(buffer.readUtf(32767)));
			ItemStack stack = buffer.readItem();
			Item resultItem = stack.getItem();
			int i = stack.getCount();
			SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(buffer.readUtf(32767)));
			ClickType click = ClickType.getClickTypeFor(buffer.readUtf(32767));
			ConsumeType consume = ConsumeType.getConsumeTypeFor(buffer.readUtf(32767));
			
			return new ClickRecipe(id, inBlocks, ingredient, outBlock, resultItem, i, sound, click, consume);
		}
		
		@Override
		public void toNetwork(PacketBuffer buffer, ClickRecipe recipe) {
			buffer.writeInt(recipe.getInputBlocks().size());
			for (Block block : recipe.getInputBlocks()) {
				buffer.writeUtf(block.getRegistryName().toString(), 32767);
			}
			recipe.getInputItem().toNetwork(buffer);
			buffer.writeUtf(recipe.getOutputBlock().getRegistryName().toString(), 32767);
			buffer.writeItem(recipe.getResultItem());
			buffer.writeUtf(recipe.soundToPlay.getRegistryName().toString(), 32767);
			buffer.writeUtf(recipe.getClick().toString(), 32767);
			buffer.writeUtf(recipe.getConsume().toString(), 32767);
		}
	}
	
	public static enum ClickType {
		RIGHT("right"),
		LEFT("left"),
		BOTH("both");
		
		private final String name;
		
		private ClickType(String s) {
			this.name = s;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		public static ClickType getClickTypeFor(String s) {
			return ClickType.valueOf(s.toUpperCase());
		}
	}
	
	
	public static enum ConsumeType {
		WEAK_CONSUME("weak_consume"),
		STRONG_CONSUME("strong_consume"),
		KEEP("keep");
		
		private final String name;
		
		private ConsumeType (String s) {
			this.name = s;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		public static ConsumeType getConsumeTypeFor(String s) {
			return ConsumeType.valueOf(s.toUpperCase());
		}
	}
}