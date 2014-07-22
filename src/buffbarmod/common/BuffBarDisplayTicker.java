package buffbarmod.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import armorbarmod.common.DefaultProps;

public class BuffBarDisplayTicker{
	//public static long inGameTicks = 0;
	protected float zLevel = 9.0F;
	private boolean isInCreative = false;
	
	public static int xOffset = mod_BuffBarMod.controls[0].getInt();
	public static int yOffset = mod_BuffBarMod.controls[1].getInt();
	public static int displayType = mod_BuffBarMod.controls[2].getInt();
	public static int fontColor = mod_BuffBarMod.controls[3].getInt();
	public static int creativeYOffSet = mod_BuffBarMod.controls[4].getInt();
	
	public static int analogMaxDurationLength = mod_BuffBarMod.controls[5].getInt();

    protected static final ResourceLocation inventory = new ResourceLocation("textures/gui/container/inventory.png");
    protected static final ResourceLocation countdown = new ResourceLocation(DefaultProps.buffBarKey,
            "countdown.png");
    private final Minecraft mc;
    private FontRenderer fontRenderer;
    private TextureManager renderEngine;
	public BuffBarDisplayTicker(){
        mc = Minecraft.getMinecraft();
    }
	@SubscribeEvent
	public void ticks(TickEvent.RenderTickEvent event) {
		if(event.phase == TickEvent.Phase.END){
            tickEnd(event.renderTickTime);
        }
	}
	
