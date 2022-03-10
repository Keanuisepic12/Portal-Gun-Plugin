package me.gamma.portalgun.portalgun.Events;

import me.gamma.portalgun.portalgun.PortalData;
import me.gamma.portalgun.portalgun.Recipes;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PortalGunEvents implements Listener {

    ItemStack gun = Recipes.gun;
    public static HashMap<UUID, PortalData> datas = new HashMap<>();
    ArrayList<Material> allowed = new ArrayList<>();

    //Declares every material the for loop is allowed to pass through in the initial portal shot.
    void setList(){

        allowed.add(Material.AIR); allowed.add(Material.CAVE_AIR);
        allowed.add(Material.VOID_AIR); allowed.add(Material.WATER);
        allowed.add(Material.VINE); allowed.add(Material.FERN);
        allowed.add(Material.GRASS); allowed.add(Material.TALL_GRASS);
        allowed.add(Material.DEAD_BUSH); allowed.add(Material.SEAGRASS);
        allowed.add(Material.DANDELION); allowed.add(Material.POPPY);
        allowed.add(Material.BLUE_ORCHID); allowed.add(Material.ALLIUM);
        allowed.add(Material.AZURE_BLUET); allowed.add(Material.RED_TULIP);
        allowed.add(Material.ORANGE_TULIP); allowed.add(Material.ORANGE_TULIP);
        allowed.add(Material.WHITE_TULIP); allowed.add(Material.PINK_TULIP);
        allowed.add(Material.OXEYE_DAISY); allowed.add(Material.CORNFLOWER);
        allowed.add(Material.LILY_OF_THE_VALLEY); allowed.add(Material.WITHER_ROSE);
        allowed.add(Material.SPORE_BLOSSOM); allowed.add(Material.BROWN_MUSHROOM);
        allowed.add(Material.RED_MUSHROOM); allowed.add(Material.WITHER_ROSE);
        allowed.add(Material.CRIMSON_FUNGUS); allowed.add(Material.WARPED_FUNGUS);
        allowed.add(Material.CRIMSON_ROOTS); allowed.add(Material.WARPED_ROOTS);
        allowed.add(Material.NETHER_SPROUTS); allowed.add(Material.WEEPING_VINES);
        allowed.add(Material.TWISTING_VINES); allowed.add(Material.SUGAR_CANE);
        allowed.add(Material.KELP); allowed.add(Material.LILY_PAD);
        allowed.add(Material.LAVA);

    }


    @EventHandler
    public void itemUse(PlayerInteractEvent e) {

        setList();
        Player p = e.getPlayer();

        //3600 ticks divided by 20 = 180 seconds.
        int lifetime = 3600;

        if (e.getPlayer().getItemInHand().isSimilar(gun)) {

            Color action = Color.ORANGE;
                                             //Add 1.6 to Y axis to put it at eye level.
            Location newLoc = p.getLocation().add(0,1.6,0).add(p.getLocation().getDirection());
            int distance = 25;

            //If action is a left click, set the colour to blue. Otherwise, it's a right click, and doesn't need to be changed.
            if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){action = Color.BLUE;}

            if(!p.isSneaking()) {
                for (int i = 0; i < distance; i++) {

                    //If the block at the location is in the list of blocks it's allowed to pass through, move on.*
                    if (allowed.contains(p.getWorld().getBlockAt(newLoc).getType())) {

                        newLoc = p.getLocation().add(0, 1.6, 0).add(p.getLocation().getDirection().multiply(i));

                        p.spawnParticle(Particle.REDSTONE, newLoc, 3, Float.MIN_VALUE, 0, 0, 0, new Particle.DustOptions(action, 1.0F));

                    } else {

                        //*If it ISN'T, we can infer that it hit a block and that this is the location we can place our portal.
                        newLoc = p.getLocation().add(0, 1.6, 0).add(p.getLocation().getDirection().multiply(i - 3));

                        if (action == Color.ORANGE) {
                            e.getPlayer().getWorld().playSound(newLoc, Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 0.5f, 1);
                        } else {
                            e.getPlayer().getWorld().playSound(newLoc, Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 0.5f, 0.6f);
                        }

                        break;
                    }
                }
                //If player ISN'T sneaking, put a portal facing the player's direction 5 blocks away.
            }else newLoc = p.getLocation().add(0,1.6,0).add(p.getLocation().getDirection().multiply(5)).setDirection(new Vector(newLoc.getDirection().getX() * -1, newLoc.getDirection().getY(), newLoc.getDirection().getZ()*-1));


            if (e.getAction() == Action.LEFT_CLICK_AIR) {

                //If the player has placed a portal already
                if (datas.containsKey(e.getPlayer().getUniqueId())) {

                    datas.get(p.getUniqueId()).setBlue(newLoc, lifetime);
                    
                }else{

                    datas.put(p.getUniqueId(), new PortalData(newLoc, null, lifetime, null));

                }

            } else if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                //If the player has placed a blue portal
                if (datas.containsKey(e.getPlayer().getUniqueId())) {

                    datas.get(p.getUniqueId()).setOrange(newLoc, lifetime);

                }else{

                    datas.put(p.getUniqueId(), new PortalData(null, newLoc, null, lifetime));

                }

            }

            datas.get(p.getUniqueId()).setUuid(p.getUniqueId());

        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){

        if(datas.containsKey(e.getPlayer().getUniqueId())){

            datas.remove(e.getPlayer().getUniqueId());

        }

    }
}
