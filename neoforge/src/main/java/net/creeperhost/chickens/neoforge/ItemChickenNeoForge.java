package net.creeperhost.chickens.neoforge;

import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ItemChickenNeoForge extends ItemChicken
{
    public ItemChickenNeoForge(Properties properties)
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
