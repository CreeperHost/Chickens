package net.creeperhost.chickens.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.blockentities.BlockEntityHenhouse;
import net.creeperhost.chickens.containers.ContainerHenhouse;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class GuiHenhouse extends AbstractContainerScreen<ContainerHenhouse>
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ChickensMod.MODID, "textures/gui/henhouse.png");
    private final ContainerHenhouse containerHenhouse;

    public GuiHenhouse(ContainerHenhouse containerHenhouse, Inventory playerInv, Component title)
    {
        super(containerHenhouse, playerInv, title);
        this.containerHenhouse = containerHenhouse;
        this.imageHeight = 166;
    }

    @Override
    public void renderBg(@NotNull PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;
        blit(poseStack, i, j, 0, 0, imageWidth, imageHeight);

        int energy = containerHenhouse.getEnergy();
        System.out.println(energy);
        final int BAR_HEIGHT = 57;
        int offset = BAR_HEIGHT - (energy * BAR_HEIGHT / BlockEntityHenhouse.hayBaleEnergy);
        blit(poseStack, i + 75, j + 14 + offset, 195, offset, 12, BAR_HEIGHT - offset);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBg(poseStack, partialTicks, mouseX, mouseY);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderTooltip(poseStack, mouseX, mouseY);
    }
}
