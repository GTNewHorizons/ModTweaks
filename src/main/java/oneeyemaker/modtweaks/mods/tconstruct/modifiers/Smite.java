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
import tconstruct.modifiers.tools.ModSmite;

@ZenClass("mods.tconstruct.modifiers.Smite")
@ModOnly("TConstruct")
public class Smite
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int[] values)
    {
        MineTweakerAPI.apply(new AddSmiteModifierAction(effect, itemStacks, values));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveSmiteModifiersAction());
    }
    private static class AddSmiteModifierAction
        extends AddModifierAction<ModSmite>
    {
        public AddSmiteModifierAction(int effect, IItemStack[] itemStacks, int[] values)
        {
            super("Smite", effect, itemStacks, values);
        }
        public ModSmite getModifier(ItemStack[] itemStacks)
        {
            return new ModSmite("Smite", this.Effect, itemStacks, this.Values);
        }
    }

    private static class RemoveSmiteModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveSmiteModifiersAction()
        {
            super("Smite");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModSmite;
        }
    }
}
