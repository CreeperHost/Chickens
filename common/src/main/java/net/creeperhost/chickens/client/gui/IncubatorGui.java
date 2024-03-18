package net.creeperhost.chickens.client.gui;

import net.creeperhost.chickens.blockentities.IncubatorBlockEntity;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.containers.IncubatorMenu;
import net.creeperhost.polylib.client.modulargui.ModularGui;
import net.creeperhost.polylib.client.modulargui.ModularGuiContainer;
import net.creeperhost.polylib.client.modulargui.elements.*;
import net.creeperhost.polylib.client.modulargui.lib.Constraints;
import net.creeperhost.polylib.client.modulargui.lib.GuiRender;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerGuiProvider;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerScreenAccess;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Align;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint;
import net.creeperhost.polylib.client.modulargui.sprite.PolyTextures;
import net.creeperhost.polylib.helpers.MathUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.*;

/**
 * Created by brandon3055 on 22/02/2024
 */
public class IncubatorGui extends ContainerGuiProvider<IncubatorMenu> {
    public static final int GUI_WIDTH = 180;
    public static final int GUI_HEIGHT = 176;

    @Override
    public GuiElement<?> createRootElement(ModularGui gui) {
        GuiManipulable root = new GuiManipulable(gui).addMoveHandle(3).enableCursors(true);
        GuiTexture bg = new GuiTexture(root.getContentElement(), ChickenGuiTextures.get("incubator"));
        Constraints.bind(bg, root.getContentElement());
        return root;
    }

