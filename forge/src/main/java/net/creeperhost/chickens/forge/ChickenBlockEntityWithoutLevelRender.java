package net.creeperhost.chickens.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.client.RenderChickenItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChickenBlockEntityWithoutLevelRender extends BlockEntityWithoutLevelRenderer implements ItemPropertyFunction
{
    public static ChickenBlockEntityWithoutLevelRender instance;

    public ChickenBlockEntityWithoutLevelRender(BlockEntityRenderDispatcher dispatcher, EntityModelSet entityModelSet)
    {
        super(dispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemStack, ItemDisplayContext transformType, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay)
    {
        RenderChickenItem.renderByItem(itemStack, transformType, poseStack, bufferSource, combinedLight, combinedOverlay);
    }

    public static ChickenBlockEntityWithoutLevelRender getInstance()
    {
        if (instance == null)
        {
            instance = new ChickenBlockEntityWithoutLevelRender(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        }
        return instance;
    }

    @Override
    public float call(@NotNull ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int pSed)
    {
        return 1F;
    }
}
