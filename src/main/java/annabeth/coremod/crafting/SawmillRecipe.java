package annabeth.coremod.crafting;

import com.google.gson.JsonObject;

import annabeth.coremod.CoreMain;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SawmillRecipe extends SingleItemRecipe {
	public SawmillRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result) {
		super(RecipeVars.SAWMILL_RECIPE, RecipeVars.SAWMILL_SERIALIZER, id, group, ingredient, result);
	}
	
	@Override
	public boolean matches(IInventory inv, World worldIn) {
		return this.ingredient.test(inv.getItem(0));
	}
	
	@Override
	public ItemStack getToastSymbol() {
		Item item = Registry.ITEM.getOptional(new ResourceLocation(CoreMain.VILLAGERS_MODID, "sawmill")).orElse(Item.byBlock(Blocks.STONECUTTER));
		return new ItemStack(item);
	}
	
	public static class SawmillSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SawmillRecipe> {
		@Override
		public SawmillRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
			String s = JSONUtils.getAsString(json, "group", "");
			Ingredient ingredient;
			if (JSONUtils.isArrayNode(json, "ingredient")) {
				ingredient = Ingredient.fromJson(JSONUtils.getAsJsonArray(json, "ingredient"));
			} else {
				ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
			}
			
			String s1 = JSONUtils.getAsString(json, "result");
			int i = JSONUtils.getAsInt(json, "count");
			ItemStack stack = new ItemStack(Registry.ITEM.get(new ResourceLocation(s1)), i);
			return new SawmillRecipe(recipeID, s, ingredient, stack);
		}

		@Override
		public SawmillRecipe fromNetwork(ResourceLocation recipeID, PacketBuffer buffer) {
			String s = buffer.readUtf(32767);
			Ingredient ingredient = Ingredient.fromNetwork(buffer);
			ItemStack stack = buffer.readItem();
			return new SawmillRecipe(recipeID, s, ingredient, stack);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, SawmillRecipe recipe) {
			buffer.writeUtf(recipe.group);
			recipe.ingredient.toNetwork(buffer);
			buffer.writeItem(recipe.result);
		}
	}
}