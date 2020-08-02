package studio.trc.bukkit.litesignin.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import studio.trc.bukkit.litesignin.config.ConfigurationUtil;
import studio.trc.bukkit.litesignin.config.ConfigurationType;

public class CustomItem
{
    private final ItemStack is;
    private final String name;
    
    public CustomItem(ItemStack is, String name) {
        this.is = is;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public ItemStack getItemStack() {
        return is;
    }
    
    public void delete() {
        ConfigurationUtil.getConfig(ConfigurationType.CUSTOMITEMS).set("Item-Collection." + name, null);
        ConfigurationUtil.saveConfig(ConfigurationType.CUSTOMITEMS);
    }
    
    public void give(Player player) {
        player.getInventory().addItem(is);
    }
    
    @Override
    public String toString() {
        return "[CustomItem] -> [" + is.toString() + "], [Name:" + name + "]";
    }
    
    public static List<CustomItem> getItemStackCollection() {
        List<CustomItem> itemList = new ArrayList();
        for (String name : ConfigurationUtil.getConfig(ConfigurationType.CUSTOMITEMS).getConfigurationSection("Item-Collection").getKeys(false)) {
            ItemStack itemStack = ConfigurationUtil.getConfig(ConfigurationType.CUSTOMITEMS).getItemStack("Item-Collection." + name);
            if (itemStack != null) {
                itemList.add(new CustomItem(itemStack, name));
            }
        }
        return itemList;
    }
    
    public static CustomItem getCustomItem(String name) {
        ItemStack is = ConfigurationUtil.getConfig(ConfigurationType.CUSTOMITEMS).getItemStack("Item-Collection." + name);
        if (is == null) {
            return null;
        }
        return new CustomItem(is, name);
    }
    
    public static boolean addItemAsCollection(ItemStack is, String name) {
        for (CustomItem ci : getItemStackCollection()) {
            if (ci.getName().equals(name)) {
                return false;
            }
        }
        ConfigurationUtil.getConfig(ConfigurationType.CUSTOMITEMS).set("Item-Collection." + name, is);
        ConfigurationUtil.saveConfig(ConfigurationType.CUSTOMITEMS);
        return true;
    }
    
    public static boolean deleteItemAsCollection(String name) {
        for (CustomItem ci : getItemStackCollection()) {
            if (ci.getName().equals(name)) {
                ci.delete();
                return true;
            }
        }
        return false;
    }
}
