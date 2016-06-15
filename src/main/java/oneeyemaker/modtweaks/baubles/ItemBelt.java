package oneeyemaker.modtweaks.baubles;

import java.util.ArrayList;
import java.util.List;

import oneeyemaker.modtweaks.ModTweaks;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

@InterfaceList(value = {
    @Interface(iface = "baubles.api.IBauble", modid = "Baubles")
})
public class ItemBelt
    extends Item
    implements IBauble
{
    private IIcon Icon;
    public ItemBelt()
    {
        super();
        this.setHasSubtypes(false);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
        GameRegistry.registerItem(this, ModTweaks.MOD_ID + ":ItemBelt", ModTweaks.MOD_ID);
    }
    @Override
    public String getUnlocalizedName()
    {
        return ModTweaks.MOD_ID + ":ItemBelt";
    }
    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return ModTweaks.MOD_ID + ":ItemBelt";
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.Icon = iconRegister.registerIcon(ModTweaks.MOD_ID + ":ItemBelt");
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
        return this.Icon;
    }
    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean flag)
    {
        if (itemStack.getTagCompound() == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        int fireImmune = Math.max(Math.min(tagCompound.getInteger("FireImmune"), 2), 0);
        int witherImmune = Math.max(Math.min(tagCompound.getInteger("WitherImmune"), 1), 0);
        int regeneration = Math.max(Math.min(tagCompound.getInteger("Regeneration"), 4), 0);
        ArrayList<String> tooltips = new ArrayList<>();
        ArrayList<String> shiftTooltips = new ArrayList<>();
        if (fireImmune == 0)
        {
            shiftTooltips.add(EnumChatFormatting.AQUA + this.getLocalizedTooltip("FireImmuneI"));
        }
        else if (fireImmune == 1)
        {
            tooltips.add(EnumChatFormatting.GREEN + this.getLocalizedTooltip("FireImmuneI"));
            shiftTooltips.add(EnumChatFormatting.AQUA + this.getLocalizedTooltip("FireImmuneII"));
        }
        else
        {
            tooltips.add(EnumChatFormatting.GREEN + this.getLocalizedTooltip("FireImmuneII"));
        }
        if (witherImmune == 0)
        {
            shiftTooltips.add(EnumChatFormatting.AQUA + this.getLocalizedTooltip("WitherImmuneI"));
        }
        else
        {
            tooltips.add(EnumChatFormatting.GREEN + this.getLocalizedTooltip("WitherImmuneI"));
        }
        if (regeneration == 0)
        {
            shiftTooltips.add(EnumChatFormatting.AQUA + this.getLocalizedTooltip("RegenerationI"));
        }
        else if (regeneration == 1)
        {
            tooltips.add(EnumChatFormatting.GREEN + this.getLocalizedTooltip("RegenerationI"));
            shiftTooltips.add(EnumChatFormatting.AQUA + this.getLocalizedTooltip("RegenerationII"));
        }
        else if (regeneration == 2)
        {
            tooltips.add(EnumChatFormatting.GREEN + this.getLocalizedTooltip("RegenerationII"));
            shiftTooltips.add(EnumChatFormatting.AQUA + this.getLocalizedTooltip("RegenerationIII"));
        }
        else if (regeneration == 3)
        {
            tooltips.add(EnumChatFormatting.GREEN + this.getLocalizedTooltip("RegenerationIII"));
            shiftTooltips.add(EnumChatFormatting.AQUA + this.getLocalizedTooltip("RegenerationIV"));
        }
        else
        {
            tooltips.add(EnumChatFormatting.GREEN + this.getLocalizedTooltip("RegenerationIV"));
        }
        list.addAll(tooltips);
        if (!shiftTooltips.isEmpty())
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            {
                list.add(EnumChatFormatting.ITALIC + this.getLocalizedTooltip("AvailableUpgrades"));
                list.addAll(shiftTooltips);
            }
            else
            {
                list.add(EnumChatFormatting.ITALIC + this.getLocalizedTooltip("ShiftTooltip"));
            }
        }
    }
    @Override
    @Method(modid = "Baubles")
    public BaubleType getBaubleType(ItemStack itemStack)
    {
        return BaubleType.BELT;
    }
    @Override
    @Method(modid = "Baubles")
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase)
    {
        if (!(entityLivingBase instanceof EntityPlayer))
        {
            return;
        }
        if (!itemStack.hasTagCompound())
        {
            return;
        }
        EntityPlayer entityPlayer = (EntityPlayer) entityLivingBase;
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        int fireImmune = tagCompound.getInteger("FireImmune");
        int witherImmune = tagCompound.getInteger("WitherImmune");
        int regeneration = tagCompound.getInteger("Regeneration");
        int regenerationCooldown = tagCompound.getInteger("RegenerationCooldown");
        if (fireImmune >= 3)
        {
            fireImmune = 2;
            itemStack.getTagCompound().setInteger("FireImmune", fireImmune);
        }
        if (witherImmune >= 2)
        {
            witherImmune = 1;
            itemStack.getTagCompound().setInteger("WitherImmune", witherImmune);
        }
        if (regeneration >= 5)
        {
            regeneration = 4;
            itemStack.getTagCompound().setInteger("Regeneration", regeneration);
        }
        if (fireImmune == 1)
        {
            if (entityPlayer.isBurning())
            {
                entityPlayer.extinguish();
            }
        }
        else if (fireImmune == 2)
        {
            this.setImmuneToFire(entityPlayer, true);
        }
        if (witherImmune == 1)
        {
            if (entityPlayer.getActivePotionEffect(Potion.wither) != null)
            {
                entityPlayer.removePotionEffect(Potion.wither.id);
            }
        }
        if (regeneration > 0)
        {
            if (regenerationCooldown > 0)
            {
                itemStack.getTagCompound().setInteger("RegenerationCooldown", regenerationCooldown - 1);
            }
            else
            {
                if (entityPlayer.getHealth() < entityPlayer.getMaxHealth())
                {
                    //entityPlayer.heal(
                    //Math.min(entityPlayer.getMaxHealth() - entityPlayer.getHealth(), (float) regeneration));
                    entityPlayer.heal((float) regeneration);
                    itemStack.getTagCompound()
                             .setInteger("RegenerationCooldown", 320 / (int) Math.pow(2, regeneration));
                }
            }
        }
    }
    @Override
    @Method(modid = "Baubles")
    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase)
    {
    }
    @Override
    @Method(modid = "Baubles")
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase)
    {
        this.setImmuneToFire(entityLivingBase, false);
    }
    @Override
    @Method(modid = "Baubles")
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase)
    {
        return true;
    }
    @Override
    @Method(modid = "Baubles")
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase)
    {
        return true;
    }
    private void setImmuneToFire(Entity entity, boolean value)
    {
        ReflectionHelper.setPrivateValue(Entity.class, entity, value, "isImmuneToFire", "field_70178_ae", "ag");
    }
    public String getLocalizedTooltip(String tooltip)
    {
        return StatCollector.translateToLocal(ModTweaks.MOD_ID + ":ItemBelt." + tooltip + ".tooltip");
    }
}
