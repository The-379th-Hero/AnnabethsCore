package annabeth.coremod.crafting;

import annabeth.coremod.CoreMain;
import net.minecraft.item.crafting.IRecipeType;

public class SawmillRecipeType implements IRecipeType<SawmillRecipe> {
	@Override
	public String toString() {
		return CoreMain.MODID + ":sawmill";
	}
}