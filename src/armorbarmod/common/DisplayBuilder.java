package armorbarmod.common;

import java.util.EnumSet;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

import org.lwjgl.util.Point;

public enum DisplayBuilder {
	Feet {
		@Override
		void createDisplayUnit() {
			displayUnit = new DisplayUnitArmorSlot(this.toString(), 0, true, 1030655, new Point(95, (16+4)*0+20));
			displayUnit.loadProfile(EnumSet.of(Setting.FlowRight, Setting.AnalogBar, Setting.DisplayWhenEmpty));
		}
	},
	Legs{
		@Override
		void createDisplayUnit() {
			displayUnit = new DisplayUnitArmorSlot(this.toString(), 1, true, 1030655, new Point(95, (16+4)*1+20));
			displayUnit.loadProfile(EnumSet.of(Setting.FlowRight, Setting.AnalogBar, Setting.DisplayWhenEmpty));
		}
	},
	Chest{
		@Override
		void createDisplayUnit() {
			displayUnit = new DisplayUnitArmorSlot(this.toString(), 2, true, 1030655, new Point(-111, (16+4)*0+20));
			displayUnit.loadProfile(EnumSet.of(Setting.FlowLeft, Setting.AnalogBar, Setting.DisplayWhenEmpty));
		}
	},
	Head{
		@Override
		void createDisplayUnit() {
			displayUnit = new DisplayUnitArmorSlot(this.toString(), 3, true, 1030655, new Point(-111, (16+4)*1+20));
			displayUnit.loadProfile(EnumSet.of(Setting.FlowLeft, Setting.AnalogBar, Setting.DisplayWhenEmpty));
		}
	},
	Arrow{
		@Override
		void createDisplayUnit() {
			displayUnit = new DisplayUnitTrackItem(this.toString(), Items.arrow, true, 1030655,  new Point(119, (16+4)*0+20));
			displayUnit.loadProfile(EnumSet.of(Setting.FlowRight, Setting.AnalogBar, Setting.DigitalCounter, Setting.DisplayWhenEmpty, Setting.trackAmount));
		}
	},
	GenericDurabilityCounter1{
		@Override
		void createDisplayUnit() {
			displayUnit = new DisplayUnitTrackItem(this.toString(), Items.wooden_sword, false, 1030655, new Point(-119-16, (16+4)*0+20));
			displayUnit.loadProfile(EnumSet.of(Setting.FlowLeft, Setting.AnalogBar, Setting.DisplayWhenEmpty, Setting.trackDurability));
		}
	},
	GenericDurabilityCounter2{
		@Override
		void createDisplayUnit() {
			displayUnit = new DisplayUnitTrackItem(this.toString(), Items.iron_sword, false, 1030655, new Point(-119-16, (16+4)*1+20));
			displayUnit.loadProfile(EnumSet.of(Setting.FlowLeft, Setting.AnalogBar, Setting.DisplayWhenEmpty, Setting.trackDurability));
		}
	},
	GenericAmountCounter1{
		@Override
		void createDisplayUnit() {
			displayUnit = new DisplayUnitTrackItem(this.toString(), Items.coal, false, 1030655, new Point(119, (16+4)*1+20));
			displayUnit.loadProfile(EnumSet.of(Setting.FlowRight, Setting.AnalogBar, Setting.DigitalCounter, Setting.DisplayWhenEmpty, Setting.trackAmount, 
					Setting.equalItemMeta));
		}
	},
	GenericAmountCounter2{
		@Override
		void createDisplayUnit() {
			displayUnit = new DisplayUnitTrackItem(this.toString(), Items.diamond, false, 1030655, new Point(119, (16+4)*2+20));
			displayUnit.loadProfile(EnumSet.of(Setting.FlowRight, Setting.AnalogBar, Setting.DigitalCounter, Setting.DisplayWhenEmpty, Setting.trackAmount, 
					Setting.equalItemMeta));
		}
	};
	
	abstract void createDisplayUnit();
	protected DisplayUnit displayUnit;
	
	public static void loadDisplayFromConfig(Configuration config){
		for (DisplayBuilder displayBuilderUnit : DisplayBuilder.values()){
			displayBuilderUnit.createDisplayUnit();
			displayBuilderUnit.displayUnit.getFromConfig(config);
		}
	}
	
	public static void buildDisplay(){
		for (DisplayBuilder displayBuilderUnit : DisplayBuilder.values()){
			MOSIDisplayTicker.addDisplay(displayBuilderUnit.displayUnit);
		}
	}
}
