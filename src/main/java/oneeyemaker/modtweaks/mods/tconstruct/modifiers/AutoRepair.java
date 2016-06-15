package oneeyemaker.modtweaks.mods.tconstruct.modifiers;

import oneeyemaker.modtweaks.mods.tconstruct.actions.AddModifierAction;
import oneeyemaker.modtweaks.mods.tconstruct.actions.RemoveModifiersAction;

import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.modifiers.tools.ModInteger;

@ZenClass("mods.tconstruct.modifiers.AutoRepair")
@ModOnly("TConstruct")
public class AutoRepair
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, @Optional int multiplier)
    {
        MineTweakerAPI.apply(new AddAutoRepairModifierAction(effect, itemStacks, multiplier));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveAutoRepairModifiersAction());
    }
    private static class AddAutoRepairModifierAction
        extends AddModifierAction<ModInteger>
    {
        private int Multiplier;
        public AddAutoRepairModifierAction(int effect, IItemStack[] itemStacks, int multiplier)
        {
            super("AutoRepair", effect, itemStacks, new int[itemStacks != null ? itemStacks.length : 0]);
            this.Multiplier = multiplier > 10 ? 10 : (multiplier < 1 ? 1 : multiplier);
        }
        public ModInteger getModifier(ItemStack[] itemStacks)
        {
            return new ModInteger(itemStacks, this.Effect, "Moss", this.Multiplier * 3, "\u00a72",
                                  StatCollector.translateToLocal("modifier.tool.moss"));
        }
    }

    private static class RemoveAutoRepairModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveAutoRepairModifiersAction()
        {
            super("AutoRepair");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return (itemModifier instanceof ModInteger) && itemModifier.key.equals("Moss");
        }
    }
}
