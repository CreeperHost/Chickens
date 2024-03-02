package net.creeperhost.chickens.client.screen;

import net.creeperhost.chickens.blockentities.BreederBlockEntity;
import net.creeperhost.chickens.blockentities.EggCrackerBlockEntity;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.containers.BreederMenu;
import net.creeperhost.chickens.containers.EggCrackerMenu;
import net.creeperhost.chickens.containers.IncubatorMenu;
import net.creeperhost.polylib.client.modulargui.ModularGui;
import net.creeperhost.polylib.client.modulargui.ModularGuiContainer;
import net.creeperhost.polylib.client.modulargui.elements.*;
import net.creeperhost.polylib.client.modulargui.lib.Constraints;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerGuiProvider;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerScreenAccess;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Align;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint;
import net.creeperhost.polylib.client.modulargui.sprite.PolyTextures;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.*;

/**
 * Created by brandon3055 on 01/03/2024
 */
public class EggCrackerGui extends ContainerGuiProvider<EggCrackerMenu> {
    public static final int GUI_WIDTH = 176;
    public static final int GUI_HEIGHT = 171;

    @Override
    public GuiElement<?> createRootElement(ModularGui gui) {
        GuiManipulable root = new GuiManipulable(gui).addMoveHandle(3).enableCursors(true);
        GuiTexture bg = new GuiTexture(root.getContentElement(), ChickenGuiTextures.get("egg_cracker"));
        Constraints.bind(bg, root.getContentElement());
        return root;
    }

    @Override
    public void buildGui(ModularGui gui, ContainerScreenAccess<EggCrackerMenu> screenAccess) {
        gui.initStandardGui(GUI_WIDTH, GUI_HEIGHT);
        EggCrackerMenu menu = screenAccess.getMenu();
        EggCrackerBlockEntity tile = menu.tile;
        gui.setGuiTitle(tile.getDisplayName());
        GuiElement<?> root = gui.getRoot();

        GuiText title = new GuiText(root, gui.getGuiTitle())
                .setTextColour(0x404040)
                .setShadow(false)
                .constrain(TOP, relative(root.get(TOP), 4))
                .constrain(HEIGHT, Constraint.literal(8))
                .constrain(LEFT, match(root.get(LEFT)))
                .constrain(RIGHT, match(root.get(RIGHT)));

        var playInv = GuiSlots.player(root, screenAccess, menu.main, menu.hotBar);
        Constraints.placeInside(playInv.container, root, Constraints.LayoutPos.BOTTOM_CENTER, 0, -7);
        GuiText invTitle = new GuiText(playInv.container, Component.translatable("container.inventory"))
                .setAlignment(Align.LEFT)
                .setTextColour(0x404040)
                .setShadow(false)
                .constrain(WIDTH, match(playInv.container.get(WIDTH)))
                .constrain(HEIGHT, literal(8));
        Constraints.placeInside(invTitle, playInv.container, Constraints.LayoutPos.TOP_LEFT, 0, -10);

        GuiSlots bucketSlot = GuiSlots.singleSlot(root, screenAccess, menu.fluidSlot)
                .setEmptyIcon(PolyTextures.get("slots/bucket"))
                .setTooltip(Component.translatable("gui.chickens.cracker.fluid_slot"))
                .constrain(RIGHT, match(playInv.container.get(RIGHT)))
                .constrain(BOTTOM, relative(invTitle.get(TOP), -2));

        var tank = GuiFluidTank.simpleTank(root);
        tank.container
                .constrain(WIDTH, literal(18))
                .constrain(LEFT, match(bucketSlot.get(LEFT)))
                .constrain(BOTTOM, relative(bucketSlot.get(TOP), -2))
                .constrain(TOP, relative(root.get(TOP), 6));
        tank.primary
                .setCapacity(tile.tank::getCapacity)
                .setFluidStack(menu.tank::get);

        GuiSlots outputSlots = new GuiSlots(root, screenAccess, menu.output, 3)
                .constrain(TOP, midPoint(title.get(BOTTOM), invTitle.get(TOP), -27))
                .constrain(RIGHT, relative(bucketSlot.get(LEFT), -5));


        boolean energy = Config.INSTANCE.enableEnergy;
        if (energy) {
            GuiSlots energySlot = GuiSlots.singleSlot(root, screenAccess, menu.energySlot)
                    .setEmptyIcon(PolyTextures.get("slots/energy"))
                    .constrain(LEFT, match(playInv.container.get(LEFT)))
                    .constrain(BOTTOM, match(bucketSlot.get(BOTTOM)));

            var energyBar = GuiEnergyBar.simpleBar(root);
            energyBar.container
                    .constrain(TOP, match(tank.container.get(TOP)))
                    .constrain(BOTTOM, relative(energySlot.get(TOP), -2))
                    .constrain(LEFT, relative(energySlot.get(LEFT), 0))
                    .constrain(RIGHT, relative(energySlot.get(RIGHT), 0));
            energyBar.primary
                    .setCapacity(tile.energy::getMaxEnergyStored)
                    .setEnergy(menu.energy::get);
        }
    }

    public static class Screen extends ModularGuiContainer<EggCrackerMenu> {
        public Screen(EggCrackerMenu menu, Inventory inv, Component title) {
            super(menu, inv, new EggCrackerGui());
            getModularGui().setGuiTitle(title);
        }
    }
}
