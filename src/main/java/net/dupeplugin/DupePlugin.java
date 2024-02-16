package net.dupeplugin;

import net.dupeplugin.commands.BlacklistCommand;
import net.dupeplugin.commands.DupeCommand;
import net.dupeplugin.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DupePlugin extends JavaPlugin {

    public Config blacklist = new Config("blacklist", this);

    @Override
    public void onEnable() {
        super.onEnable();

        getCommand("dupe").setExecutor(new DupeCommand(this));
        getCommand("blacklist").setExecutor(new BlacklistCommand(this));
        Bukkit.getLogger().info("Dupe loaded");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
