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
import tconstruct.modifiers.tools.ModButtertouch;

@ZenClass("mods.tconstruct.modifiers.SilkTouch")
@ModOnly("TConstruct")
public class SilkTouch
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks)
    {
        MineTweakerAPI.apply(new AddSilkTouchModifierAction(effect, itemStacks));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveSilkTouchModifiersAction());
    }
    private static class AddSilkTouchModifierAction
        extends AddModifierAction<ModButtertouch>
    {
        public AddSilkTouchModifierAction(int effect, IItemStack[] itemStacks)
        {
            super("SilkTouch", effect, itemStacks, new int[itemStacks != null ? itemStacks.length : 0]);
        }
        public ModButtertouch getModifier(ItemStack[] itemStacks)
        {
            return new ModButtertouch(itemStacks, this.Effect);
        }
    }

    private static class RemoveSilkTouchModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveSilkTouchModifiersAction()
        {
            super("SilkTouch");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModButtertouch;
        }
    }
}
