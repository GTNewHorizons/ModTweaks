package oneeyemaker.modtweaks.mods.tconstruct.modifiers;

import java.util.ArrayList;
import java.util.Iterator;

import cofh.api.energy.IEnergyContainerItem;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tconstruct.tools.TinkerTools;

@ZenClass("mods.tconstruct.modifiers.Flux")
@ModOnly("TConstruct")
public class Flux
{
    @ZenMethod
    public static void addModifier(IItemStack[] itemStacks)
    {
        MineTweakerAPI.apply(new AddFluxModifierAction(itemStacks));
    }
    @ZenMethod
    public static void removeModifiers()
    {
        MineTweakerAPI.apply(new RemoveFluxModifiersAction());
    }
    private static class AddFluxModifierAction
        implements IUndoableAction
    {
        private IItemStack[] ItemStacks;
        private ArrayList<ItemStack> AddedModifiers;
        public AddFluxModifierAction(IItemStack[] itemStacks)
        {
            this.ItemStacks = itemStacks;
            this.AddedModifiers = new ArrayList<>();
        }
        public void apply()
        {
            if (this.ItemStacks != null)
            {
                ItemStack[] itemStacks = MineTweakerMC.getItemStacks(this.ItemStacks);
                for (ItemStack itemStack : itemStacks)
                {
                    if (itemStack.getItem() instanceof IEnergyContainerItem)
                    {
                        this.AddedModifiers.add(itemStack);
                        TinkerTools.modFlux.batteries.add(itemStack);
                    }
                    else
                    {
                        MineTweakerAPI.logWarning("Can't register Flux modifier with item: " + itemStack.toString() +
                                                  ". Item must be RF battery.");
                    }
                }
            }
        }
        public boolean canUndo()
        {
            return this.ItemStacks != null;
        }
        public void undo()
        {
            Iterator<ItemStack> itemStackIterator = TinkerTools.modFlux.batteries.iterator();
            while (itemStackIterator.hasNext())
            {
                ItemStack itemStack = itemStackIterator.next();
                for (ItemStack addedModifier : this.AddedModifiers)
                {
                    if (ItemStack.areItemStacksEqual(itemStack, addedModifier))
                    {
                        itemStackIterator.remove();
                    }
                }
            }
            this.AddedModifiers.clear();
        }
        public String describe()
        {
            return "Adding new Flux tool modifier...";
        }
        public String describeUndo()
        {
            return "Removing new Flux tool modifier...";
        }
        public Object getOverrideKey()
        {
            return null;
        }
    }

    private static class RemoveFluxModifiersAction
        implements IUndoableAction
    {
        private ArrayList<ItemStack> RemovedModifiers;
        public RemoveFluxModifiersAction()
        {
            this.RemovedModifiers = new ArrayList<>();
        }
        public void apply()
        {
            this.RemovedModifiers.addAll(TinkerTools.modFlux.batteries);
            TinkerTools.modFlux.batteries.clear();
        }
        public boolean canUndo()
        {
            return true;
        }
        public void undo()
        {
            Iterator<ItemStack> itemStackIterator = this.RemovedModifiers.iterator();
            while (itemStackIterator.hasNext())
            {
                TinkerTools.modFlux.batteries.add(itemStackIterator.next());
                itemStackIterator.remove();
            }
        }
        public String describe()
        {
            return "Removing Flux modifiers...";
        }
        public String describeUndo()
        {
            return "Re-adding Flux modifiers...";
        }
        public Object getOverrideKey()
        {
            return null;
        }
    }
}
