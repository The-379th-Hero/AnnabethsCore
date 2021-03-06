package annabeth.coremod;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CoreMain.MODID)
public class CoreMain {
	public static final String MODID = "annabethscore";
	
	public static final String VILLAGERS_MODID = "annabethsextravillagers";
	public static final String SSW_MODID = "sswplus";
	public static final String VOREE_MODID = "voree";
	public static final String LEAF_MODID = "leaf";
	public static final String SNA_MODID = "annabethsnethermod";
	
	public static final List<String> DEPENDENTS_MODIDS = Lists.newArrayList();
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public CoreMain() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void setup(final FMLCommonSetupEvent e) {
		DEPENDENTS_MODIDS.add(VILLAGERS_MODID);
		DEPENDENTS_MODIDS.add(SSW_MODID);
		DEPENDENTS_MODIDS.add(VOREE_MODID);
		DEPENDENTS_MODIDS.add(LEAF_MODID);
		DEPENDENTS_MODIDS.add(SNA_MODID);
	}
}