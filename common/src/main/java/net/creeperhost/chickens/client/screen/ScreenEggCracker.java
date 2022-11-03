package net.creeperhost.chickens.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.containers.ContainerEggCracker;
import net.creeperhost.polylib.client.screenbuilder.ScreenBuilder;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ScreenEggCracker extends AbstractContainerScreen<ContainerEggCracker>
{
    ScreenBuilder screenBuilder = new ScreenBuilder();
    private final ContainerEggCracker containerEggCracker;

    public ScreenEggCracker(ContainerEggCracker containerEggCracker, Inventory playerInv, Component title)
    {
        super(containerEggCracker, playerInv, title);
        this.containerEggCracker = containerEggCracker;
        this.imageHeight = 166;
    }

    @Override
    public void renderBg(@NotNull PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        screenBuilder.drawDefaultBackground(this, poseStack, leftPos, topPos, imageWidth, imageHeight, 256, 256);
        screenBuilder.drawPlayerSlots(this, poseStack, leftPos + imageWidth / 2, topPos + 83, true, 256, 256);

        screenBuilder.drawSlot(this, poseStack, leftPos + 25, topPos + 33, 256, 256);

        int i = 0;
        for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 3; ++k)
            {
                screenBuilder.drawSlot(this, poseStack, leftPos + 104 + k * 18, topPos + l * 18 + 16, 256, 256);
                i++;
            }
        }

        screenBuilder.drawProgressBar(this, poseStack, containerEggCracker.getProgress(), 100, leftPos + 60, topPos + 33, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY)
    {
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);

        renderTooltip(poseStack, mouseX, mouseY);
    }
}
