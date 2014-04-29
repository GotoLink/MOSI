package armorbarmod.common;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = DefaultProps.modId, name = "Armor Bar Mod", version = DefaultProps.VERSION_STRING)
public class mod_ArmorBarMod {
    @SidedProxy(clientSide = "armorbarmod.client.ClientProxyArmorBarMod", serverSide = "armorbarmod.common.CommonProxyArmorBarMod")
    public static CommonProxyArmorBarMod proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration armorConfig = new Configuration(event.getSuggestedConfigurationFile());
        armorConfig.load();
        DisplayBuilder.loadDisplayFromConfig(armorConfig);
        armorConfig.save();
        proxy.registerDisplay();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        DisplayBuilder.buildDisplay();
    }
}
