package annabeth.coremod.util;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.task.GiveHeroGiftsTask;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern.PlacementBehaviour;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.LegacySingleJigsawPiece;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class VillagerHelper {
	private static Map<VillagerProfession, ResourceLocation> GIFTS;
	
	static {
		try {
			GIFTS = (Map<VillagerProfession, ResourceLocation>) ObfuscationReflectionHelper.findField(GiveHeroGiftsTask.class, "field_220403_a").get(Maps.newHashMap());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private VillagerHelper() {
	}
	
	public static void setHOTVGifts(VillagerProfession profession) {
		ResourceLocation name = profession.getRegistryName();
		GIFTS.put(profession, new ResourceLocation(name.getNamespace(), "gameplay/hero_of_the_village/" + name.getPath()));
	}
	
	public static PointOfInterestType makePOI(ResourceLocation name, Set<BlockState> states) {
		PointOfInterestType type = new PointOfInterestType(name.toString(), ImmutableSet.copyOf(states), 1, 1);
		return type;
	}
	
	public static PointOfInterestType makePOI(ResourceLocation name, Block block) {
		return makePOI(name, PointOfInterestType.getBlockStates(block));
	}
	
	public static VillagerProfession makeProfession(ResourceLocation name, PointOfInterestType poi, SoundEvent workSound) {
		return new VillagerProfession(name.toString(), poi, ImmutableSet.<Item>builder().build(), ImmutableSet.<Block>builder().build(), workSound);
	}
	
	public static VillagerProfession makeProfession(ResourceLocation name, PointOfInterestType poi, ImmutableSet<Item> items, ImmutableSet<Block> blocks, SoundEvent workSound) {
		return new VillagerProfession(name.toString(), poi, items, blocks, workSound);
	}
	
	public static void addVillagerHouse(ResourceLocation pool, ResourceLocation newHouse, int wheight) {
		JigsawPattern old = WorldGenRegistries.TEMPLATE_POOL.get(pool);
		
		List<JigsawPiece> shuffled;
		
		if (old != null) {
			shuffled = old.getShuffledTemplates(new Random(0));
		} else {
			shuffled = ImmutableList.of();
		}List<Pair<JigsawPiece, Integer>> newPieces = Lists.newArrayList();
		
		for (JigsawPiece piece : shuffled) {
			newPieces.add(new Pair<>(piece, 1));
		}
		
		newPieces.add(Pair.of(new LegacySingleJigsawPieceHelper(Either.left(newHouse), () -> ProcessorLists.EMPTY, PlacementBehaviour.RIGID), wheight));
		
		ResourceLocation name = old.getName();
		Registry.register(WorldGenRegistries.TEMPLATE_POOL, pool, new JigsawPattern(pool, name, newPieces));
	}
	
	public static void addVillagerHouse(ResourceLocation pool, ResourceLocation newHouse) {
		addVillagerHouse(pool, newHouse, 1);
	}
	
	private static class LegacySingleJigsawPieceHelper extends LegacySingleJigsawPiece {
		protected LegacySingleJigsawPieceHelper(Either<ResourceLocation, Template> p_i242007_1_, Supplier<StructureProcessorList> p_i242007_2_, PlacementBehaviour p_i242007_3_) {
			super(p_i242007_1_, p_i242007_2_, p_i242007_3_);
		}
	}
}