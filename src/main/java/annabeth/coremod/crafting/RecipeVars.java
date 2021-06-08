package annabeth.coremod.crafting;

import annabeth.coremod.CoreMain;
import annabeth.coremod.crafting.SawmillRecipe.SawmillSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;

public class RecipeVars {
	public static final IRecipeType<SawmillRecipe> SAWMILL_RECIPE = new SawmillRecipeType();
	
	public static final IRecipeSerializer<SawmillRecipe> SAWMILL_SERIALIZER = (IRecipeSerializer<SawmillRecipe>) new SawmillSerializer().setRegistryName(CoreMain.MODID, "sawmill");
}