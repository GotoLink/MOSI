package armorbarmod.common;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;

public class MOSIDisplayTicker {
    public static int inGameTicks = 0;
    protected float zLevel = 10.0F;

    private static List<DisplayUnit> displayList = new ArrayList<DisplayUnit>();

    public static void addDisplay(DisplayUnit displayUnit) {
        displayList.add(displayUnit);
    }
    private final Minecraft mc;
    public MOSIDisplayTicker(){
        mc = Minecraft.getMinecraft();
    }
    @SubscribeEvent
    public void onRender(Post event) {
        if (event.type == ElementType.HOTBAR) {
            for (DisplayUnit displayUnit : displayList) {
                displayUnit.onUpdate(mc, inGameTicks);
                if (displayUnit.shouldRender(mc)) {
                    displayUnit.renderDisplay(mc);
                }
            }
            inGameTicks++;
        }
    }
}
