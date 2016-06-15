package oneeyemaker.modtweaks.mods.tconstruct.modifiers;

import oneeyemaker.modtweaks.mods.tconstruct.actions.AddModifierAction;
import oneeyemaker.modtweaks.mods.tconstruct.actions.RemoveModifiersAction;

import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.modifiers.tools.ModWindup;

@ZenClass("mods.tconstruct.modifiers.Windup")
@ModOnly({"TConstruct"})
public class Windup
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int[] values)
    {
        MineTweakerAPI.apply(new AddWindupModifierAction(effect, itemStacks, values));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveWindupModifiersAction());
    }
    private static class AddWindupModifierAction
        extends AddModifierAction<ModWindup>
    {
        public AddWindupModifierAction(int effect, IItemStack[] itemStacks, int[] values)
        {
            super("Windup", effect, itemStacks, values);
        }
        public ModWindup getModifier(ItemStack[] itemStacks)
        {
            return new ModWindup(this.Effect, itemStacks, this.Values);
        }
    }

    private static class RemoveWindupModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveWindupModifiersAction()
        {
            super("Windup");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModWindup;
        }
    }
}
