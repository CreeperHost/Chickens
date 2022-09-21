package net.creeperhost.chickens.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.creeperhost.chickens.block.BlockRoost;
import net.creeperhost.chickens.blockentities.BlockEntityRoost;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public class RenderRoost implements BlockEntityRenderer<BlockEntityRoost>
{
    public RenderRoost() {}

    @Override
    public void render(BlockEntityRoost blockEntityRoost, float p_112308_, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int ov)
    {
        if(blockEntityRoost != null && !blockEntityRoost.inventory.getStackInSlot(0).isEmpty())
        {
            ItemStack itemStack = blockEntityRoost.inventory.getStackInSlot(0);
            if(!(itemStack.getItem() instanceof ItemChicken)) return;

            Minecraft mc = Minecraft.getInstance();
            EntityType<?> entityType = Registry.ENTITY_TYPE.get(ResourceLocation.tryParse(ItemChicken.getTypeFromStack(itemStack)));
            EntityChickensChicken chicken = (EntityChickensChicken) entityType.create(Minecraft.getInstance().level);
            //Force the head rot in position to stop it bouncing
            chicken.yHeadRot = 0;

            poseStack.pushPose();
            poseStack.translate(0.5F, -0.008F, 0.5F);
            poseStack.scale(0.9F, 0.85F, 0.9F);
            Direction direction = blockEntityRoost.getBlockState().getValue(BlockRoost.FACING);
            poseStack.mulPose(direction.getRotation());
            poseStack.mulPose(Vector3f.XP.rotationDegrees(270));


            EntityRenderDispatcher entityRenderDispatcher = mc.getEntityRenderDispatcher();
            MultiBufferSource.BufferSource irendertypebuffer$impl = mc.renderBuffers().bufferSource();
            RenderSystem.runAsFancy(() -> {
                entityRenderDispatcher.getRenderer(chicken).render(chicken, 0.0F, 0.0F, poseStack, irendertypebuffer$impl, 15728880);
            });

            poseStack.popPose();
        }
    }
}
