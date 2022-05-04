package net.creeperhost.chickens.client.model;

import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelChickensChicken extends ChickenModel
{
    public ModelChickensChicken(ModelPart modelPart)
    {
        super(modelPart);
    }
}
