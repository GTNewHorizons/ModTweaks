package oneeyemaker.modtweaks.mods.tconstruct.modifiers;

import oneeyemaker.modtweaks.mods.tconstruct.actions.AddModifierAction;
import oneeyemaker.modtweaks.mods.tconstruct.actions.RemoveModifiersAction;

import cpw.mods.fml.common.Loader;
import iguanaman.iguanatweakstconstruct.leveling.modifiers.ModXpAwareRedstone;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.modifiers.tools.ModRedstone;
import tconstruct.modifiers.tools.ModWindup;

@ZenClass("mods.tconstruct.modifiers.Haste")
@ModOnly("TConstruct")
public class Haste
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int[] values)
    {
        MineTweakerAPI.apply(new AddHasteModifierAction(effect, itemStacks, values));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveHasteModifiersAction());
    }
    private static class AddHasteModifierAction
        extends AddModifierAction<ModRedstone>
    {
        private boolean IsIguanaTinkerTweaksLoaded;
        public AddHasteModifierAction(int effect, IItemStack[] itemStacks, int[] values)
        {
            super("Haste", effect, itemStacks, values);
            this.IsIguanaTinkerTweaksLoaded = Loader.isModLoaded("IguanaTweaksTConstruct");
        }
        public ModRedstone getModifier(ItemStack[] itemStacks)
        {
            ModRedstone modRedstone = new ModRedstone(this.Effect, itemStacks, this.Values);
            return this.IsIguanaTinkerTweaksLoaded ? new ModXpAwareRedstone(modRedstone) : modRedstone;
        }
    }

    private static class RemoveHasteModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveHasteModifiersAction()
        {
            super("Haste");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return (itemModifier instanceof ModRedstone) && !(itemModifier instanceof ModWindup);
        }
    }
}
