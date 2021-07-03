package annabeth.coremod.util;

import java.util.Map;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class RecipeHelper {
	private RecipeHelper() {
	}
	
	public static Map<ResourceLocation, IRecipe<?>> getRecipes(IRecipeType<?> type, RecipeManager manager) {
		final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> map =  ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager, "field_199522_d");
		return map.get(type);
	}
	
	public static Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> getRecipes(RecipeManager manager) {
		return ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager, "field_199522_d");
	}
}