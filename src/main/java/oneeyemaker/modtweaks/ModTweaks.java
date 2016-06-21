package oneeyemaker.modtweaks;

import oneeyemaker.modtweaks.items.ModItems;
import oneeyemaker.modtweaks.mods.tconstruct.modifiers.*;
import oneeyemaker.modtweaks.world.MobSpawnConfiguration;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import minetweaker.MineTweakerAPI;

@Mod(modid = ModTweaks.MOD_ID,
     name = ModTweaks.MOD_NAME,
     version = ModTweaks.VERSION,
     dependencies = "required-after:MineTweaker3;after:Baubles")
public class ModTweaks
{
    public static final String MOD_ID = "ModTweaks";
    public static final String MOD_NAME = "ModTweaks";
    public static final String VERSION = "@VERSION@";
    public static MobSpawnConfiguration MobSpawnConfiguration;
    @EventHandler
    public void preInitialize(FMLPreInitializationEvent preInitializationEvent)
    {
        ModTweaks.MobSpawnConfiguration =
            new MobSpawnConfiguration(preInitializationEvent.getModConfigurationDirectory());
        ModTweaks.MobSpawnConfiguration.initialize();
        ModItems.initialize();
    }
    @EventHandler
    public void initialize(FMLInitializationEvent initializationEvent)
    {
        if (Loader.isModLoaded("TConstruct"))
        {
            MineTweakerAPI.registerClass(AutoRepair.class);
            MineTweakerAPI.registerClass(AutoSmelt.class);
            MineTweakerAPI.registerClass(BaneOfArthropods.class);
            MineTweakerAPI.registerClass(Beheading.class);
            MineTweakerAPI.registerClass(Creative.class);
            MineTweakerAPI.registerClass(Durability.class);
            MineTweakerAPI.registerClass(Extra.class);
            MineTweakerAPI.registerClass(Flame.class);
            MineTweakerAPI.registerClass(Flux.class);
            MineTweakerAPI.registerClass(Haste.class);
            MineTweakerAPI.registerClass(Knockback.class);
            MineTweakerAPI.registerClass(Luck.class);
            MineTweakerAPI.registerClass(Necrotic.class);
            MineTweakerAPI.registerClass(Repair.class);
            MineTweakerAPI.registerClass(Reinforced.class);
            MineTweakerAPI.registerClass(Sharpness.class);
            MineTweakerAPI.registerClass(SilkTouch.class);
            MineTweakerAPI.registerClass(Smite.class);
            MineTweakerAPI.registerClass(Windup.class);
        }
    }
    @EventHandler
    public void postInitialize(FMLPostInitializationEvent postInitializationEvent)
    {
        ModTweaks.MobSpawnConfiguration.postInitialize();
    }
}
