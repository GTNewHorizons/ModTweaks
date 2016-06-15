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
import tconstruct.modifiers.tools.ModCreativeToolModifier;

@ZenClass("mods.tconstruct.modifiers.Creative")
@ModOnly({"TConstruct"})
public class Creative
{
    @ZenMethod
    public static void addModifier(IItemStack[] itemStacks)
    {
        MineTweakerAPI.apply(new AddCreativeModifierAction(itemStacks));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveCreativeModifiersAction());
    }
    private static class AddCreativeModifierAction
        extends AddModifierAction<ModCreativeToolModifier>
    {
        public AddCreativeModifierAction(IItemStack[] itemStacks)
        {
            super("Creative", 0, itemStacks, new int[itemStacks != null ? itemStacks.length : 0]);
        }
        public ModCreativeToolModifier getModifier(ItemStack[] itemStacks)
        {
            return new ModCreativeToolModifier(itemStacks);
        }
    }

    private static class RemoveCreativeModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveCreativeModifiersAction()
        {
            super("Creative");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModCreativeToolModifier;
        }
    }
}
