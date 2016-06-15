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
import tconstruct.modifiers.tools.ModExtraModifier;

@ZenClass("mods.tconstruct.modifiers.Extra")
@ModOnly("TConstruct")
public class Extra
{
    @ZenMethod
    public static void addModifier(IItemStack[] itemStacks, int tier)
    {
        MineTweakerAPI.apply(new AddExtraModifierAction(itemStacks, tier));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveExtraModifiersAction());
    }
    private static class AddExtraModifierAction
        extends AddModifierAction<ModExtraModifier>
    {
        private int Tier;
        public AddExtraModifierAction(IItemStack[] itemStacks, int tier)
        {
            super("Extra", 0, itemStacks, new int[itemStacks != null ? itemStacks.length : 0]);
            this.Tier = tier;
        }
        public ModExtraModifier getModifier(ItemStack[] itemStacks)
        {
            return new ModExtraModifier(itemStacks, "Tier" + this.Tier + "Free");
        }
    }

    private static class RemoveExtraModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveExtraModifiersAction()
        {
            super("Extra");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModExtraModifier;
        }
    }
}
