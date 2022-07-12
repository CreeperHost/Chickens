package net.creeperhost.chickens.forge;

import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ItemChickenForge extends ItemChicken
{
    public ItemChickenForge(Properties properties)
    {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return ChickenBlockEntityWithoutLevelRender.getInstance();
            }
        });
    }
}
