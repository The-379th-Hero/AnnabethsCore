package annabeth.coremod;

import java.util.List;

import com.google.common.collect.Lists;

import annabeth.coremod.crafting.BlockStateConverting;
import annabeth.coremod.crafting.ClickRecipe;
import annabeth.coremod.crafting.RecipeVars;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CoreMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
	@SubscribeEvent
	public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
		processClickRecipe(event, ClickRecipe.ClickType.LEFT);
	}
	
	@SubscribeEvent
	public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		processClickRecipe(event, ClickRecipe.ClickType.RIGHT);
	}
	
	private static void processClickRecipe(PlayerInteractEvent event, ClickRecipe.ClickType click) {
		World world = event.getWorld();
		ItemStack stack = event.getItemStack();
		BlockState state = world.getBlockState(event.getPos());
		PlayerEntity player = event.getPlayer();
		BlockPos pos = event.getPos();
		
		List <ClickRecipe> recipes = world.getRecipeManager().getAllRecipesFor(RecipeVars.CLICK_RECIPE);
		
		for (ClickRecipe recipe : recipes) {
			if ((recipe.getClick() == click || recipe.getClick() == ClickRecipe.ClickType.BOTH) && recipe.getInputBlocks().contains(state.getBlock()) && recipe.getInputItem().test(stack)) {
				Block blockIn = state.getBlock();
				Block blockOut = recipe.getOutputBlock();
				
				BlockState newState = BlockStateConverting.convert(state, blockIn, blockOut, event.getFace(), player.getDirection());
				
				recipe.playSound(world, player, pos);
				
				if (!world.isClientSide) {
					world.setBlock(pos, newState, 11);
					
					ClickRecipe.ConsumeType consume = recipe.getConsume();
					
					if (consume != ClickRecipe.ConsumeType.KEEP) {
						if (consume == ClickRecipe.ConsumeType.WEAK_CONSUME && stack.isDamageableItem()) {
							stack.hurtAndBreak(1, player, (p) -> {
								p.broadcastBreakEvent(event.getHand());
							});
						} else {
							stack.shrink(1);
						}
					}
					
					Item result = recipe.getOutputItem();
					
					if (result != Items.AIR) {
						world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), recipe.getResultItem()));
					}
					
					player.awardRecipes(Lists.newArrayList(recipe));
				}
				
				event.setCanceled(true);
				event.setCancellationResult(ActionResultType.sidedSuccess(world.isClientSide));
				event.setResult(Result.DENY);
			}
		}
	}
}