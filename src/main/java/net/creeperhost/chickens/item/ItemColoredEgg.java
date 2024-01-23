package net.creeperhost.chickens.item;

import net.creeperhost.chickens.entity.EntityColoredEgg;
import net.creeperhost.chickens.handler.IColorSource;
import net.creeperhost.chickens.init.ModChickens;
import net.creeperhost.chickens.init.ModEntities;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemColoredEgg extends Item implements IColorSource
{
    public ItemColoredEgg(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        try
        {
            if (!stack.hasTag()) {
                return 0;
            }
            int colourid = stack.getTag().getInt("colourid");
            DyeColor dyeColor = DyeColor.byId(colourid);
            if (dyeColor != null)
            {
                return ModChickens.getRGB(dyeColor);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_)
    {
        super.appendHoverText(itemStack, p_41422_, tooltip, p_41424_);
        tooltip.add(new TranslatableComponent("item.colored_egg.tooltip"));
    }

    @Override
    public Component getName(ItemStack stack)
    {
        if (!stack.hasTag()) return new TextComponent("null");

        String name = stack.getTag().getString("id");
        String[] split = name.split(":");
        String s = split[1].replace("_chicken", "");

        return new TranslatableComponent("item.colored_egg." + s + ".name");
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> subItems)
    {
        if (this.allowdedIn(tab))
        {
            try
            {
                if (ChickensRegistry.getItems() != null)
                {
                    for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
                    {
                        if (chicken.isDye())
                        {
                            ItemStack stack = new ItemStack(this, 1);
                            CompoundTag compoundTag = new CompoundTag();
                            compoundTag.putString("id", chicken.getRegistryName().toString());
                            if (chicken.getLayItemHolder().getItem() instanceof DyeItem dyeItem)
                            {
                                DyeColor dyeColor = dyeItem.getDyeColor();
                                int id = dyeColor.getId();
                                compoundTag.putInt("colourid", id);
                            }
                            stack.setTag(compoundTag);
                            subItems.add(stack);
                        }
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        ItemStack itemStackIn = player.getItemInHand(interactionHand);

        if (!player.isCreative())
        {
            itemStackIn.shrink(1);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide)
        {
            String chickenType = itemStackIn.getTag().getString("id");
            if (chickenType != null)
            {
                EntityColoredEgg entityIn = ModEntities.EGG.get().create(level);
                entityIn.setItem(itemStackIn);
                entityIn.setChickenType(chickenType);
                entityIn.setPos(player.getX(), player.getEyeY() - 0.1D, player.getZ());
                entityIn.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                entityIn.setOwner(player);

                level.addFreshEntity(entityIn);
            }
        }

        return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, itemStackIn);
    }
}