    @Override
    public void buildGui(ModularGui gui, ContainerScreenAccess<IncubatorMenu> screenAccess) {
        gui.initStandardGui(GUI_WIDTH, GUI_HEIGHT);
        IncubatorMenu menu = screenAccess.getMenu();
        IncubatorBlockEntity tile = menu.tile;
        gui.setGuiTitle(tile.getDisplayName());
        GuiElement<?> root = gui.getRoot();

        var playInv = GuiSlots.player(root, screenAccess, menu.main, menu.hotBar);
        Constraints.placeInside(playInv.container, root, Constraints.LayoutPos.BOTTOM_CENTER, 0, -7);
        GuiText invTitle = new GuiText(playInv.container, Component.translatable("container.inventory"))
                .setAlignment(Align.LEFT)
                .setTextColour(0x404040)
                .setShadow(false)
                .constrain(WIDTH, match(playInv.container.get(WIDTH)))
                .constrain(HEIGHT, literal(8));
        Constraints.placeInside(invTitle, playInv.container, Constraints.LayoutPos.TOP_LEFT, 0, -10);

        boolean energy = Config.INSTANCE.enableEnergy;

        GuiRectangle eggsBorder = GuiRectangle.invertedSlot(root);
        GuiSlots eggSlots = new GuiSlots(root, screenAccess, menu.eggSlots, 3);
        Constraints.placeInside(eggSlots, root, Constraints.LayoutPos.TOP_CENTER, energy ? 4 : 0, 26);
        Constraints.bind(eggsBorder, eggSlots, -1);

        GuiText title = new GuiText(root, gui.getGuiTitle())
                .setTextColour(0x404040)
                .setShadow(false)
                .constrain(TOP, relative(root.get(TOP), 4))
                .constrain(HEIGHT, Constraint.literal(8))
                .constrain(LEFT, match(eggsBorder.get(LEFT)))
                .constrain(RIGHT, match(eggsBorder.get(RIGHT)));

        GuiSlots waterSlot = GuiSlots.singleSlot(root, screenAccess, menu.waterSlot)
                .setEmptyIcon(PolyTextures.get("slots/bucket"))
                .setTooltip(Component.translatable("gui.chickens.incubator.water_slot"))
                .constrain(RIGHT, match(playInv.container.get(RIGHT)))
                .constrain(BOTTOM, match(eggsBorder.get(BOTTOM)));

        var tank = GuiFluidTank.simpleTank(root);
        tank.container
                .constrain(WIDTH, literal(18))
                .constrain(LEFT, match(waterSlot.get(LEFT)))
                .constrain(BOTTOM, relative(waterSlot.get(TOP), -2))
                .constrain(TOP, relative(root.get(TOP), 6));
        tank.primary
                .setCapacity(tile.tank::getCapacity)
                .setFluidStack(menu.tank::get);

        GuiElement<?> thermometer = createThermometer(root, menu);
        Constraints.placeInside(thermometer, root, Constraints.LayoutPos.TOP_LEFT, energy ? 29 : 7, 8);

        GuiElement<?> hygrometer = createHygrometer(root, menu);
        Constraints.placeOutside(hygrometer, thermometer, Constraints.LayoutPos.MIDDLE_RIGHT, energy ? 2 : 10, 0);

        if (energy) {
            GuiSlots energySlot = GuiSlots.singleSlot(root, screenAccess, menu.energySlot)
                    .setEmptyIcon(PolyTextures.get("slots/energy"))
                    .constrain(LEFT, match(playInv.container.get(LEFT)))
                    .constrain(BOTTOM, match(eggsBorder.get(BOTTOM)));

            var energyBar = GuiEnergyBar.simpleBar(root);
            energyBar.container
                    .constrain(TOP, relative(thermometer.get(TOP), -3))
                    .constrain(BOTTOM, relative(energySlot.get(TOP), -2))
                    .constrain(LEFT, relative(energySlot.get(LEFT), 0))
                    .constrain(RIGHT, relative(energySlot.get(RIGHT), 0));
            energyBar.primary
                    .setCapacity(tile.energy::getMaxEnergyStored)
                    .setEnergy(menu.energy::get);
        }

        GuiTexture lightOff = new GuiTexture(root, ChickenGuiTextures.get("elements/incubator_light"));
        Constraints.size(lightOff, 64, 16);
        Constraints.placeOutside(lightOff, eggsBorder, Constraints.LayoutPos.TOP_CENTER, 0, 4);

        GuiTexture lightOn = new GuiTexture(root, ChickenGuiTextures.get("elements/incubator_light_on"));
        lightOn.setColour(() -> 0x00FFFFFF | ((menu.heatSetting.get() * 17) << 24));
        Constraints.size(lightOn, 64, 16);
        Constraints.placeOutside(lightOn, eggsBorder, Constraints.LayoutPos.TOP_CENTER, 0, 4);

        GuiTextList heatLabel = new GuiTextList(root)
                .setTextSupplier(() -> List.of(Component.translatable("gui.chickens.incubator.heat"), Component.literal(String.valueOf(menu.heatSetting.get()))))
                .setTextColour(0x404040)
                .setShadow(false)
                .constrain(LEFT, relative(eggsBorder.get(RIGHT), 2))
                .constrain(RIGHT, relative(tank.container.get(LEFT), -2))
                .autoHeight();
        heatLabel.constrain(TOP, midPoint(eggsBorder.get(TOP), eggsBorder.get(BOTTOM), heatLabel.ySize() / -2));

        GuiButton incButton = GuiButton.vanillaAnimated(root, Component.literal("\u2191 \u2191"))
                .setDisabled(() -> menu.heatSetting.get() >= 15)
                .onPress(() -> tile.sendDataValueToServer(tile.heatSetting, menu.heatSetting.get() + 1))
                .setTooltip(Component.translatable("gui.chickens.incubator.increase_heat"))
                .constrain(TOP, match(eggsBorder.get(TOP)))
                .constrain(LEFT, relative(eggsBorder.get(RIGHT), 5))
                .constrain(RIGHT, relative(tank.container.get(LEFT), -5))
                .constrain(HEIGHT, literal(14));

        GuiButton decButton = GuiButton.vanillaAnimated(root, Component.literal("\u2193 \u2193"))
                .setDisabled(() -> menu.heatSetting.get() <= 0)
                .onPress(() -> tile.sendDataValueToServer(tile.heatSetting, menu.heatSetting.get() - 1))
                .setTooltip(Component.translatable("gui.chickens.incubator.decrease_heat"))
                .constrain(BOTTOM, match(eggsBorder.get(BOTTOM)))
                .constrain(LEFT, relative(eggsBorder.get(RIGHT), 5))
                .constrain(RIGHT, relative(tank.container.get(LEFT), -5))
                .constrain(HEIGHT, literal(14));

        GuiButton rsButton = GuiButton.redstoneButton(root, tile);
        Constraints.placeOutside(rsButton, tank.container, Constraints.LayoutPos.TOP_LEFT, -2, 12);
    }

