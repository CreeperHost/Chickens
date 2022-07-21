package net.creeperhost.chickens.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.client.model.BakedModelWrapper;

public class ChickenBakedItemModel extends BakedModelWrapper<BakedModel>
{
    public ChickenBakedItemModel(BakedModel originalModel)
    {
        super(originalModel);
    }

    @Override
    public boolean isCustomRenderer()
    {
        return true;
    }

    @Override
    public BakedModel applyTransform(ItemTransforms.TransformType cameraTransformType, PoseStack poseStack, boolean applyLeftHandedTransform)
    {
        super.applyTransform(cameraTransformType, poseStack, applyLeftHandedTransform);
        return this;
    }
}
