package net.creeperhost.chickens.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;

public class EntityRooster extends Chicken
{
    public EntityRooster(EntityType<? extends Chicken> entityType, Level level)
    {
        super(entityType, level);
    }
}
