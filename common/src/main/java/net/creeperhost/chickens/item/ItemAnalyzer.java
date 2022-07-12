package net.creeperhost.chickens.item;

import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemAnalyzer extends Item
{
    public ItemAnalyzer(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag)
    {
        super.appendHoverText(stack, level, components, tooltipFlag);
        components.add(Component.translatable("item.analyzer.tooltip1"));
        components.add(Component.translatable("item.analyzer.tooltip2"));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack itemStack, @NotNull Player playerIn, LivingEntity target, @NotNull InteractionHand hand)
    {
        if (target.level.isClientSide || !(target instanceof EntityChickensChicken))
        {
            return InteractionResult.FAIL;
        }

        EntityChickensChicken chicken = (EntityChickensChicken) target;
        chicken.setStatsAnalyzed(true);

        Component chickenName = chicken.getName();
        playerIn.displayClientMessage(chickenName, false);

        playerIn.displayClientMessage(Component.translatable("entity.ChickensChicken.tier", chicken.getTier()), false);

        playerIn.displayClientMessage(Component.translatable("entity.ChickensChicken.growth", chicken.getGrowth()), false);
        playerIn.displayClientMessage(Component.translatable("entity.ChickensChicken.gain", chicken.getGain()), false);
        playerIn.displayClientMessage(Component.translatable("entity.ChickensChicken.strength", chicken.getStrength()), false);

        if (!chicken.isBaby())
        {
            int layProgress = chicken.getLayProgress();
            if (layProgress <= 0)
            {
                playerIn.displayClientMessage(Component.translatable("entity.ChickensChicken.nextEggSoon"), false);
            }
            else
            {
                playerIn.displayClientMessage(Component.translatable("entity.ChickensChicken.layProgress", layProgress), false);
            }
        }
        return InteractionResult.PASS;
    }
}
