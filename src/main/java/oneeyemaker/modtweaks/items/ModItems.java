package oneeyemaker.modtweaks.items;

import oneeyemaker.modtweaks.items.baubles.ItemBelt;

import cpw.mods.fml.common.Loader;

public class ModItems
{
    public static ItemBelt ItemBelt;
    public static void initialize()
    {
        if (Loader.isModLoaded("Baubles"))
        {
            ModItems.ItemBelt = new ItemBelt();
        }
    }
}
