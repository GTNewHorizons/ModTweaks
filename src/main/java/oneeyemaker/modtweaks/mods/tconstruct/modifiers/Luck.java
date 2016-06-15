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
import tconstruct.modifiers.tools.ModLapis;

@ZenClass("mods.tconstruct.modifiers.Luck")
@ModOnly({"TConstruct"})
public class Luck
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int[] values)
    {
        MineTweakerAPI.apply(new AddLuckModifierAction(effect, itemStacks, values));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveLuckModifiersAction());
    }
    private static class AddLuckModifierAction
        extends AddModifierAction<ModLapis>
    {
        public AddLuckModifierAction(int effect, IItemStack[] itemStacks, int[] values)
        {
            super("Luck", effect, itemStacks, values);
        }
        public ModLapis getModifier(ItemStack[] itemStacks)
        {
            return new ModLapis(this.Effect, itemStacks, this.Values);
        }
    }

    private static class RemoveLuckModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveLuckModifiersAction()
        {
            super("Luck");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModLapis;
        }
    }
}
