package net.creeperhost.chickens.client.screen;

import com.mojang.math.Axis;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.blockentities.EggCrackerBlockEntity;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.containers.EggCrackerMenu;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.polylib.client.modulargui.ModularGui;
import net.creeperhost.polylib.client.modulargui.ModularGuiContainer;
import net.creeperhost.polylib.client.modulargui.elements.*;
import net.creeperhost.polylib.client.modulargui.lib.BackgroundRender;
import net.creeperhost.polylib.client.modulargui.lib.Constraints;
import net.creeperhost.polylib.client.modulargui.lib.GuiRender;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerGuiProvider;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerScreenAccess;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Align;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Direction;
import net.creeperhost.polylib.client.modulargui.lib.geometry.GuiParent;
import net.creeperhost.polylib.client.modulargui.sprite.Material;
import net.creeperhost.polylib.client.modulargui.sprite.PolyTextures;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.*;

/**
 * Created by brandon3055 on 01/03/2024
 */
public class EggCrackerGui extends ContainerGuiProvider<EggCrackerMenu> {
    public static final int GUI_WIDTH = 176;
    public static final int GUI_HEIGHT = 185;

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

        GuiSlots outputSlots = new GuiSlots(root, screenAccess, menu.output, 6);
        Constraints.placeOutside(outputSlots, playInv.container, Constraints.LayoutPos.TOP_CENTER, 0, -12);

        GuiSlots inputSlot = new GuiSlots(root, screenAccess, menu.input, 1);
        Constraints.placeOutside(inputSlot, outputSlots, Constraints.LayoutPos.TOP_CENTER, 0, -36);

        reverseEggDropChallenge(root, menu, inputSlot, outputSlots);

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

        GuiButton rsButton = GuiButton.redstoneButton(root, tile);
        Constraints.placeOutside(rsButton, tank.container, Constraints.LayoutPos.TOP_LEFT, -2, 12);
    }

    private void reverseEggDropChallenge(GuiElement<?> root, EggCrackerMenu menu, GuiSlots inputSlot, GuiSlots outputSlots) {
        GuiProgressIcon arrow = new GuiProgressIcon(root)
                .setBackground(PolyTextures.get("widgets/progress_arrow_empty"))
                .setAnimated(PolyTextures.get("widgets/progress_arrow_full"))
                .setDirection(Direction.DOWN)
                .setProgress(() -> menu.progress.get() / (double) Config.INSTANCE.crackerProcessTime);
        Constraints.size(arrow, 16, 22);
        Constraints.placeOutside(arrow, inputSlot, Constraints.LayoutPos.BOTTOM_CENTER, 0, 5);

        EggDropTrapDoor trapDoor = new EggDropTrapDoor(root, menu);
        Constraints.bind(trapDoor, inputSlot);

        trapDoor.floorPlate = new GuiRectangle(root).fill(0xFF000000);
        Constraints.size(trapDoor.floorPlate, 32, 2);
        Constraints.placeOutside(trapDoor.floorPlate, outputSlots, Constraints.LayoutPos.TOP_CENTER, 0, -1);
    }

    private static class EggDropTrapDoor extends GuiElement<EggDropTrapDoor> implements BackgroundRender {
        private final EggCrackerMenu menu;
        private GuiElement<?> floorPlate;

        public EggDropTrapDoor(@NotNull GuiParent<?> parent, EggCrackerMenu menu) {
            super(parent);
            this.menu = menu;
        }

        @Override
        public void renderBehind(GuiRender render, double mouseX, double mouseY, float partialTicks) {
            ItemStack stack = menu.input.getSlot(0).getItem();
            float progress = Math.min(menu.progress.get() + partialTicks, Config.INSTANCE.crackerProcessTime);
            if (stack.isEmpty() || !(stack.getItem() instanceof ItemChickenEgg eggItem) || progress == 0) {
                return;
            }
            ChickensRegistryItem type = eggItem.getType(stack);
            if (type == null) return;
            int colour = type.getBgColor();

            float animation = progress / (float) Config.INSTANCE.crackerProcessTime;
            float maxAnimLen = 30;
            if (Config.INSTANCE.crackerProcessTime > maxAnimLen) {
                float remain = Config.INSTANCE.crackerProcessTime - progress;
                animation = remain < maxAnimLen ? 1F - (remain / maxAnimLen) : 0;
            }

            if (animation <= 0) return;
            render.fill(xMin() + 1, yMax() - 1, xMax() - 1, yMax(), 0xFF8b8b8b);

            float doorAnim = (float) Math.sin(Math.min(animation * 1.1, 1) * Math.PI);
            render.pose().pushPose();
            render.pose().translate(xMin() + 1.5, yMax() - 0.5, 0);
            render.pose().mulPose(Axis.ZP.rotationDegrees(doorAnim * 125));
            render.rect(-0.5, -0.5, 8, 1, 0xFFFFFFFF);
            render.pose().popPose();

            render.pose().pushPose();
            render.pose().translate(xMax() - 1.5, yMax() - 0.5, 0);
            render.pose().mulPose(Axis.ZP.rotationDegrees(180 - (doorAnim * 125)));
            render.rect(-0.5, -0.5, 8, 1, 0xFFFFFFFF);
            render.pose().popPose();

            Material eggMat = ChickenGuiTextures.get("elements/egg");
            Material eggCrackedMat = ChickenGuiTextures.get("elements/egg_cracked");

            float dropAnim = Math.min(1, (1F - (float) Math.cos(animation * Math.PI * 0.5)) * 2F);
            float sinkAnim = Math.max(0, animation - 0.5F) * 2;
            double yTravel = (floorPlate.yMin() - yCenter() - 4) * dropAnim;

            render.pose().pushPose();
            if (dropAnim < 1) {
                render.pose().translate(xCenter(), yCenter() + yTravel, 0);
                render.pose().mulPose(Axis.ZP.rotationDegrees(dropAnim * 180));
                render.texRect(eggMat, -8D, -8D, 16D, 16D, colour | 0xFF000000);
            } else {
                render.pose().translate(0, (sinkAnim * 2D), 0);
                render.texRect(eggCrackedMat, xCenter() - 8, (floorPlate.yMin() - 16), 16D, 16D, colour | (int) ((1 - sinkAnim) * 0xFF) << 24);
            }
            render.pose().popPose();
        }
    }

    public static class Screen extends ModularGuiContainer<EggCrackerMenu> {
        public Screen(EggCrackerMenu menu, Inventory inv, Component title) {
            super(menu, inv, new EggCrackerGui());
            getModularGui().setGuiTitle(title);
        }
    }
}
