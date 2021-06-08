package annabeth.coremod;

import annabeth.coremod.crafting.RecipeVars;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CoreMain.MODID)
public class RegistryHandler {
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> e) {
		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CoreMain.MODID, "sawmill"), RecipeVars.SAWMILL_RECIPE);
		e.getRegistry().register(RecipeVars.SAWMILL_SERIALIZER);
	}
	
}