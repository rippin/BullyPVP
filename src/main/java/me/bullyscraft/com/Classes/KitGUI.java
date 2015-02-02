package me.bullyscraft.com.Classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitGUI {

private static List<Inventory> invs = new ArrayList<Inventory>();

    public static void createGuiInventory(int size, List<Kit> kits){
        invs.clear();
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.AQUA + "Kit Selector 1");
        ItemStack forward = new ItemStack(Material.REDSTONE_TORCH_ON);
        ItemMeta meta = forward.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Click to go to next page.");
        forward.setItemMeta(meta);

        ItemStack back = new ItemStack(Material.REDSTONE_TORCH_OFF);
        ItemMeta backMeta =  back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Click to go to back.");
        back.setItemMeta(backMeta);

        invs.add(inv);
        int invIndex = 0;
        int placeHolder = 0;
        for (int i = 0; i < size; i++){
            if (size % 36 == 0){
                invs.get(invIndex).setItem(40, forward);
                ++invIndex;
                invs.add(Bukkit.createInventory(null, 45, ChatColor.AQUA + "Kit Selector " + invIndex));
                invs.get(invIndex).setItem(39, back);
                placeHolder = 0;
            }
            invs.get(invIndex).setItem(placeHolder, kits.get(i).getKitIcon());

            ++placeHolder;
        }
    }

    public static List<Inventory> getGuiList(){
        return invs;
    }

}
