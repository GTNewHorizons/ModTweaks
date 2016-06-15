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
import tconstruct.modifiers.tools.ModReinforced;

@ZenClass("mods.tconstruct.modifiers.Reinforced")
@ModOnly("TConstruct")
public class Reinforced
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int increase)
    {
        MineTweakerAPI.apply(new AddReinforcedModifierAction(effect, itemStacks, increase));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveReinforcedModifiersAction());
    }
    private static class AddReinforcedModifierAction
        extends AddModifierAction<ModReinforced>
    {
        private int Increase;
        public AddReinforcedModifierAction(int effect, IItemStack[] itemStacks, int increase)
        {
            super("Reinforced", effect, itemStacks, new int[itemStacks != null ? itemStacks.length : 0]);
            this.Increase = increase < 0 ? 0 : increase;
        }
        public ModReinforced getModifier(ItemStack[] itemStacks)
        {
            return new ModReinforced(itemStacks, this.Effect, this.Increase);
        }
    }

    private static class RemoveReinforcedModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveReinforcedModifiersAction()
        {
            super("Reinforced");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModReinforced;
        }
    }
}
