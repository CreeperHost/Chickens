package net.creeperhost.chickens.compat.jade;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public class JadePlugin implements IWailaPlugin
{
    private static final ResourceLocation ENTITY_DATA_ID = ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "entity_data");

    @Override
    public void register(IWailaCommonRegistration registration)
    {
        registration.registerEntityDataProvider(new IServerDataProvider<>()
        {
            @Override
            public void appendServerData(CompoundTag tag, EntityAccessor accessor)
            {
                if(accessor.getEntity() instanceof EntityChickensChicken entityChickensChicken)
                {
                    tag.putInt("lifespan", entityChickensChicken.getLifeSpan());
                }
            }

            @Override
            public ResourceLocation getUid()
            {
                return ENTITY_DATA_ID;
            }
        }, EntityChickensChicken.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(new IEntityComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig iPluginConfig) {
                tooltip.add(Component.literal("Lifespan " + accessor.getServerData().get("lifespan")));
            }

            @Override
            public ResourceLocation getUid() {
                return ENTITY_DATA_ID;
            }
        }, EntityChickensChicken.class);
    }
}
