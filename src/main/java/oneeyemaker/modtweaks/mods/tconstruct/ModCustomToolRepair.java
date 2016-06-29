package oneeyemaker.modtweaks.mods.tconstruct;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.tools.AbilityHelper;
import tconstruct.modifiers.tools.ModToolRepair;

public class ModCustomToolRepair
    extends ModToolRepair
{
    public ModCustomToolRepair()
    {
        super();
    }
    @Override
    protected boolean canModify(ItemStack tool, ItemStack[] input)
    {
        NBTTagCompound tagCompound = tool.getTagCompound().getCompoundTag("InfiTool");
        if (tagCompound.getInteger("Damage") > 0)
        {
            int headID = tagCompound.getInteger("Head");
            boolean areInputsValid = true;
            for (ItemStack currentInput : input)
            {
                if (currentInput != null && headID != PatternBuilder.instance.getPartID(currentInput))
                {
                    areInputsValid = false;
                    break;
                }
            }
            if (areInputsValid)
            {
                return calculateIfNecessary(tool, input);
            }
        }
        return false;
    }
    private boolean calculateIfNecessary(ItemStack tool, ItemStack[] input)
    {
        NBTTagCompound tagCompound = tool.getTagCompound().getCompoundTag("InfiTool");
        int damage = tagCompound.getInteger("Damage");
        int numInputs = 0;
        int materialValue = 0;
        for (ItemStack currentInput : input)
        {
            if (currentInput != null)
            {
                materialValue += PatternBuilder.instance.getPartValue(currentInput);
                numInputs++;
            }
        }
        if (numInputs == 0)
        {
            return false;
        }
        int totalRepairValue = calculateIncrease(tool, materialValue, numInputs);
        float averageRepairValue = totalRepairValue / numInputs;
        return numInputs == 1 || (damage - totalRepairValue >= -averageRepairValue);
    }
    private int calculateIncrease(ItemStack tool, int materialValue, int itemsUsed)
    {
        NBTTagCompound tagCompound = tool.getTagCompound().getCompoundTag("InfiTool");
        int durability = TConstructRegistry.toolMaterials.get(tagCompound.getInteger("Head")).durability();
        int increase = durability * materialValue / 2;
        int repair = tagCompound.getInteger("RepairCount");
        float repairCount = (float) (100 - repair) / 100.0F;
        if (repairCount < 0.5F)
        {
            repairCount = 0.5F;
        }
        increase = (int) ((float) increase * repairCount);
        return increase;
    }
    @Override
    public void modify(ItemStack[] input, ItemStack tool)
    {
        NBTTagCompound tagCompound = tool.getTagCompound().getCompoundTag("InfiTool");
        tagCompound.setBoolean("Broken", false);
        int damage = tagCompound.getInteger("Damage");
        int itemsUsed = 0;
        int materialValue = 0;
        for (ItemStack currentInput : input)
        {
            if (currentInput != null)
            {
                materialValue += PatternBuilder.instance.getPartValue(currentInput);
                itemsUsed++;
            }
        }
        int increase = calculateIncrease(tool, materialValue, itemsUsed);
        int repair = tagCompound.getInteger("RepairCount");
        repair += itemsUsed;
        tagCompound.setInteger("RepairCount", repair);
        damage -= increase;
        if (damage < 0)
        {
            damage = 0;
        }
        tagCompound.setInteger("Damage", damage);
        AbilityHelper.damageTool(tool, 0, null, true);
    }
}
