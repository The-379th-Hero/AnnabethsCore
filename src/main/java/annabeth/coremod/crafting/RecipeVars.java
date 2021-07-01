package annabeth.coremod.crafting;

import annabeth.coremod.CoreMain;
import annabeth.coremod.crafting.ClickRecipe.ClickSerializer;
import annabeth.coremod.crafting.SawmillRecipe.SawmillSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;

public class RecipeVars {
	public static final IRecipeType<SawmillRecipe> SAWMILL_RECIPE = new SawmillRecipeType();
	public static final IRecipeType<ClickRecipe> CLICK_RECIPE = new ClickRecipeType();
	
	public static final IRecipeSerializer<SawmillRecipe> SAWMILL_SERIALIZER = (IRecipeSerializer<SawmillRecipe>) new SawmillSerializer().setRegistryName(CoreMain.MODID, "sawmill");
	public static final IRecipeSerializer<ClickRecipe> CLICK_SERIALIZER = (IRecipeSerializer<ClickRecipe>) new ClickSerializer().setRegistryName(CoreMain.MODID, "click");
}