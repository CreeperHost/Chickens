package net.creeperhost.chickens.item;

import net.creeperhost.chickens.data.ChickenStats;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.handler.IColorSource;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ItemSpawnEgg extends Item implements IColorSource
{
    public ItemSpawnEgg(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> subItems)
    {
        if (this.allowedIn(tab))
        {
            for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
            {
                ItemStack itemstack = new ItemStack(this, 1);
                applyEntityIdToItemStack(itemstack, chicken.getRegistryName());
                subItems.add(itemstack);
            }
        }
    }

    @Override
    public Component getName(ItemStack stack)
    {
        ChickensRegistryItem chickenDescription = ChickensRegistry.getByRegistryName(getTypeFromStack(stack));
        if (chickenDescription == null) return Component.translatable("nul1");
        return Component.translatable("entity.chickens." + chickenDescription.getEntityName());
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        ChickensRegistryItem chickenDescription = ChickensRegistry.getByRegistryName(getTypeFromStack(stack));
        if (chickenDescription == null) return 0;
        return renderPass == 0 ? chickenDescription.getBgColor() : chickenDescription.getFgColor();
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext)
    {
        Level level = useOnContext.getLevel();
        if (!level.isClientSide)
        {
            InteractionHand hand = useOnContext.getHand();
            ItemStack stack = useOnContext.getPlayer().getItemInHand(hand);
            BlockPos blockPos = correctPosition(useOnContext.getClickedPos(), useOnContext.getClickedFace());
            activate(stack, level, blockPos);
            if (!useOnContext.getPlayer().isCreative())
            {
                stack.shrink(1);
            }
        }
        return InteractionResult.PASS;
    }

    public static BlockPos correctPosition(BlockPos pos, Direction side)
    {
        final int[] offsetsXForSide = new int[]{0, 0, 0, 0, -1, 1};
        final int[] offsetsYForSide = new int[]{-1, 1, 0, 0, 0, 0};
        final int[] offsetsZForSide = new int[]{0, 0, -1, 1, 0, 0};

        int posX = pos.getX() + offsetsXForSide[side.ordinal()];
        int posY = pos.getY() + offsetsYForSide[side.ordinal()];
        int posZ = pos.getZ() + offsetsZForSide[side.ordinal()];

        return new BlockPos(posX, posY, posZ);
    }

    public static void activate(ItemStack stack, Level worldIn, BlockPos pos)
    {
        ResourceLocation entityName = ResourceLocation.tryParse(getTypeFromStack(stack));
        EntityChickensChicken entity = (EntityChickensChicken) Registry.ENTITY_TYPE.get(entityName).create(worldIn);
        if(entity != null && stack.getTag() != null)
        {
            if(stack.getTag().contains("baby"))
               entity.setBaby(stack.getTag().getBoolean("baby"));
            if(stack.getTag().contains("love"))
                entity.setInLoveTime(stack.getTag().getInt("love"));
        }

        ChickenStats chickenStats = new ChickenStats(stack);
        if (entity == null) return;

        entity.setStatsAnalyzed(true);
        entity.setGain(chickenStats.getGain());
        entity.setStrength(chickenStats.getStrength());
        entity.setGrowth(chickenStats.getGrowth());

        entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        entity.setChickenType(getTypeFromStack(stack));

        worldIn.addFreshEntity(entity);
    }


    public static void applyEntityIdToItemStack(ItemStack stack, ResourceLocation entityId)
    {
        CompoundTag nbttagcompound = stack.hasTag() ? stack.getTag() : new CompoundTag();
        CompoundTag nbttagcompound1 = new CompoundTag();
        nbttagcompound1.putString("id", entityId.toString());
        nbttagcompound.put("ChickenType", nbttagcompound1);
        stack.setTag(nbttagcompound);
    }

    @Nullable
    public static String getTypeFromStack(ItemStack stack)
    {
        CompoundTag nbttagcompound = stack.getTag();

        if (nbttagcompound != null && nbttagcompound.contains("ChickenType", 10))
        {
            new CompoundTag();
            CompoundTag chickentag = nbttagcompound.getCompound("ChickenType");
            return chickentag.getString("id");
        }
        return null;
    }
}
