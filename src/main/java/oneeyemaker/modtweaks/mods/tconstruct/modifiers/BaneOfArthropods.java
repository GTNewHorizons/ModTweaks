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
import tconstruct.modifiers.tools.ModAntiSpider;

@ZenClass("mods.tconstruct.modifiers.BaneOfArthropods")
@ModOnly("TConstruct")
public class BaneOfArthropods
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int[] values)
    {
        MineTweakerAPI.apply(new AddBaneOfArthropodsModifierAction(effect, itemStacks, values));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveBaneOfArthropodsModifiersAction());
    }
    private static class AddBaneOfArthropodsModifierAction
        extends AddModifierAction<ModAntiSpider>
    {
        public AddBaneOfArthropodsModifierAction(int effect, IItemStack[] itemStacks, int[] values)
        {
            super("BaneOfArthropods", effect, itemStacks, values);
        }
        public ModAntiSpider getModifier(ItemStack[] itemStacks)
        {
            return new ModAntiSpider("ModAntiSpider", this.Effect, itemStacks, this.Values);
        }
    }

    private static class RemoveBaneOfArthropodsModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveBaneOfArthropodsModifiersAction()
        {
            super("BaneOfArthropods");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModAntiSpider;
        }
    }
}
