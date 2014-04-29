package buffbarmod.common;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Property;

@Mod(modid = "mod_BuffBarMod", name = "Buff Bar Mod", version = "0.7.1")
public class mod_BuffBarMod {
	@SidedProxy(clientSide = "buffbarmod.client.ClientProxyBuffBarMod", serverSide = "buffbarmod.common.CommonProxyBuffBarMod")
	public static CommonProxyBuffBarMod proxy;
	public static final int[] DEFAULTS = {10, -28, 0, 1030655, 13, 60};
    public static final String[] OPTIONS = {"Display X Offeset", "Display Y Offeset", "Display Type", "Display Font Color", "Display YOffSet in Creative", "Analog Max Duration Length"};
	public static Property[] controls = new Property[OPTIONS.length];
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.registerTickers();
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        for(int i = 0; i< OPTIONS.length; i++)
            controls[i] = config.get("Buff Bar Controls", OPTIONS[i], DEFAULTS[i]);
        
        config.save();
	}
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderThings();
	}
}
