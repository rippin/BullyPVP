package me.bullyscraft.com.Classes;


import me.bullyscraft.com.AbilityCountdowns.AbilityCountdown;
import me.bullyscraft.com.AbilityCountdowns.AbilityCountdownManager;
import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Config;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Kit {
    private String kitName;
    private LinkedHashMap<String, Integer> buffsandPrice = new LinkedHashMap<String, Integer>();
    private LinkedHashMap<String, String> buffsandDescription = new LinkedHashMap<String, String>();
    private List<String> items = new ArrayList<String>();
    private String armorType;
    private String weapon;
    private List<String> weaponEnchants = new ArrayList<String>();
    private List<String> weaponLore = new ArrayList<String>();
    private List<String> armorEnchants = new ArrayList<String>();
    private List<String> armorLore = new ArrayList<String>();
    private List<String> potions = new ArrayList<String>();
    private List<ItemStack> armorItems = new ArrayList<ItemStack>();
    private String kitType;
    private String kitNoPerms;
    private String kitDescription;
    private BullyPVP plugin;
    private String weaponName;

    public Kit(String kitName) {
        this.kitName = kitName;
        if (Config.getConfig().getString("Kits." + getName() + ".Weapon.Item") != null) {
        weapon = Config.getConfig().getString("Kits." + getName() + ".Weapon.Item").toUpperCase();
        }
        weaponName = Config.getConfig().getString("Kits." + getName() + ".Weapon.Name");
        if (Config.getConfig().getString("Kits." + getName() + ".Armor.Type") != null) {
        armorType = Config.getConfig().getString("Kits." + getName() + ".Armor.Type").toUpperCase();
        }
        if (Config.getConfig().getStringList("Kits." + getName() + ".Items") != null) {
        items = Config.getConfig().getStringList("Kits." + getName() + ".Items");
        }
        weaponEnchants = Config.getConfig().getStringList("Kits." + getName() + ".Weapon.Enchants");
        if (Config.getConfig().getStringList("Kits." + getName() + ".Weapon.Lore") != null){
        weaponLore = Config.getConfig().getStringList("Kits." + getName() + ".Weapon.Lore");
        }
        armorEnchants = Config.getConfig().getStringList("Kits." + getName() + ".Armor.Enchants");
        armorLore = Config.getConfig().getStringList("Kits." + getName() + ".Armor.Lore");
        kitType = Config.getConfig().getString("Kits." + getName() + ".Type");
        kitNoPerms = Config.getConfig().getString("Kits." + getName() + ".NoPermsMessage");
        kitDescription = Config.getConfig().getString("Kits." + getName() + ".Description");
        potions = Config.getConfig().getStringList("Kits." + getName() + ".Potions");
        if (KitManager.getArmor(kitName) != null) {
        armorItems = KitManager.getArmor(kitName);
        }
        plugin = BullyPVP.instance;
        loadBuffs();
    }


    private void loadBuffs() {
        for (String key : Config.getConfig().getConfigurationSection("Kits." + kitName + ".Buffs").getKeys(false)) {
            int price = Config.getConfig().getInt("Kits." + kitName + ".Buffs." + key + ".Price");
            String desc = Config.getConfig().getString("Kits." + kitName + ".Buffs." + key + ".Description");
            buffsandPrice.put(key, price);
            buffsandDescription.put(key, desc);
            System.out.print("Loaded " + key + " buff from file.");

        }

    }

    public LinkedHashMap<String, Integer> getBuffsandPrice() {

        return buffsandPrice;
    }

    public LinkedHashMap<String, String> getBuffsandDesc() {

        return buffsandDescription;
    }

    public List<String> getPotions(){
        return potions;
    }

    public String getArmorType() {
        return armorType;

    }
    public List<String> getItems(){
        return items;
    }

    public String getWeapon() {
        return weapon;

    }

    public String getName() {
        return kitName;
    }
    public void giveKit(final Player player){
        AbilityCountdownManager.removeAbilityCountdown(player, plugin);
        if (armorType != null) {
        ItemStack helm = new ItemStack(Material.getMaterial(armorType + "_HELMET"));
        ItemStack chest = new ItemStack(Material.getMaterial(armorType + "_CHESTPLATE"));
        ItemStack pants = new ItemStack(Material.getMaterial(armorType + "_LEGGINGS"));
        ItemStack boots = new ItemStack(Material.getMaterial(armorType + "_BOOTS"));
        List<ItemStack> armor = new ArrayList<ItemStack>();
        armor.add(boots);
        armor.add(pants);
        armor.add(chest);
        armor.add(helm);

            if (armorEnchants != null){
                for (String s : armorEnchants) {
                    if (s.contains(":")) {
                        String[] split = s.split(":");
                        Enchantment e = Enchantment.getByName(Buffs.getOfficialEnchantmentName(split[0].toUpperCase()));
                        for (ItemStack i : armor) {
                            i.addUnsafeEnchantment(e, Integer.parseInt(split[1]));

                        }
                    }
                }
            }
                if (armorLore != null){
                    for (ItemStack i : armor){
                        ItemMeta meta = i.getItemMeta();
                        armorLore = translateColorCodes(armorLore);

                        meta.setLore(armorLore);
                        i.setItemMeta(meta);
                    }
                }
            player.getInventory().setArmorContents(armor.toArray(new ItemStack[4]));
    }
    else if (armorItems != null){
    player.getInventory().setArmorContents(armorItems.toArray(new ItemStack[4]));
    }

        ItemStack weap = null;
        if (weapon != null) {
            weap = new ItemStack(Material.getMaterial(weapon));
        }

        if (potions != null){
            for (String s : potions) {
                if (s.contains(":")) {
                    final String[] split = s.split(":");
                    plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(Buffs.getOfficialPotionName(split[0])),
                                    Integer.parseInt(split[1]), Integer.parseInt(split[2]), true));
                        }
                    },1L);


                }
            }
        }

        if (weaponEnchants != null) {
            for (String s : weaponEnchants) {
                if (s.contains(":")) {
                    String[] split = s.split(":");
                    Enchantment e = Enchantment.getByName(Buffs.getOfficialEnchantmentName(split[0].toUpperCase()));
                    weap.addUnsafeEnchantment(e, Integer.parseInt(split[1]));

                    }
                }
            }

            if (weap != null && weaponLore != null){
                ItemMeta meta = weap.getItemMeta();
                weaponLore = translateColorCodes(weaponLore);
                meta.setLore(weaponLore);
                weap.setItemMeta(meta);
            }
        if (weaponName != null){
            ItemMeta meta = weap.getItemMeta();
            weaponName = ChatColor.translateAlternateColorCodes('&', weaponName);
            meta.setDisplayName(weaponName);
            weap.setItemMeta(meta);
        }

       player.getInventory().setItem(0, weap);
        if (items != null){
         for (ItemStack i : KitManager.parseItems(items)){
             player.getInventory().addItem(i);
         }

        }

        player.updateInventory();

        if (getName().equalsIgnoreCase("Freezer")){
            new AbilityCountdown(180, plugin, player).startFreezerCountdown();
        }
        else if (getName().equalsIgnoreCase("Assassin")){
            new AbilityCountdown(180, plugin, player).startAssassinCountdown();
        }
        else if (getName().equalsIgnoreCase("Pyro")){
            new AbilityCountdown(180, plugin, player).startPyroCountdown();
        }
        player.sendMessage(ChatColor.GREEN + "You have been given the " + getName() + " kit.");
        PlayerStatsObjectManager.getPSO(player, plugin).setKitClass(getName());

    }

    public void giveKit1v1(final Player player){
        AbilityCountdownManager.removeAbilityCountdown(player, plugin);
        if (armorType != null) {
            ItemStack helm = new ItemStack(Material.getMaterial(armorType + "_HELMET"));
            ItemStack chest = new ItemStack(Material.getMaterial(armorType + "_CHESTPLATE"));
            ItemStack pants = new ItemStack(Material.getMaterial(armorType + "_LEGGINGS"));
            ItemStack boots = new ItemStack(Material.getMaterial(armorType + "_BOOTS"));
            List<ItemStack> armor = new ArrayList<ItemStack>();
            armor.add(boots);
            armor.add(pants);
            armor.add(chest);
            armor.add(helm);

            if (armorEnchants != null){
                for (String s : armorEnchants) {
                    if (s.contains(":")) {
                        String[] split = s.split(":");
                        Enchantment e = Enchantment.getByName(Buffs.getOfficialEnchantmentName(split[0].toUpperCase()));
                        for (ItemStack i : armor) {
                            i.addUnsafeEnchantment(e, Integer.parseInt(split[1]));

                        }
                    }
                }
            }
            if (armorLore != null){
                for (ItemStack i : armor){
                    ItemMeta meta = i.getItemMeta();
                    armorLore = translateColorCodes(armorLore);

                    meta.setLore(armorLore);
                    i.setItemMeta(meta);
                }
            }
            player.getInventory().setArmorContents(armor.toArray(new ItemStack[4]));
        }
        else if (armorItems != null){
            player.getInventory().setArmorContents(armorItems.toArray(new ItemStack[4]));
        }

        ItemStack weap = null;
        if (weapon != null) {
            weap = new ItemStack(Material.getMaterial(weapon));
        }

        if (potions != null){
            for (String s : potions) {
                if (s.contains(":")) {
                    final String[] split = s.split(":");
                    plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(Buffs.getOfficialPotionName(split[0])),
                                    Integer.parseInt(split[1]), Integer.parseInt(split[2]), true));
                        }
                    },1L);


                }
            }
        }

        if (weaponEnchants != null) {
            for (String s : weaponEnchants) {
                if (s.contains(":")) {
                    String[] split = s.split(":");
                    Enchantment e = Enchantment.getByName(Buffs.getOfficialEnchantmentName(split[0].toUpperCase()));
                    weap.addUnsafeEnchantment(e, Integer.parseInt(split[1]));

                }
            }
        }

        if (weap != null && weaponLore != null){
            ItemMeta meta = weap.getItemMeta();
            weaponLore = translateColorCodes(weaponLore);
            meta.setLore(weaponLore);
            weap.setItemMeta(meta);
        }
        if (weaponName != null){
            ItemMeta meta = weap.getItemMeta();
            weaponName = ChatColor.translateAlternateColorCodes('&', weaponName);
            meta.setDisplayName(weaponName);
            weap.setItemMeta(meta);
        }

        player.getInventory().setItem(0, weap);
        if (items != null){
            for (ItemStack i : KitManager.parseItems(items)){
                player.getInventory().addItem(i);
            }

        }

        player.updateInventory();

        if (getName().equalsIgnoreCase("Freezer")){
            new AbilityCountdown(180, plugin, player).startFreezerCountdown();
        }
        else if (getName().equalsIgnoreCase("Assassin")){
            new AbilityCountdown(180, plugin, player).startAssassinCountdown();
        }
        else if (getName().equalsIgnoreCase("Pyro")){
            new AbilityCountdown(180, plugin, player).startPyroCountdown();
        }
        player.sendMessage(ChatColor.GREEN + "You have been given the " + getName() + " kit.");

    }


    public static List<String> translateColorCodes(List<String> lore){

        List<String> newList = new ArrayList<String>();
        for (Iterator<String> it = lore.iterator(); it.hasNext();){
            String s = ChatColor.translateAlternateColorCodes('&', it.next());
            newList.add(s);

        }
        return newList;
    }

    public String getKitType(){
        return kitType;
    }

    public String getKitDescription(){
        return kitDescription;
    }

    public String getKitNoPerms() { return kitNoPerms; }

}
