package net.creeperhost.chickens.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.containers.ContainerIncubator;
import net.creeperhost.chickens.containers.ContainerRoost;
import net.creeperhost.chickens.network.PacketHandler;
import net.creeperhost.chickens.network.PacketIncubator;
import net.creeperhost.polylib.client.fluid.ScreenFluidRenderer;
import net.minecraft.client.gui.components.Button;
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
    public void init()
    {
        super.init();
        addRenderableWidget(new Button(leftPos + 30, topPos + 15, 20, 20, Component.literal("+"), button -> PacketHandler.HANDLER.sendToServer(new PacketIncubator(containerIncubator.getBlockPos(), true))));
        addRenderableWidget(new Button(leftPos + 30, topPos + 50, 20, 20, Component.literal("-"), button -> PacketHandler.HANDLER.sendToServer(new PacketIncubator(containerIncubator.getBlockPos(), false))));
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
        font.draw(poseStack, String.valueOf(containerIncubator.getLightLevel()), leftPos + 36, topPos + 38, 0);
        drawBar(poseStack, leftPos + 7, topPos + 7, 71, containerIncubator.getTemp(), 60, mouseX, mouseY);
        drawTank(poseStack, leftPos + 133, topPos + 19, 60, containerIncubator.getTemp(), 60, mouseX, mouseY);

        renderTooltip(poseStack, mouseX, mouseY);
    }


    public void drawBar(PoseStack poseStack, int x, int y, int height, int value, int maxValue, int mouseX, int mouseY)
    {
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int draw = (int) ((double) value / (double) maxValue * (height - 2));
        blit(poseStack, x + 1, y + height - draw - 1, 176, height - draw, 12, draw, 256, 256);
        blit(poseStack, x + 1, y - 10, 188, 0, 16, height);

        if (isInRect(x, y, 14, height, mouseX, mouseY))
        {
            renderTooltip(poseStack, Component.literal(containerIncubator.getTemp() + "c"), mouseX, mouseY);
        }
        poseStack.popPose();
    }

    public void drawTank(PoseStack poseStack, int x, int y, int height, int value, int maxValue, int mouseX, int mouseY)
    {
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        ScreenFluidRenderer screenFluidRenderer = new ScreenFluidRenderer(1000, 14, 48, 16);
        screenFluidRenderer.render(x, y, containerIncubator.getBlockEntityIncubator().fluidTank.getFluidStack());

        if (isInRect(x, y, 14, height, mouseX, mouseY))
        {
            int stored = (int) containerIncubator.getBlockEntityIncubator().fluidTank.getFluidStack().getAmount();
            if(stored > 0)
            {
                renderTooltip(poseStack, Component.literal(stored + "mb of Water"), mouseX, mouseY);
            }
            else
            {
                renderTooltip(poseStack, Component.literal("Empty"), mouseX, mouseY);
            }
        }
        poseStack.popPose();
    }


    public boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
    {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }
}