    public GuiElement<?> createThermometer(GuiElement<?> root, IncubatorMenu menu) {
        GuiRectangle slot = GuiRectangle.invertedSlot(root);
        GuiRectangle slot2 = GuiRectangle.vanillaSlot(root);
        Constraints.bind(slot2, slot, 1);

        GuiTexture texture = new GuiTexture(root, ChickenGuiTextures.get("elements/thermometer"));
        Constraints.size(texture, 16, 70);
        Constraints.bind(slot, texture, -3, 0, -3, 0);

        texture.setTooltipDelay(0);
        texture.setTooltip(() -> {
            List<Component> list = new ArrayList<>();
            list.add(Component.translatable("gui.chickens.incubator.temperature.info"));
            list.add(Component.translatable("gui.chickens.incubator.temperature.temp", menu.temperature.get()).withStyle(ChatFormatting.GOLD));
            if (menu.temperature.get() > IncubatorBlockEntity.TEMP_MAX) {
                list.add(Component.translatable("gui.chickens.incubator.temperature.hot").withStyle(ChatFormatting.RED));
            } else if (menu.temperature.get() < IncubatorBlockEntity.TEMP_MIN) {
                list.add(Component.translatable("gui.chickens.incubator.temperature.cold").withStyle(ChatFormatting.BLUE));
            }
            return list;
        });

        GuiRectangle zone = new GuiRectangle(texture) {
            @Override
            public void renderBehind(GuiRender render, double mouseX, double mouseY, float partialTicks) {
                render.fill(xMin(), yMin(), xMin() + 1, yMax(), 0xFF00FF00);
                render.gradientFillV(xMin(), yMax(), xMin() + 1, yMax() + 6, 0xFF0000FF, 0x00000000);
                render.gradientFillV(xMin(), yMin() - 6, xMin() + 1, yMin(), 0x00000000, 0xFFFF0000);

                render.fill(xMax() - 1, yMin(), xMax(), yMax(), 0xFF00FF00);
                render.gradientFillV(xMax() - 1, yMax(), xMax(), yMax() + 6, 0xFF0000FF, 0x00000000);
                render.gradientFillV(xMax() - 1, yMin() - 6, xMax(), yMin(), 0x00000000, 0xFFFF0000);
            }
        };
        zone.constrain(LEFT, relative(texture.get(LEFT), 6));
        zone.constrain(WIDTH, literal(4));
        zone.constrain(BOTTOM, relative(texture.get(BOTTOM), -8 - IncubatorBlockEntity.TEMP_MIN));
        zone.constrain(TOP, relative(texture.get(BOTTOM), -8 - IncubatorBlockEntity.TEMP_MAX - 1));

        GuiRectangle bar = new GuiRectangle(texture)
                .fill(0xFFFF0000)
                .constrain(LEFT, relative(texture.get(LEFT), 7))
                .constrain(WIDTH, literal(2))
                .constrain(BOTTOM, relative(texture.get(BOTTOM), -9))
                .constrain(HEIGHT, dynamic(() -> (double) MathUtil.clamp(menu.temperature.get(), 0, 60)));

        return texture;
    }

    public GuiElement<?> createHygrometer(GuiElement<?> root, IncubatorMenu menu) {
        GuiRectangle slot = GuiRectangle.invertedSlot(root);
        GuiRectangle slot2 = GuiRectangle.vanillaSlot(root);
        Constraints.bind(slot2, slot, 1);

        GuiTexture texture = new GuiTexture(root, ChickenGuiTextures.get("elements/hygrometer"));
        Constraints.size(texture, 16, 70);
        Constraints.bind(slot, texture, -3, 0, -3, 0);

        texture.setTooltipDelay(0);
        texture.setTooltip(() -> {
            List<Component> list = new ArrayList<>();
            list.add(Component.translatable("gui.chickens.incubator.hygrometer.info"));
            list.add(Component.translatable("gui.chickens.incubator.hygrometer.value", String.format("%.1f", menu.humidity.get())).withStyle(ChatFormatting.GOLD));
            if (menu.humidity.get() < IncubatorBlockEntity.MIN_HUMIDITY && menu.tank.get().isEmpty()) {
                list.add(Component.translatable("gui.chickens.incubator.hygrometer.dry").withStyle(ChatFormatting.RED));
            }
            return list;
        });

        GuiRectangle zone = new GuiRectangle(texture) {
            @Override
            public void renderBehind(GuiRender render, double mouseX, double mouseY, float partialTicks) {
                render.gradientFillV(xMin(), yMin(), xMin() + 1, yMax(), 0x00000000, 0xFF00FF00);
                render.gradientFillV(xMin(), yMax(), xMin() + 1, yMax() + 5, 0xFF00FF00, 0x77FF0000);
                render.gradientFillV(xMin(), yMax() + 5, xMin() + 1, yMax() + 10, 0x77FF0000, 0x00FF0000);

                render.gradientFillV(xMax() - 1, yMin(), xMax(), yMax(), 0x00000000, 0xFF00FF00);
                render.gradientFillV(xMax() - 1, yMax(), xMax(), yMax() + 5, 0xFF00FF00, 0x77FF0000);
                render.gradientFillV(xMax() - 1, yMax() + 5, xMax(), yMax() + 10, 0x77FF0000, 0x00FF0000);
            }
        };
        zone.constrain(LEFT, relative(texture.get(LEFT), 6));
        zone.constrain(WIDTH, literal(4));
        zone.constrain(BOTTOM, relative(texture.get(BOTTOM), -2 - IncubatorBlockEntity.MIN_HUMIDITY));
        zone.constrain(TOP, relative(texture.get(BOTTOM), -2 - IncubatorBlockEntity.MIN_HUMIDITY - 10));

        GuiTexture pointer = new GuiTexture(texture, ChickenGuiTextures.get("elements/hygro_pointer"))
                .constrain(WIDTH, literal(8))
                .constrain(HEIGHT, literal(8))
                .constrain(LEFT, relative(texture.get(LEFT), 4))
                .constrain(BOTTOM, relative(texture.get(BOTTOM), () -> 2D - MathUtil.clamp(menu.humidity.get(), 0, 65)).precise());
        return texture;
    }

    public static class Screen extends ModularGuiContainer<IncubatorMenu> {
        public Screen(IncubatorMenu menu, Inventory inv, Component title) {
            super(menu, inv, new IncubatorGui());
            getModularGui().setGuiTitle(title);
        }
    }
}
