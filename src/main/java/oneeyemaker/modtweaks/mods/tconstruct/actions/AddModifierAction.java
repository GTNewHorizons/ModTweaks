package oneeyemaker.modtweaks.mods.tconstruct.actions;

import java.util.Iterator;

import minetweaker.IUndoableAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.modifier.ItemModifier;

public abstract class AddModifierAction<T extends ItemModifier>
    implements IUndoableAction
{
    protected int Effect;
    protected int[] Values;
    protected IItemStack[] ItemStacks;
    protected T Modifier;
    private String Name;
    public AddModifierAction(String name, int effect, IItemStack[] itemStacks, int[] values)
    {
        this.Name = name;
        this.Effect = (effect < 0 ? 0 : effect);
        this.ItemStacks = itemStacks;
        this.Values = values;
    }
    public abstract T getModifier(ItemStack[] itemStacks);
    public void apply()
    {
        if ((this.ItemStacks != null) && (this.Values != null) && (this.ItemStacks.length == this.Values.length))
        {
            ItemStack[] itemStacks = MineTweakerMC.getItemStacks(this.ItemStacks);
            this.Modifier = getModifier(itemStacks);
            ModifyBuilder.registerModifier(this.Modifier);
        }
    }
    public boolean canUndo()
    {
        return (this.ItemStacks != null) && (this.Values != null) && (this.ItemStacks.length == this.Values.length);
    }
    public void undo()
    {
        if (this.Modifier != null)
        {
            Iterator<ItemModifier> itemModifierIterator = ModifyBuilder.instance.itemModifiers.iterator();
            while (itemModifierIterator.hasNext())
            {
                ItemModifier itemModifier = itemModifierIterator.next();
                if (itemModifier.equals(this.Modifier))
                {
                    itemModifierIterator.remove();
                    this.Modifier = null;
                    break;
                }
            }
        }
    }
    public String describe()
    {
        return "Adding new " + this.Name + " tool modifier...";
    }
    public String describeUndo()
    {
        return "Removing new " + this.Name + " tool modifier...";
    }
    public Object getOverrideKey()
    {
        return null;
    }
}
