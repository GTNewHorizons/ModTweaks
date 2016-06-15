package oneeyemaker.modtweaks;

import oneeyemaker.modtweaks.baubles.ModBaubles;
import oneeyemaker.modtweaks.mods.tconstruct.modifiers.*;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import minetweaker.MineTweakerAPI;

@Mod(modid = ModTweaks.MOD_ID,
     name = ModTweaks.MOD_NAME,
     version = ModTweaks.VERSION,
     dependencies = "required-after:MineTweaker3;required-after:TConstruct;after:Baubles")
public class ModTweaks
{
    public static final String MOD_ID = "ModTweaks";
    public static final String MOD_NAME = "ModTweaks";
    public static final String VERSION = "@VERSION@";
    @EventHandler
    public void preInitialize(FMLPreInitializationEvent preInitializationEvent)
    {
        if (Loader.isModLoaded("Baubles"))
        {
            ModBaubles.initialize();
        }
    }
    @EventHandler
    public void initialize(FMLInitializationEvent initializationEvent)
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
        MineTweakerAPI.registerClass(Reinforced.class);
        MineTweakerAPI.registerClass(Sharpness.class);
        MineTweakerAPI.registerClass(SilkTouch.class);
        MineTweakerAPI.registerClass(Smite.class);
        MineTweakerAPI.registerClass(Windup.class);
    }
}
