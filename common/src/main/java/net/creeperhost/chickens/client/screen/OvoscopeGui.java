package net.creeperhost.chickens.client.screen;

import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.blockentities.BreederBlockEntity;
import net.creeperhost.chickens.blockentities.EggCrackerBlockEntity;
import net.creeperhost.chickens.blockentities.OvoscopeBlockEntity;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.containers.BreederMenu;
import net.creeperhost.chickens.containers.OvoscopeMenu;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.polylib.client.modulargui.ModularGui;
import net.creeperhost.polylib.client.modulargui.ModularGuiContainer;
import net.creeperhost.polylib.client.modulargui.elements.*;
import net.creeperhost.polylib.client.modulargui.lib.Constraints;
import net.creeperhost.polylib.client.modulargui.lib.ForegroundRender;
import net.creeperhost.polylib.client.modulargui.lib.GuiRender;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerGuiProvider;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerScreenAccess;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Align;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint;
import net.creeperhost.polylib.client.modulargui.lib.geometry.GuiParent;
import net.creeperhost.polylib.client.modulargui.sprite.Material;
import net.creeperhost.polylib.client.modulargui.sprite.PolyTextures;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.*;

/**
 * Created by brandon3055 on 01/03/2024
 */
public class OvoscopeGui extends ContainerGuiProvider<OvoscopeMenu> {
    public static final int GUI_WIDTH = 176;
    public static final int GUI_HEIGHT = 160;

    @Override
    public GuiElement<?> createRootElement(ModularGui gui) {
        GuiManipulable root = new GuiManipulable(gui).addMoveHandle(3).enableCursors(true);
        GuiTexture bg = new GuiTexture(root.getContentElement(), ChickenGuiTextures.get("ovoscope"));
        Constraints.bind(bg, root.getContentElement());
        return root;
    }

    @Override
    public void buildGui(ModularGui gui, ContainerScreenAccess<OvoscopeMenu> screenAccess) {
        gui.initStandardGui(GUI_WIDTH, GUI_HEIGHT);
        OvoscopeMenu menu = screenAccess.getMenu();
        OvoscopeBlockEntity tile = menu.tile;
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

        GuiTexture slotTex = new GuiTexture(root, () -> ChickenGuiTextures.get("elements/ovoscope_slot" + (menu.scanning.get() ? "_active" : "")));
        Constraints.size(slotTex, 22, 22);
        Constraints.placeInside(slotTex, root, Constraints.LayoutPos.TOP_CENTER, 0, 28);

        GuiSlots inputSlot = GuiSlots.singleSlot(slotTex, screenAccess, menu.input)
                .setSlotTexture(integer -> null);
        Constraints.center(inputSlot, slotTex);

        Constraints.bind(new Scanner(root, menu), slotTex, 1);

        GuiTexture viableTex = new GuiTexture(root, ChickenGuiTextures.get("elements/viable_arrow"))
                .setTooltipDelay(0)
                .setTooltip(Component.translatable("gui.chickens.ovoscope.viable"));
        Constraints.size(viableTex, 24, 16);
        Constraints.placeOutside(viableTex, inputSlot, Constraints.LayoutPos.MIDDLE_RIGHT, 4, 0);

        GuiSlots viableSlot = GuiSlots.singleSlot(root, screenAccess, menu.viable);
        Constraints.placeOutside(viableSlot, viableTex, Constraints.LayoutPos.MIDDLE_RIGHT, 2, 0);

        GuiTexture nonViableTex = new GuiTexture(root, ChickenGuiTextures.get("elements/non_viable_arrow"))
                .setTooltipDelay(0)
                .setTooltip(Component.translatable("gui.chickens.ovoscope.non_viable"));
        Constraints.size(nonViableTex, 24, 16);
        Constraints.placeOutside(nonViableTex, inputSlot, Constraints.LayoutPos.MIDDLE_LEFT, -4, 0);

        GuiSlots nonViableSlot = GuiSlots.singleSlot(root, screenAccess, menu.nonViable);
        Constraints.placeOutside(nonViableSlot, nonViableTex, Constraints.LayoutPos.MIDDLE_LEFT, -2, 0);

        if (Config.INSTANCE.enableEnergy) {
            GuiSlots energySlot = GuiSlots.singleSlot(root, screenAccess, menu.energySlot)
                    .setEmptyIcon(PolyTextures.get("slots/energy"))
                    .constrain(LEFT, match(playInv.container.get(LEFT)))
                    .constrain(BOTTOM, relative(invTitle.get(TOP), -2));

            var energyBar = GuiEnergyBar.simpleBar(root);
            energyBar.container
                    .constrain(TOP, relative(root.get(TOP), 5))
                    .constrain(BOTTOM, relative(energySlot.get(TOP), -1))
                    .constrain(LEFT, relative(energySlot.get(LEFT), 0))
                    .constrain(RIGHT, relative(energySlot.get(RIGHT), 0));
            energyBar.primary
                    .setCapacity(tile.energy::getMaxEnergyStored)
                    .setEnergy(menu.energy::get);
        }

        GuiButton rsButton = GuiButton.redstoneButton(root, tile);
        Constraints.placeInside(rsButton, root, Constraints.LayoutPos.TOP_RIGHT, -4, 4);
    }

