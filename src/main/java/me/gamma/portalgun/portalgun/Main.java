package me.gamma.portalgun.portalgun;

import me.gamma.portalgun.portalgun.Events.PortalGunEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        Recipes.init();

        getServer().getPluginManager().registerEvents(new PortalGunEvents(), this);
        BukkitTask runTask = new TimedEvents(this).runTaskTimer(this, 1, 1);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
