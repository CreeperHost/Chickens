package net.creeperhost.chickens.client.screen;

import net.creeperhost.chickens.blockentities.BreederBlockEntity;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.containers.BreederMenu;
import net.creeperhost.polylib.client.modulargui.ModularGui;
import net.creeperhost.polylib.client.modulargui.ModularGuiContainer;
import net.creeperhost.polylib.client.modulargui.elements.*;
import net.creeperhost.polylib.client.modulargui.lib.Constraints;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerGuiProvider;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerScreenAccess;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Align;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.RIGHT;

/**
 * Created by brandon3055 on 01/03/2024
 */
public class BreederGui extends ContainerGuiProvider<BreederMenu> {
    public static final int GUI_WIDTH = 176;
    public static final int GUI_HEIGHT = 133;

    @Override
    public GuiElement<?> createRootElement(ModularGui gui) {
        GuiManipulable root = new GuiManipulable(gui).addMoveHandle(3).enableCursors(true);
        GuiTexture bg = new GuiTexture(root.getContentElement(), ChickenGuiTextures.get("breeder"));
        Constraints.bind(bg, root.getContentElement());
        return root;
    }

    @Override
    public void buildGui(ModularGui gui, ContainerScreenAccess<BreederMenu> screenAccess) {
        gui.initStandardGui(GUI_WIDTH, GUI_HEIGHT);
        BreederMenu menu = screenAccess.getMenu();
        BreederBlockEntity tile = menu.tile;
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

        GuiSlots seeds = GuiSlots.singleSlot(root, screenAccess, menu.seeds)
                .setEmptyIcon(ChickenGuiTextures.get("slot/seeds"))
                .setTooltip(Component.translatable("gui.chickens.breeder.seed_slot"))
                .constrain(LEFT, match(playInv.container.get(LEFT)))
                .constrain(TOP, relative(title.get(BOTTOM), 6));

        GuiSlots outputs = new GuiSlots(root, screenAccess, menu.output, 3)
                .constrain(RIGHT, match(playInv.container.get(RIGHT)))
                .constrain(TOP, match(seeds.get(TOP)));

        GuiTexture plus = new GuiTexture(root, ChickenGuiTextures.get("elements/plus_icon"));
        Constraints.size(plus, 16, 16);
        Constraints.placeOutside(plus, seeds, Constraints.LayoutPos.MIDDLE_RIGHT, 2, 0);

        GuiSlots chickens = new GuiSlots(root, screenAccess, menu.chickens, 2)
                .setEmptyIcon(ChickenGuiTextures.get("slot/chicken"))
                .setTooltip(Component.translatable("gui.chickens.breeder.chicken_slot"));
        Constraints.placeOutside(chickens, plus, Constraints.LayoutPos.MIDDLE_RIGHT, 2, 0);

        GuiProgressIcon progress = new GuiProgressIcon(root)
                .setProgress(() -> menu.progress.get() / (double) BreederBlockEntity.MAX_PROGRESS)
                .setBackground(ChickenGuiTextures.get("elements/breeder_progress_empty"))
                .setAnimated(ChickenGuiTextures.get("elements/breeder_progress"));
        Constraints.size(progress, 26, 16);
        Constraints.placeOutside(progress, chickens, Constraints.LayoutPos.MIDDLE_RIGHT, 4, 0);
    }

    public static class Screen extends ModularGuiContainer<BreederMenu> {
        public Screen(BreederMenu menu, Inventory inv, Component title) {
            super(menu, inv, new BreederGui());
            getModularGui().setGuiTitle(title);
        }
    }
}
