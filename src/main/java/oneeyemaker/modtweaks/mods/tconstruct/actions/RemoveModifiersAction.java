package oneeyemaker.modtweaks.mods.tconstruct.actions;

import java.util.ArrayList;
import java.util.Iterator;

import minetweaker.IUndoableAction;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.modifier.ItemModifier;

public abstract class RemoveModifiersAction
    implements IUndoableAction
{
    private String Name;
    private ArrayList<ItemModifier> RemovedModifiers;
    public RemoveModifiersAction(String name)
    {
        this.Name = name;
        this.RemovedModifiers = new ArrayList<>();
    }
    public abstract boolean canRemove(ItemModifier itemModifier);
    public void apply()
    {
        Iterator<ItemModifier> itemModifierIterator = ModifyBuilder.instance.itemModifiers.iterator();
        while (itemModifierIterator.hasNext())
        {
            ItemModifier itemModifier = itemModifierIterator.next();
            if (canRemove(itemModifier))
            {
                this.RemovedModifiers.add(itemModifier);
                itemModifierIterator.remove();
            }
        }
    }
    public boolean canUndo()
    {
        return true;
    }
    public void undo()
    {
        Iterator<ItemModifier> itemModifierIterator = this.RemovedModifiers.iterator();
        while (itemModifierIterator.hasNext())
        {
            ModifyBuilder.registerModifier(itemModifierIterator.next());
            itemModifierIterator.remove();
        }
    }
    public String describe()
    {
        return "Removing " + this.Name + " modifiers...";
    }
    public String describeUndo()
    {
        return "Re-adding " + this.Name + " modifiers...";
    }
    public Object getOverrideKey()
    {
        return null;
    }
}
