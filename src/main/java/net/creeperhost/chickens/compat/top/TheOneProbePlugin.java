package net.creeperhost.chickens.compat.top;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.entity.EntityChickensChicken;

import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TheOneProbePlugin implements IProbeInfoEntityProvider
{
	@Override
	public String getID()
    {
		return ChickensMod.MODID +":chickenstop";
	}

    @Override
	public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level world, Entity entity, IProbeHitEntityData data)
	{
		if(entity instanceof EntityChickensChicken chicken)
		{
			probeInfo.text(translate("entity.ChickensChicken.top.tier") +" "+ chicken.getTier());

	        if (chicken.getStatsAnalyzed())
			{
	        	probeInfo.text(translate("entity.ChickensChicken.top.growth") +" "+ chicken.getGrowth());
	        	probeInfo.text(translate("entity.ChickensChicken.top.gain") +" "+ chicken.getGain());
	        	probeInfo.text(translate("entity.ChickensChicken.top.strength") +" "+ chicken.getStrength());
			}

	        if (!chicken.isBaby())
			{
	            int layProgress = chicken.getLayProgress();
	            if (layProgress <= 0)
				{
	            	probeInfo.text(translate("entity.ChickensChicken.top.nextEggSoon"));
	            }
				else
				{
	            	probeInfo.text(translate("entity.ChickensChicken.top.layProgress") + " " + layProgress + translate("entity.ChickensChicken.top.layProgressEnd"));
	            }
	        }
		}
	}

	public static String translate(String text)
	{
		return I18n.get(text);
	}

	public static class GetTheOneProbe implements Function<ITheOneProbe, Void>
	{
		public static ITheOneProbe theOneProbe;
		@Nullable
		@Override
		public Void apply (ITheOneProbe input)
		{
			theOneProbe = input;
			theOneProbe.registerEntityProvider(new TheOneProbePlugin());
			return null;
		}
	}
}
