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
import tconstruct.modifiers.tools.ModPiston;

@ZenClass("mods.tconstruct.modifiers.Knockback")
@ModOnly("TConstruct")
public class Knockback
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int[] values)
    {
        MineTweakerAPI.apply(new AddKnockbackModifierAction(effect, itemStacks, values));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveKnockbackModifiersAction());
    }
    private static class AddKnockbackModifierAction
        extends AddModifierAction<ModPiston>
    {
        public AddKnockbackModifierAction(int effect, IItemStack[] itemStacks, int[] values)
        {
            super("Knockback", effect, itemStacks, values);
        }
        public ModPiston getModifier(ItemStack[] itemStacks)
        {
            return new ModPiston(this.Effect, itemStacks, this.Values);
        }
    }

    private static class RemoveKnockbackModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveKnockbackModifiersAction()
        {
            super("Knockback");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModPiston;
        }
    }
}
