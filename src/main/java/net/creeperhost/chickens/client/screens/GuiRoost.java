package net.creeperhost.chickens.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.containers.ContainerRoost;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Created by setyc on 06.03.2016.
 */
@OnlyIn(Dist.CLIENT)
public class GuiRoost extends AbstractContainerScreen<ContainerRoost>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ChickensMod.MODID, "textures/gui/roost.png");
    private final ContainerRoost containerRoost;

    public GuiRoost(ContainerRoost containerRoost, Inventory playerInv, Component title)
    {
        super(containerRoost, playerInv, title);
        this.containerRoost = containerRoost;
        this.imageWidth = 176;
        this.imageHeight = 133;
    }

    @Override
    public void renderBg(@NotNull PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;
        blit(poseStack, i, j, 0, 0, imageWidth, imageHeight);
        blit(poseStack, i + 48, j + 20, 176, 0, getProgressWidth(), 16);
    }

    private int getProgressWidth()
    {
        double progress = containerRoost.getProgress() / 1000.0;
        return progress == 0.0D ? 0 : 1 + (int) (progress * 25);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {}

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderTooltip(poseStack, mouseX, mouseY);
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;

        if(isInRect(i + 48, j + 20, 26, 20, mouseX, mouseY))
        {
            renderTooltip(poseStack, Component.translatable(containerRoost.getProgress() / 10D + "%"), mouseX, mouseY);
        }
    }

    public boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
    {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }
}
