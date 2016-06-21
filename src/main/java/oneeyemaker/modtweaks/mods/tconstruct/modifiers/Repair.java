package oneeyemaker.modtweaks.mods.tconstruct.modifiers;

import java.util.ArrayList;
import java.util.Iterator;

import oneeyemaker.modtweaks.mods.tconstruct.ModCustomToolRepair;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.modifiers.tools.ModToolRepair;

@ZenClass("mods.tconstruct.modifiers.Repair")
@ModOnly("TConstruct")
public class Repair
{
    @ZenMethod
    public static void replaceModifier()
    {
        MineTweakerAPI.apply(new ReplaceRepairModifierAction());
    }
    private static class ReplaceRepairModifierAction
        implements IUndoableAction
    {
        private ItemModifier AddedModifier;
        private ArrayList<ItemModifier> RemovedModifiers;
        public ReplaceRepairModifierAction()
        {
            this.AddedModifier = new ModCustomToolRepair();
            this.RemovedModifiers = new ArrayList<>();
        }
        @Override
        public void apply()
        {
            Iterator<ItemModifier> itemModifierIterator = ModifyBuilder.instance.itemModifiers.iterator();
            while (itemModifierIterator.hasNext())
            {
                ItemModifier itemModifier = itemModifierIterator.next();
                if ((itemModifier instanceof ModToolRepair) && !(itemModifier instanceof ModCustomToolRepair))
                {
                    this.RemovedModifiers.add(itemModifier);
                    itemModifierIterator.remove();
                }
            }
            ModifyBuilder.registerModifier(this.AddedModifier);
        }
        @Override
        public boolean canUndo()
        {
            return true;
        }
        @Override
        public void undo()
        {
            Iterator<ItemModifier> itemModifierIterator = ModifyBuilder.instance.itemModifiers.iterator();
            while (itemModifierIterator.hasNext())
            {
                ItemModifier itemModifier = itemModifierIterator.next();
                if (itemModifier instanceof ModCustomToolRepair)
                {
                    itemModifierIterator.remove();
                }
            }
            Iterator<ItemModifier> removedModifiersIterator = this.RemovedModifiers.iterator();
            while (removedModifiersIterator.hasNext())
            {
                ModifyBuilder.registerModifier(removedModifiersIterator.next());
                removedModifiersIterator.remove();
            }
        }
        @Override
        public String describe()
        {
            return "Replacing Repair Modifier for TConstruct...";
        }
        @Override
        public String describeUndo()
        {
            return "Restoring default TConstruct Repair modifier...";
        }
        @Override
        public Object getOverrideKey()
        {
            return null;
        }
    }
}
