package net.creeperhost.chickens.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class RenderChickenItem extends BlockEntityWithoutLevelRenderer  implements ItemPropertyFunction
{
    public static RenderChickenItem instance;

    public RenderChickenItem(BlockEntityRenderDispatcher dispatcher, EntityModelSet entityModelSet)
    {
        super(dispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay)
    {
        Minecraft mc = Minecraft.getInstance();
        EntityType<?> entityType = Registry.ENTITY_TYPE.get(ResourceLocation.tryParse(ItemChicken.getTypeFromStack(itemStack)));
        EntityChickensChicken chicken = (EntityChickensChicken) entityType.create(Minecraft.getInstance().level);
        //Force the head rot in position to stop it bouncing
        chicken.yHeadRot = 0;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);

        EntityRenderDispatcher entityRenderDispatcher = mc.getEntityRenderDispatcher();
        entityRenderDispatcher.setRenderShadow(false);
        entityRenderDispatcher.setRenderHitBoxes(false);
        MultiBufferSource.BufferSource irendertypebuffer$impl = mc.renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(chicken, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack, irendertypebuffer$impl, 15728880);
        });

        poseStack.popPose();
    }

    public static RenderChickenItem getInstance()
    {
        if (instance == null)
        {
            instance = new RenderChickenItem(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        }
        return instance;
    }

    @Override
    public float call(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int pSed)
    {
        return 1F;
    }
}
