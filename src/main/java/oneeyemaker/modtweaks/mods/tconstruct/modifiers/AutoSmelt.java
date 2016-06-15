package oneeyemaker.modtweaks.mods.tconstruct.modifiers;

import oneeyemaker.modtweaks.mods.tconstruct.actions.AddModifierAction;
import oneeyemaker.modtweaks.mods.tconstruct.actions.RemoveModifiersAction;

import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.modifiers.tools.ModAutoSmelt;

@ZenClass("mods.tconstruct.modifiers.AutoSmelt")
@ModOnly("TConstruct")
public class AutoSmelt
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks)
    {
        MineTweakerAPI.apply(new AddAutoSmeltModifierAction(effect, itemStacks));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveAutoSmeltModifiersAction());
    }
    private static class AddAutoSmeltModifierAction
        extends AddModifierAction<ModAutoSmelt>
    {
        public AddAutoSmeltModifierAction(int effect, IItemStack[] itemStacks)
        {
            super("AutoSmelt", effect, itemStacks, new int[itemStacks != null ? itemStacks.length : 0]);
        }
        public ModAutoSmelt getModifier(ItemStack[] itemStacks)
        {
            return new ModAutoSmelt(itemStacks, this.Effect, "Lava", "\u00a74",
                                    StatCollector.translateToLocal("modifier.tool.lava"));
        }
    }

    private static class RemoveAutoSmeltModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveAutoSmeltModifiersAction()
        {
            super("AutoSmelt");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModAutoSmelt;
        }
    }
}
