package net.dupeplugin.commands;

import net.dupeplugin.DupePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlacklistCommand implements TabExecutor {

    private DupePlugin plugin;

    public BlacklistCommand(DupePlugin plugin) {
        this.plugin = plugin;
    }

    private void addToBlacklist(ItemStack itemStack) {
        plugin.blacklist.getConfig().set("Items.items." + itemStack.getType(), true);
        plugin.blacklist.saveConfig();
    }
    private void removeFromBlacklist(ItemStack itemStack) {
        plugin.blacklist.getConfig().set("Items.items." + itemStack.getType(), null);
        plugin.blacklist.saveConfig();
    }

    private String getBlacklist() {
        List<String> x = new ArrayList<>(plugin.blacklist.getConfig().getConfigurationSection("Items.items").getKeys(false));
        return x.toString();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!s.equalsIgnoreCase("blacklist")) return false;
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        player.sendMessage("jee");
        if (strings.length == 1) {
            player.sendMessage("ok");
            if (strings[0].equalsIgnoreCase("add")) {
                player.sendMessage("added");
                if(!player.hasPermission("Dupe.blacklist.add")) {
                    player.sendMessage("§cYou do not have permission to use this!");
                    return false;
                }
                addToBlacklist(player.getInventory().getItemInMainHand());
                player.sendMessage("added");
                return true;
            }
            if(strings[0].equalsIgnoreCase("remove")) {
                player.sendMessage("removed");
                if(!player.hasPermission("Dupe.blacklist.remove")) {
                    player.sendMessage("§cYou do not have permission to use this!");
                    return false;
                }
                removeFromBlacklist(player.getInventory().getItemInMainHand());
                player.sendMessage("removed");
                return true;

            }
            if(strings[0].equalsIgnoreCase("list")) {
                player.sendMessage("lista");
                if(!player.hasPermission("Dupe.blacklist.list")) {
                    player.sendMessage("§cYou do not have permission to use this!");
                    return false;
                }
                player.sendMessage("§c" + getBlacklist());
                player.sendMessage("lista");
                return true;

            }


        }



        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            list.add("add");
            list.add("remove");
            list.add("list");
            return list;
        }
        return list;
    }
}
