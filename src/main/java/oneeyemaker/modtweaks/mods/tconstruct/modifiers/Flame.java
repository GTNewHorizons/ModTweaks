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
import tconstruct.modifiers.tools.ModBlaze;

@ZenClass("mods.tconstruct.modifiers.Flame")
@ModOnly("TConstruct")
public class Flame
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int[] values)
    {
        MineTweakerAPI.apply(new AddFlameModifierAction(effect, itemStacks, values));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveFlameModifiersAction());
    }
    private static class AddFlameModifierAction
        extends AddModifierAction<ModBlaze>
    {
        public AddFlameModifierAction(int effect, IItemStack[] itemStacks, int[] values)
        {
            super("Flame", effect, itemStacks, values);
        }
        public ModBlaze getModifier(ItemStack[] itemStacks)
        {
            return new ModBlaze(this.Effect, itemStacks, this.Values);
        }
    }

    private static class RemoveFlameModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveFlameModifiersAction()
        {
            super("Flame");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModBlaze;
        }
    }
}
