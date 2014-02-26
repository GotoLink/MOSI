package mosi.display.units;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mosi.display.DisplayUnitFactory;
import mosi.display.inventoryrules.ScrollableSubDisplays;
import mosi.display.resource.SimpleImageResource.GuiIconImageResource;
import mosi.display.units.action.ReplaceAction;
import mosi.display.units.windows.DisplayUnitButton;
import mosi.display.units.windows.DisplayUnitButton.Clicker;
import mosi.display.units.windows.DisplayUnitToggle;
import mosi.display.units.windows.DisplayWindowScrollList;
import mosi.display.units.windows.button.AddScrollClick;
import mosi.display.units.windows.button.CloseClick;
import mosi.display.units.windows.button.MoveScrollElementToggle;
import mosi.display.units.windows.button.RemoveScrollToggle;
import mosi.utilities.Coord;
import net.minecraft.client.Minecraft;

import com.google.common.base.Optional;
import com.google.gson.JsonObject;

public class DisplayUnitSortedPanel extends DisplayUnitPanel {
    public static final String DISPLAY_ID = "DisplayUnitSortedPanel";

    private List<DisplayUnitCountable> childDisplays;

    private SortMode sortMode;

    // TODO: For SortMode COUNT an interface DisplayUnitCountable needs to be created
    // Should Sorting be seperate DisplayUnit? i.e. DisplayUnitSortedPanel, this would maintain the ability for
    // DisplayUnitPanel to contain other DisplayUnitPanel
    // Both DisplayUnitPanel and DisplayUnitSortedPanel should extend common base class
    public enum SortMode {
        // No SORT, Sorted by order added to list or last sorted value
        NATURAL,
        // Sort by trackedCount
        HIGHLOW(new Comparator<DisplayUnitCountable>() {

            @Override
            public int compare(DisplayUnitCountable duc1, DisplayUnitCountable duc2) {
                return duc1.getCount() < duc2.getCount() ? +1 : duc1.getCount() > duc2.getCount() ? -1 : 0;
            }
        }), LOWHIGH(new Comparator<DisplayUnitCountable>() {

            @Override
            public int compare(DisplayUnitCountable duc1, DisplayUnitCountable duc2) {
                return duc1.getCount() > duc2.getCount() ? +1 : duc1.getCount() < duc2.getCount() ? -1 : 0;
            }
        });
        public final Optional<Comparator<DisplayUnitCountable>> sorter;

        SortMode() {
            this.sorter = Optional.absent();
        }

        SortMode(Comparator<DisplayUnitCountable> sorter) {
            this.sorter = Optional.of(sorter);
        }
    }

    public DisplayUnitSortedPanel() {
        super();
        sortMode = SortMode.LOWHIGH;
        childDisplays = new ArrayList<DisplayUnitCountable>();
        childDisplays.add(new DisplayUnitPotion(20, 1));
        childDisplays.add(new DisplayUnitPotion(20, 4));
        childDisplays.add(new DisplayUnitPotion(20, 8));
        childDisplays.add(new DisplayUnitPotion(20, 11));
        childDisplays.add(new DisplayUnitItem());
        childDisplays.add(new DisplayUnitPotion(20, 14));
    }

    @Override
    public String getType() {
        return DISPLAY_ID;
    }

    @Override
    public Coord getSize() {
        return new Coord(18, 18);
    }

    @Override
    public void update(Minecraft mc, int ticks) {
        if (sortMode.sorter.isPresent()) {
            Collections.sort(childDisplays, sortMode.sorter.get());
        }

        for (DisplayUnit displayUnit : childDisplays) {
            displayUnit.onUpdate(mc, ticks);
        }
    }

    @Override
    public List<? extends DisplayUnit> getDisplaysToRender() {
        return childDisplays;
    }