    public static class Scanner extends GuiElement<Scanner> implements ForegroundRender {
        private static final Random randy = new Random();
        private final OvoscopeMenu menu;

        public Scanner(@NotNull GuiParent<?> parent, OvoscopeMenu menu) {
            super(parent);
            this.menu = menu;
        }

        @Override
        public void renderInFront(GuiRender render, double mouseX, double mouseY, float partialTicks) {
            ItemStack stack = menu.input.getSlot(0).getItem();
            if (stack.isEmpty() || !(stack.getItem() instanceof ItemChickenEgg eggItem) || !menu.scanning.get()) {
                return;
            }
            ChickensRegistryItem type = eggItem.getType(stack);
            if (type == null) return;
            int colour = type.getBgColor();
            randy.setSeed(menu.scanCount.get());
            int pIndex = randy.nextInt(1, 4);
            boolean viable = eggItem.isViable(stack);
//            float progress = Math.max(0, -1F + ((System.currentTimeMillis() % 2000) / 1000F));
            float progress = menu.progress.get() / (float) Config.INSTANCE.ovoscopeProcessTime;

            float barWidth = (Math.min(1, progress * 8) - Math.max(0, (progress - (7/8F)) * 8)) * 9;
            float barPos = Math.min(Math.max(progress - (1/8F), 0), 6/8F) / 0.75F;
            barPos = (float) Math.sin(barPos * Math.PI) * 16F;

            render.pose().pushPose();
            render.pose().translate(0, 0, 300);


            Material egg = ChickenGuiTextures.get("elements/egg");
            Material backing = ChickenGuiTextures.get("scope_patterns/backing");
            Material pattern = ChickenGuiTextures.get("scope_patterns/" + (viable ? "viable_" : "non_viable_") + pIndex);

            render.texRect(backing, xCenter() - 8, yCenter() - 8, 16, 16);
            render.texRect(pattern, xCenter() - 8, yCenter() - 8, 16, 16, 0xFFFF0000);
            render.texRect(egg, xCenter() - 8, yCenter() - 8, 16, 16, 0xBB000000 | colour);

            render.fill(xCenter() - barWidth, yMin() + 1 + barPos, xCenter() + barWidth, yMin() + 2 + barPos, 0xFFFF0000);

            render.pose().popPose();
        }
    }


    public static class Screen extends ModularGuiContainer<OvoscopeMenu> {
        public Screen(OvoscopeMenu menu, Inventory inv, Component title) {
            super(menu, inv, new OvoscopeGui());
            getModularGui().setGuiTitle(title);
        }
    }
}
