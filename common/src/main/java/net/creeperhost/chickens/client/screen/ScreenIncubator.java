//package net.creeperhost.chickens.client.screen;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.creeperhost.chickens.Chickens;
//import net.creeperhost.chickens.containers.IncubatorMenu;
//import net.creeperhost.chickens.network.PacketHandler;
//import net.creeperhost.chickens.network.packets.PacketIncubator;
//import net.creeperhost.polylib.client.screenbuilder.ScreenBuilder;
//import net.creeperhost.polylib.inventory.PolyFluidInventory;
//import net.minecraft.client.gui.components.Button;
//import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.player.Inventory;
//import org.jetbrains.annotations.NotNull;
//
//public class ScreenIncubator extends AbstractContainerScreen<IncubatorMenu>
//{
//    ScreenBuilder screenBuilder = new ScreenBuilder();
//
//    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Chickens.MOD_ID, "textures/gui/incubator.png");
//    private final IncubatorMenu containerIncubator;
//
//    public ScreenIncubator(IncubatorMenu containerIncubator, Inventory playerInv, Component title)
//    {
//        super(containerIncubator, playerInv, title);
//        this.containerIncubator = containerIncubator;
//        this.imageHeight = 166;
//    }
//
//    @Override
//    public void renderBg(@NotNull PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
//    {
//        screenBuilder.drawDefaultBackground(this, poseStack, leftPos, topPos, imageWidth, imageHeight, 256, 256);
//        screenBuilder.drawPlayerSlots(this, poseStack, leftPos + imageWidth / 2, topPos + 83, true, 256, 256);
//
//
//        int i = 0;
//        for (int l = 0; l < 3; ++l)
//        {
//            for (int k = 0; k < 3; ++k)
//            {
//                screenBuilder.drawSlot(this, poseStack, leftPos + 60 + k * 18, topPos + l * 18 + 16, 256, 256);
//                i++;
//            }
//        }
//        screenBuilder.drawSlot(this, poseStack, leftPos + imageWidth - 28, topPos + 62, 256, 256);
//
//        screenBuilder.drawBar(this, poseStack, leftPos + 7, topPos + 7, 71,  containerIncubator.getTemp(), 60, mouseX, mouseY, Component.literal(containerIncubator.getTemp() + "c"));
//        drawBarOverlay(poseStack, leftPos + 7, topPos + 7, 71);
//        PolyFluidInventory polyFluidInventory = containerIncubator.getBlockEntityIncubator().fluidTank;
//        screenBuilder.drawTankWithOverlay(this, poseStack, polyFluidInventory.getFluidStack(), polyFluidInventory.getCapacity(),
//                (leftPos + imageWidth) - 30, topPos + 5, 50, mouseX, mouseY);
//
//    }
//
//    @Override
//    public void init()
//    {
//        super.init();
//        addRenderableWidget(new Button(leftPos + 30, topPos + 15, 20, 20, Component.literal("+"), button -> PacketHandler.HANDLER.sendToServer(new PacketIncubator(containerIncubator.getBlockPos(), true))));
//        addRenderableWidget(new Button(leftPos + 30, topPos + 50, 20, 20, Component.literal("-"), button -> PacketHandler.HANDLER.sendToServer(new PacketIncubator(containerIncubator.getBlockPos(), false))));
//    }
//
//    @Override
//    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY)
//    {
//    }
//
//    @Override
//    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
//    {
//        this.renderBackground(poseStack);
//        super.render(poseStack, mouseX, mouseY, partialTicks);
//
//        font.draw(poseStack, String.valueOf(containerIncubator.getLightLevel()), leftPos + 36, topPos + 38, 0);
//
//        renderTooltip(poseStack, mouseX, mouseY);
//    }
//
//
//    public void drawBarOverlay(PoseStack poseStack, int x, int y, int height)
//    {
//        poseStack.pushPose();
//        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
//        blit(poseStack, x + 1, y - 10, 188, 0, 16, height);
//        poseStack.popPose();
//    }
//}