    @Override
    public DisplayUnit getPanelEditor() {
        return new DisplayUnitButton(new Coord(0, 122), new Coord(80, 15), VerticalAlignment.TOP_ABSO,
                HorizontalAlignment.CENTER_ABSO, new Clicker() {
                    private DisplayUnitPanel display;
                    private VerticalAlignment parentVert;
                    private HorizontalAlignment parentHorz;

                    private Clicker init(DisplayUnitPanel display, VerticalAlignment parentVert,
                            HorizontalAlignment parentHorz) {
                        this.display = display;
                        this.parentVert = parentVert;
                        this.parentHorz = parentHorz;
                        return this;
                    }

                    @Override
                    public ActionResult onClick() {
                        return ActionResult.SIMPLEACTION;
                    }

                    @Override
                    public ActionResult onRelease() {
                        ScrollableSubDisplays<DisplayUnitCountable> scrollable = new ScrollableSubDisplays<DisplayUnitCountable>(
                                childDisplays);
                        DisplayWindowScrollList<DisplayUnitCountable> slider = new DisplayWindowScrollList<DisplayUnitCountable>(
                                new Coord(90, 00), new Coord(90, 100), 25, parentVert, parentHorz, scrollable);
                        // Add Element Buttons
                        slider.addElement(new DisplayUnitButton(new Coord(2, 2), new Coord(20, 20),
                                VerticalAlignment.TOP_ABSO, HorizontalAlignment.LEFT_ABSO,
                                new AddScrollClick<DisplayUnitCountable, ScrollableSubDisplays<DisplayUnitCountable>>(
                                        scrollable) {

                                    @Override
                                    public void performScrollAddition(
                                            ScrollableSubDisplays<DisplayUnitCountable> container) {
                                        container.addElement(new DisplayUnitPotion());
                                    }
                                })
                                .setIconImageResource(new GuiIconImageResource(new Coord(147, 44), new Coord(12, 16))));
                        slider.addElement(new DisplayUnitButton(new Coord(23, 2), new Coord(20, 20),
                                VerticalAlignment.TOP_ABSO, HorizontalAlignment.LEFT_ABSO,
                                new AddScrollClick<DisplayUnitCountable, ScrollableSubDisplays<DisplayUnitCountable>>(
                                        scrollable) {

                                    @Override
                                    public void performScrollAddition(
                                            ScrollableSubDisplays<DisplayUnitCountable> container) {
                                        container.addElement(new DisplayUnitItem());
                                    }
                                })
                                .setIconImageResource(new GuiIconImageResource(new Coord(165, 44), new Coord(12, 16))));

                        // List interactive Buttons - Remove, MoveUp, MoveDown
                        slider.addElement(new DisplayUnitToggle(new Coord(-2, 2), new Coord(20, 20),
                                VerticalAlignment.TOP_ABSO, HorizontalAlignment.RIGHT_ABSO,
                                new RemoveScrollToggle<DisplayUnitCountable>(scrollable))
                                .setIconImageResource(new GuiIconImageResource(new Coord(201, 44), new Coord(13, 16))));
                        slider.addElement(new DisplayUnitToggle(new Coord(-23, 2), new Coord(20, 20),
                                VerticalAlignment.TOP_ABSO, HorizontalAlignment.RIGHT_ABSO,
                                new MoveScrollElementToggle<DisplayUnitCountable>(scrollable, 1))
                                .setIconImageResource(new GuiIconImageResource(new Coord(165, 66), new Coord(12, 15))));
                        slider.addElement(new DisplayUnitToggle(new Coord(-44, 2), new Coord(20, 20),
                                VerticalAlignment.TOP_ABSO, HorizontalAlignment.RIGHT_ABSO,
                                new MoveScrollElementToggle<DisplayUnitCountable>(scrollable, -1))
                                .setIconImageResource(new GuiIconImageResource(new Coord(147, 66), new Coord(12, 15))));
                        slider.addElement(new DisplayUnitButton(new Coord(0, -2), new Coord(60, 20),
                                VerticalAlignment.BOTTOM_ABSO, HorizontalAlignment.CENTER_ABSO, new CloseClick(slider),
                                "Close"));
                        return new ReplaceAction(slider, true);
                    }
                }.init(this, getVerticalAlignment(), getHorizontalAlignment()), "Sub Displays");
    }

    @Override
    public void saveCustomData(JsonObject jsonObject) {
        super.saveCustomData(jsonObject);
    }

    @Override
    public void loadCustomData(DisplayUnitFactory factory, JsonObject customData) {
        super.loadCustomData(factory, customData);
    }
}
