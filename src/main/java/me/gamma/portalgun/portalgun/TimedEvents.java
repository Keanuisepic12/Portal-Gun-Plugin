package me.gamma.portalgun.portalgun;

import me.gamma.portalgun.portalgun.Events.PortalGunEvents;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TimedEvents extends BukkitRunnable {

    Main plugin;

    public TimedEvents(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {

        for(PortalData i : PortalGunEvents.datas.values()){

            Player owner = Bukkit.getPlayer(i.getUUID());

        //---Blue Portal---
            if(i.getBlue() != null) {

                //Show particle for blue portal
                Bukkit.getPlayer(i.getUUID()).getWorld().spawnParticle(Particle.REDSTONE, i.getBlue(), 3, 0, 0, 0, 0, new Particle.DustOptions(Color.BLUE, 10f));

                //Increment portals life time by -1
                i.setBlueLifetime(i.getBlueLifetime() - 1);

                if(i.getBlueLifetime() < 1) {

                    i.setBlue(null);

                    //Play portal breaking sound for everyone in the portal owners world, at the portals location
                    if(owner.isOnline()) owner.getWorld().playSound(i.getBlue(), Sound.BLOCK_GLASS_BREAK, 0.3f, 0.5f);

                    //Play portal breaking sound for the owner of the portal, so they know it broke
                    if(owner.isOnline()) owner.playSound(owner.getLocation(), Sound.BLOCK_GLASS_BREAK, 0.3f, 0.5f);

                }
            }

        //---Orange Portal---
            if(i.getOrange() != null){

                Bukkit.getPlayer(i.getUUID()).getWorld().spawnParticle(Particle.REDSTONE, i.getOrange(), 3, 0, 0, 0, 0, new Particle.DustOptions(Color.ORANGE, 10f));
                i.setOrangeLifetime(i.getOrangeLifetime() - 1);

                if(i.getOrangeLifetime() < 1){

                    i.setOrange(null);
                    if(owner.isOnline()) owner.getWorld().playSound(i.getOrange(), Sound.BLOCK_GLASS_BREAK, 0.3f, 0.6f);
                    if(owner.isOnline()) owner.playSound(owner.getLocation(), Sound.BLOCK_GLASS_BREAK, 0.3f, 0.6f);

                }
            }


        }

        for(PortalData p : PortalGunEvents.datas.values()) {

            //If both portals are active
            if (p.portalsActive()) {

                //If there are entities in the blue portals world (E.G if the portal is in the nether and nobody is in the nether there wouldn't be any entities)
                if (!p.getBlue().getWorld().getEntities().isEmpty()) {

                    //For every entity in the blue portals world
                    for (Entity i : p.getBlue().getWorld().getEntities()) {

                        try {

                            //If the entity is less than 1.8 blocks away
                            if (i.getLocation().distance(p.getBlue()) < 1.8) {

                                i.getWorld().playSound(i.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

                                Location tempLoc = p.getOrange();
                                Location playerLoc = i.getLocation();

                                if (i instanceof Arrow || i instanceof EnderPearl || i instanceof Snowball || i instanceof Egg) {


                                    //If the entity is an arrow, ender pearl, snowball, or egg, the direction is multiplied by -1, effectively
                                    //turning it around, and it's X and Z velocities are multiplied by -1 but not it's Y velocity.

                                    i.teleport(tempLoc.clone().add(tempLoc.getDirection().multiply(-2)).setDirection(playerLoc.getDirection()));
                                    i.setVelocity(new Vector(i.getVelocity().getX() * -1, i.getVelocity().getY(), i.getVelocity().getZ() * -1));

                                } else {

                                    //Same thing but for entities like players, zombies, items, etc. They are teleported to the other portal
                                    //but 2 blocks in front of it to not instantly teleport them back to the other portal.
                                    //Some extra velocity in the portal's direction is also added so the entity doesn't walk straight back
                                    //into the portal after being teleported.

                                    i.teleport(tempLoc.clone().add(tempLoc.getDirection().multiply(-2)).setDirection(playerLoc.getDirection()));
                                    i.setVelocity(tempLoc.getDirection().clone().multiply(-0.3));

                                }

                                i.getWorld().playSound(i.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                            }

                        //Just to get rid of the annoying errors in the log that didn't affect anything, but I couldn't fix.
                        //Sorry for the sloppy code ;p
                        } catch (IllegalArgumentException exception) {
                        }

                    }
                }

                if (!p.getOrange().getWorld().getEntities().isEmpty()) {
                    for (Entity i : p.getOrange().getWorld().getEntities()) {

                        try {
                            if (i.getLocation().distance(p.getOrange()) < 1.8) {

                                if (p.portalsActive()) {
                                    i.getWorld().playSound(i.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);

                                    Location tempLoc = p.getBlue();
                                    Location playerLoc = i.getLocation();

                                    if (i instanceof Arrow || i instanceof EnderPearl || i instanceof Snowball) {

                                        i.teleport(tempLoc.clone().add(tempLoc.getDirection().multiply(-2)).setDirection(playerLoc.getDirection()));
                                        i.setVelocity(new Vector(i.getVelocity().getX() * -1, i.getVelocity().getY(), i.getVelocity().getZ() * -1));

                                    } else {

                                        i.teleport(tempLoc.clone().add(tempLoc.getDirection().multiply(-2)).setDirection(playerLoc.getDirection()));
                                        i.setVelocity(tempLoc.getDirection().clone().multiply(-0.3));

                                    }

                                    i.getWorld().playSound(i.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                                }
                            }

                        } catch (IllegalArgumentException exception) {
                        }
                    }
                }
            }
        }
    }
}
