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
import tconstruct.modifiers.tools.ModDurability;

@ZenClass("mods.tconstruct.modifiers.Durability")
@ModOnly("TConstruct")
public class Durability
{
    @ZenMethod
    public static void addModifier(int effect, IItemStack[] itemStacks, int durability, float modifier, int miningLevel,
                                   String key, String tooltip, String color)
    {
        MineTweakerAPI.apply(
            new AddDurabilityModifierAction(effect, itemStacks, durability, modifier, miningLevel, key, tooltip,
                                            color));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveDurabilityModifiersAction());
    }
    private static class AddDurabilityModifierAction
        extends AddModifierAction<ModDurability>
    {
        private int Durability;
        private float Modifier;
        private int MiningLevel;
        private String Key;
        private String Tooltip;
        private String Color;
        public AddDurabilityModifierAction(int effect, IItemStack[] itemStacks, int durability, float modifier,
                                           int miningLevel, String key, String tooltip, String color)
        {
            super("Durability", effect, itemStacks, new int[itemStacks != null ? itemStacks.length : 0]);
            this.Durability = durability;
            this.Modifier = modifier;
            this.MiningLevel = miningLevel;
            this.Key = key;
            this.Tooltip = (color != null && tooltip != null) ? color + tooltip : tooltip;
            this.Color = color;
        }
        public ModDurability getModifier(ItemStack[] itemStacks)
        {
            return new ModDurability(itemStacks, this.Effect, this.Durability, this.Modifier, this.MiningLevel,
                                     this.Key, this.Tooltip, this.Color);
        }
    }

    private static class RemoveDurabilityModifiersAction
        extends RemoveModifiersAction
    {
        public RemoveDurabilityModifiersAction()
        {
            super("Durability");
        }
        public boolean canRemove(ItemModifier itemModifier)
        {
            return itemModifier instanceof ModDurability;
        }
    }
}