	public void tickEnd(float tickTime) {
		if(mc.thePlayer != null && mc.currentScreen == null){
			EntityPlayer player = mc.thePlayer;
	        isInCreative = player.capabilities.isCreativeMode;
	        if(fontRenderer==null) {
                fontRenderer = mc.fontRenderer;
            }
            if(renderEngine==null){
                renderEngine = mc.renderEngine;
            }
			ScaledResolution var3 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

			int scalewidth = var3.getScaledWidth();
			int scaleHeight = var3.getScaledHeight();
			int buffsToShow = 10;
			int maxDuration = analogMaxDurationLength;
			short lengthOfBuffBar = (short)(18*buffsToShow);
			
			int buffNumber = 0;
			if( player.isPotionActive(Potion.moveSpeed) && buffNumber < 10){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.moveSpeed).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.moveSpeed).getDuration(), maxDuration, 0, 198, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.moveSlowdown) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.moveSlowdown).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.moveSlowdown).getDuration(), maxDuration, 0+18*1, 198, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.digSpeed) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.digSpeed).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.digSpeed).getDuration(), maxDuration, 0+18*2, 198, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.digSlowdown) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.digSlowdown).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.digSlowdown).getDuration(), maxDuration, 0+18*3, 198, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.damageBoost) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.damageBoost).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.damageBoost).getDuration(), maxDuration, 0+18*4, 198, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.weakness) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.weakness).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.weakness).getDuration(), maxDuration, 0+18*5, 198, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.poison) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.poison).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.poison).getDuration(), maxDuration, 0+18*6, 198, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.regeneration) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.regeneration).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.regeneration).getDuration(), maxDuration, 0+18*7, 198, xOffset, yOffset);
				buffNumber++;
			}
			
			if( player.isPotionActive(Potion.invisibility) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.invisibility).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.invisibility).getDuration(), maxDuration, 0, 198+18*1, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.hunger) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.hunger).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.hunger).getDuration(), maxDuration, 0+18*1, 198+18*1, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.jump) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.jump).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.jump).getDuration(), maxDuration, 0+18*2, 198+18*1, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.confusion) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.confusion).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.confusion).getDuration(), maxDuration, 0+18*3, 198+18*1, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.nightVision) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.nightVision).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.nightVision).getDuration(), maxDuration, 0+18*4, 198+18*1, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.blindness) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.blindness).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.blindness).getDuration(), maxDuration, 0+18*5, 198+18*1, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.resistance) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.resistance).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.resistance).getDuration(), maxDuration, 0+18*6, 198+18*1, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.fireResistance) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.fireResistance).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.fireResistance).getDuration(), maxDuration, 0+18*7, 198+18*1, xOffset, yOffset);
				buffNumber++;
			}
			if( player.isPotionActive(Potion.waterBreathing) && buffNumber < 10 ){
				int scaledDuration = mapDurationTo18(player.getActivePotionEffect(Potion.waterBreathing).getDuration(),maxDuration*20);
				drawPotionBuffAndDuration(scalewidth, scaleHeight, lengthOfBuffBar, buffNumber, scaledDuration,
						player.getActivePotionEffect(Potion.waterBreathing).getDuration(), maxDuration, 0, 198+18*2, xOffset, yOffset);
				buffNumber++;
			}
		}
		//inGameTicks++;
	}
	
	public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;

		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + par6) * var8));
		var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + par6) * var8));
		var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + 0) * var8));
		var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + 0) * var8));
		var9.draw();        	
	}
	
	public int mapDurationTo18(int duration, int maxDuration){
		if(duration > maxDuration){
			return 18;
		}
		if(duration < 0){
			return 0;
		}		
		return (int)( (float)(duration)/(float)(maxDuration)*18 ); 
	}

	public void drawPotionBuffAndDuration(int scalewidth, int scaleHeight, int lengthOfBuffBar, int buffNumber,
			int scaledDuration, int duration, int maxDuration, int iconXCoord, int iconYCoord, int xOffset, int yOffset){
		int xAxisPadding = 5;
		yOffset = isInCreative ? yOffset + creativeYOffSet : yOffset;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		  
		renderEngine.bindTexture(inventory);
		this.drawTexturedModalRect(scalewidth/2-lengthOfBuffBar/2+(18+xAxisPadding)*buffNumber+xOffset, scaleHeight-18*3-7+yOffset, iconXCoord, iconYCoord, 18, 18);
        renderEngine.bindTexture(countdown);
		
		if(displayType == 0 || displayType == 2){
			//Draw BackGround of CountDown Bar
			this.drawTexturedModalRect(scalewidth/2-lengthOfBuffBar/2+(18+xAxisPadding)*buffNumber+xOffset, scaleHeight-18*3-7+18+yOffset, 0, 0, 18, 3);
			if(scaledDuration > 9){
				this.drawTexturedModalRect(scalewidth/2-lengthOfBuffBar/2+(18+xAxisPadding)*buffNumber+xOffset, scaleHeight-18*3-7+18+yOffset, 0, 3, scaledDuration, 3);
			}else if(scaledDuration > 4){
				this.drawTexturedModalRect(scalewidth/2-lengthOfBuffBar/2+(18+xAxisPadding)*buffNumber+xOffset, scaleHeight-18*3-7+18+yOffset, 0, 3+3, scaledDuration, 3);
			}else{
				this.drawTexturedModalRect(scalewidth/2-lengthOfBuffBar/2+(18+xAxisPadding)*buffNumber+xOffset, scaleHeight-18*3-7+18+yOffset, 0, 3+6, scaledDuration, 3);
			}
		}
		if(displayType == 2){
			yOffset += 4;
		}
		if(displayType == 1 || displayType == 2){
			int totalSeconds = duration / 20;
			
			/* Get Duration in Seconds */
		    int seconds = totalSeconds % 60; 
			/* Get Duration in Minutes */
		    int minutes = (totalSeconds / 60) % 60; 
		    String var9;
		    if(minutes == 0){
				var9 = String.format("%02d",seconds);
		    }else{
				var9 = minutes+":"+ String.format("%02d",seconds);
		    }
			float scale = 0.85f;
			GL11.glPushMatrix();
			GL11.glTranslatef((scalewidth/2-lengthOfBuffBar/2+(18+xAxisPadding)*buffNumber+xOffset - (fontRenderer.getStringWidth(var9)-18)/2)*(1-scale),
					(scaleHeight-18*3-7+18+yOffset)*(1-scale), 1f);
			GL11.glScalef(scale, scale, 1.0f);
			fontRenderer.drawString(var9,
					scalewidth/2-lengthOfBuffBar/2+(18+xAxisPadding)*buffNumber+xOffset - (fontRenderer.getStringWidth(var9)-20)/2,
					scaleHeight-18*3-7+18+yOffset,
					fontColor);
            GL11.glPopMatrix();
            
		}
		
		//Foreground of CountdownBar
	}

}
