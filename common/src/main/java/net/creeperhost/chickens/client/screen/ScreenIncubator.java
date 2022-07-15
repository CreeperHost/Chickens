package net.creeperhost.chickens.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.containers.ContainerIncubator;
import net.creeperhost.chickens.containers.ContainerRoost;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ScreenIncubator extends AbstractContainerScreen<ContainerIncubator>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Chickens.MOD_ID, "textures/gui/incubator.png");
    private final ContainerIncubator containerIncubator;

    public ScreenIncubator(ContainerIncubator containerIncubator, Inventory playerInv, Component title)
    {
        super(containerIncubator, playerInv, title);
        this.containerIncubator = containerIncubator;
        this.imageHeight = 166;
    }

    @Override
    public void renderBg(@NotNull PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;
        blit(poseStack, i, j, 0, 0, imageWidth, imageHeight);
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

    public boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
    {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }
}
