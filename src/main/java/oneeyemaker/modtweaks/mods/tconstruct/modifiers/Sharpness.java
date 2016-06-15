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
import tconstruct.modifiers.tools.ModAttack;

@ZenClass("mods.tconstruct.modifiers.Sharpness")
@ModOnly("TConstruct")
public class Sharpness
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int[] values, @Optional boolean ammoOnly)
    {
        MineTweakerAPI.apply(new AddSharpnessModifierAction(effect, itemStacks, values, ammoOnly));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveSharpnessModifiersAction());
    }
    private static class AddSharpnessModifierAction
        extends AddModifierAction<ModAttack>
    {
        private boolean AmmoOnly;
        public AddSharpnessModifierAction(int effect, IItemStack[] itemStacks, int[] values, boolean ammoOnly)
        {
            super("Sharpness", effect, itemStacks, values);
            this.AmmoOnly = ammoOnly;
        }
        public ModAttack getModifier(ItemStack[] itemStacks)
        {
            return this.AmmoOnly ?
                   new ModAttack("Quartz", this.Effect, itemStacks, this.Values, true) :
                   new ModAttack("Quartz", this.Effect, itemStacks, this.Values);
        }
    }

    private static class RemoveSharpnessModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveSharpnessModifiersAction()
        {
            super("Sharpness");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModAttack;
        }
    }
}
