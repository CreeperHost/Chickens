package net.creeperhost.chickens.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.containers.ContainerBreeder;
import net.creeperhost.chickens.containers.ContainerOvoscope;
import net.creeperhost.polylib.client.screenbuilder.ScreenBuilder;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ScreenOvoscope extends AbstractContainerScreen<ContainerOvoscope>
{
    ScreenBuilder screenBuilder = new ScreenBuilder();
    private final ContainerOvoscope containerOvoscope;

    public ScreenOvoscope(ContainerOvoscope containerOvoscope, Inventory playerInv, Component title)
    {
        super(containerOvoscope, playerInv, title);
        this.containerOvoscope = containerOvoscope;
        this.imageWidth = 190;
        this.imageHeight = 220;
        this.inventoryLabelY = 118;
    }

    @Override
    public void renderBg(@NotNull PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        renderBackground(poseStack);
        screenBuilder.drawDefaultBackground(this, poseStack, leftPos, topPos, imageWidth, imageHeight, 256, 256);
        screenBuilder.drawPlayerSlots(this, poseStack, leftPos + imageWidth / 2, topPos + 131, true, 256, 256);

        screenBuilder.drawSlot(this, poseStack, leftPos + 88, topPos + 26, 256, 256);
        screenBuilder.drawSlot(this, poseStack, leftPos + 32, topPos + 60, 256, 256);
        screenBuilder.drawSlot(this, poseStack, leftPos + 140, topPos + 60, 256, 256);

    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderTooltip(poseStack, mouseX, mouseY);
    }
}
