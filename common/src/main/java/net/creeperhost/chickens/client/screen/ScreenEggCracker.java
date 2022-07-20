package net.creeperhost.chickens.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.containers.ContainerEggCracker;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ScreenEggCracker extends AbstractContainerScreen<ContainerEggCracker>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Chickens.MOD_ID, "textures/gui/egg_cracker.png");
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
