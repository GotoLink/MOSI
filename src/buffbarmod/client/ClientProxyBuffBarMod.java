package buffbarmod.client;

import buffbarmod.common.BuffBarDisplayTicker;
import buffbarmod.common.CommonProxyBuffBarMod;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxyBuffBarMod extends CommonProxyBuffBarMod{

	@Override
	public void registerRenderThings() {
        FMLCommonHandler.instance().bus().register(new BuffBarDisplayTicker());
	}
		
	//Pre-Init
	@Override
	public void registerTickers(){
		//None Yet :(
	}

}
