package me.gamma.portalgun.portalgun;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes {

    public static ItemStack gun;

    public static void init(){
        createGun();
    }

    private static void createGun(){

        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Portal Gun");

        item.setItemMeta(meta);
        gun = item;

        //Shaped Recipe

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("gammaportalgun"), item);
        sr.shape(" II",
                 "DPI",
                 "ND ");

        sr.setIngredient('I', Material.IRON_INGOT);
        sr.setIngredient('D', Material.DIAMOND);
        sr.setIngredient('P', Material.ENDER_PEARL);
        sr.setIngredient('N', Material.NETHER_STAR);

        Bukkit.getServer().addRecipe(sr);

    }

}
