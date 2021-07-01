package annabeth.coremod.crafting;

import annabeth.coremod.CoreMain;
import net.minecraft.item.crafting.IRecipeType;

public class ClickRecipeType implements IRecipeType<ClickRecipe> {
	@Override
	public String toString() {
		return CoreMain.MODID + ":click";
	}
}