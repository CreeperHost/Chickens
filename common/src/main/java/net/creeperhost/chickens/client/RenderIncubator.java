package net.creeperhost.chickens.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.creeperhost.chickens.blockentities.IncubatorBlockEntity;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RenderIncubator implements BlockEntityRenderer<IncubatorBlockEntity>
{
    @Override
    public void render(@NotNull IncubatorBlockEntity blockEntity, float f, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, int j)
    {
        if(blockEntity.getLevel() == null) return;

        for (int i1 = 0; i1 < blockEntity.getContainer().getContainerSize(); i1++)
        {
            if(blockEntity.getContainer().getItem(i1).isEmpty()) continue;

            ItemStack stack = blockEntity.getContainer().getItem(i1);
            if(stack.getItem() instanceof ItemChickenEgg || stack.getItem() instanceof ItemChicken)
            {
                poseStack.pushPose();
                double offsetX = 0.25D;
                double offsetZ = 0.25D;

                if(i1 <= 2)
                {
                    offsetX = offsetX * i1;
                }
                if(i1 > 2 && i1 <= 5)
                {
                    int z = i1 -3;
                    offsetX = offsetX * z;
                    offsetZ = offsetZ * 2;
                }
                if(i1 > 5 && i1 <= 8)
                {
                    int z = i1 -6;
                    offsetX = offsetX * z;
                    offsetZ = offsetZ * 3;
                }


                poseStack.translate(offsetX + 0.25D, 0.3D, offsetZ);
                float rotation = (float) (blockEntity.getLevel().getGameTime() % 80);

                poseStack.scale(.2f, .2f, .2f);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(360f * rotation / 80f));

                Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, i, j, poseStack, multiBufferSource, 0);
                poseStack.popPose();
            }
        }
    }
}
