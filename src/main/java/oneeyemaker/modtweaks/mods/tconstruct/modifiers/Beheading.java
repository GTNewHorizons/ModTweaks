package oneeyemaker.modtweaks.mods.tconstruct.modifiers;

import oneeyemaker.modtweaks.mods.tconstruct.actions.AddModifierAction;
import oneeyemaker.modtweaks.mods.tconstruct.actions.RemoveModifiersAction;

import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.modifiers.tools.ModInteger;

@ZenClass("mods.tconstruct.modifiers.Beheading")
@ModOnly("TConstruct")
public class Beheading
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, @Optional int multiplier)
    {
        MineTweakerAPI.apply(new AddBeheadingModifierAction(effect, itemStacks, multiplier));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveBeheadingModifiersAction());
    }
    private static class AddBeheadingModifierAction
        extends AddModifierAction<ModInteger>
    {
        private int Multiplier;
        public AddBeheadingModifierAction(int effect, IItemStack[] itemStacks, int multiplier)
        {
            super("Beheading", effect, itemStacks, new int[itemStacks != null ? itemStacks.length : 0]);
            this.Multiplier = multiplier > 10 ? 10 : (multiplier < 1 ? 1 : multiplier);
        }
        public ModInteger getModifier(ItemStack[] itemStacks)
        {
            return new ModInteger(itemStacks, this.Effect, "Beheading", this.Multiplier, "\u00a7d", "Beheading");
        }
    }

    private static class RemoveBeheadingModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveBeheadingModifiersAction()
        {
            super("Beheading");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return (itemModifier instanceof ModInteger) && itemModifier.key.equals("Beheading");
        }
    }
}
