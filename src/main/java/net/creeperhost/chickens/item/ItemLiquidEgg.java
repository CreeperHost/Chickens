package net.creeperhost.chickens.item;

import net.creeperhost.chickens.handler.IColorSource;
import net.creeperhost.chickens.handler.LiquidEggFluidWrapper;
import net.creeperhost.chickens.registry.LiquidEggRegistry;
import net.creeperhost.chickens.registry.LiquidEggRegistryItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.RenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemLiquidEgg extends Item implements IColorSource
{
    public ItemLiquidEgg(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        if (!stack.hasTag()) return 0;
        String[] strings = stack.getTag().get("id").toString().replace("\"", "").split(":");

        ResourceLocation resourceLocation = new ResourceLocation(strings[0], strings[1]);
        Fluid fluid = Registry.FLUID.get(resourceLocation);
        if (fluid != null)
        {
            return RenderProperties.get(fluid).getColorTint();
        }
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Component.translatable("item.liquid_egg.tooltip"));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> subItems)
    {
        if (this.allowedIn(tab))
        {
            for (LiquidEggRegistryItem liquid : LiquidEggRegistry.getAll())
            {
                ItemStack itemstack = new ItemStack(this, 1);
                CompoundTag compoundTag = new CompoundTag();
                //TODO
//                compoundTag.putString("id", liquid.getFluid().getRegistryName().toString());
                itemstack.setTag(compoundTag);
                subItems.add(itemstack);
            }
        }
    }

    @Override
    public Component getName(ItemStack stack)
    {
        if (!stack.hasTag()) return super.getName(stack);

        String[] strings = stack.getTag().getString("id").split(":");
        return Component.translatable("item.liquid_egg." + strings[1] + ".name");
    }

    //TODO
//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
//        ItemStack itemStackIn = playerIn.getHeldItem(hand);
//        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);
//
//        //noinspection ConstantConditions
//        if (raytraceresult == null) {
//            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
//        } else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
//            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
//        } else {
//            BlockPos blockpos = raytraceresult.getBlockPos();
//            if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
//                return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
//            } else {
//                boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
//                BlockPos blockPos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);
//
//                Block liquid = LiquidEggRegistry.findById(itemStackIn.getMetadata()).getLiquid();
//                if (!playerIn.canPlayerEdit(blockPos1, raytraceresult.sideHit, itemStackIn)) {
//                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
//                } else if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockPos1, liquid)) {
//                    //noinspection ConstantConditions
//                    playerIn.addStat(StatList.getObjectUseStats(this));
//                    return !playerIn.capabilities.isCreativeMode ? new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(itemStackIn.getItem(), itemStackIn.getCount() - 1, itemStackIn.getMetadata())) : new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
//                } else {
//                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
//                }
//            }
//        }
//    }
//
//    public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer playerIn, World worldIn, BlockPos pos, Block liquid) {
//        Material material = worldIn.getBlockState(pos).getMaterial();
//        boolean flag = !material.isSolid();
//
//        if (!worldIn.isAirBlock(pos) && !flag) {
//            return false;
//        } else {
//            if (worldIn.provider.doesWaterVaporize() && liquid == Blocks.FLOWING_WATER) {
//                int i = pos.getX();
//                int j = pos.getY();
//                int k = pos.getZ();
//                worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
//
//                for (int l = 0; l < 8; ++l) {
//                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) i + Math.random(), (double) j + Math.random(), (double) k + Math.random(), 0.0D, 0.0D, 0.0D);
//                }
//            } else {
//                if (!worldIn.isRemote && flag && !material.isLiquid()) {
//                    worldIn.destroyBlock(pos, true);
//                }
//
//                worldIn.setBlockState(pos, liquid.getDefaultState(), 3);
//            }
//
//            return true;
//        }
//    }
//

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        return new LiquidEggFluidWrapper(stack);
    }
}
