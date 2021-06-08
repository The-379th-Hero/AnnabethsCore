package annabeth.coremod.crafting;

import annabeth.coremod.CoreMain;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public class RecipeHandler {
	public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> e) {
		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CoreMain.MODID, "sawmill"), RecipeVars.SAWMILL_RECIPE);
		e.getRegistry().register(RecipeVars.SAWMILL_SERIALIZER);
	}
}