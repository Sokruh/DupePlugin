package net.dupeplugin.commands;



import net.dupeplugin.DupePlugin;
import net.dupeplugin.config.Config;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import de.tr7zw.nbtapi.NBTItem;


import java.util.List;



public class DupeCommand implements CommandExecutor {


    public DupePlugin dupe;
    public DupeCommand(DupePlugin dupe) {
        this.dupe = dupe;

    }



    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (s.equalsIgnoreCase("dupe")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;

                if (!player.hasPermission("Dupe.use")) {
                    player.sendMessage("§cYou do not have permissions to use this command!");
                    return false;
                }

                if (strings.length == 0) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (!checkItem(item)) {
                        player.sendMessage("§cYour item cannot be duplicated!");
                        return false;
                    }
                    if(!hasInventorySpace(player, item)) {
                        player.sendMessage("§cYou don't have inventory space!");
                        return false;
                    }
                    player.getInventory().addItem(item);
                }

                else if (strings.length >= 1) {
                    if (Integer.parseInt(strings[0]) > 10 || Integer.parseInt(strings[0]) < 1) {
                        player.sendMessage("§cNumber between 1-10");
                        return false;
                    }
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (!checkItem(item)) {
                        player.sendMessage("§cYour item cannot be duplicated!");
                        return false;
                    }
                    if (!player.hasPermission("Dupe.limit." + strings[0])) {
                        player.sendMessage("§cYou do not have permissions to use this command!");
                        return false;
                    }

                    for (int i = Integer.parseInt(strings[0]); i>0; i--) {
                        if (!hasInventorySpace(player, item)) {
                            player.sendMessage("§cYou don't have inventory space!");
                            break;
                        }
                        player.getInventory().addItem(item);
                    }

                }



            }
        }

        return false;
    }

    private boolean checkItem(ItemStack itemStack) {
        NBTItem item = new NBTItem(itemStack);
        if (item.hasKey("lifesteal-smp-plugin")) return false;
        if (item.hasKey("lifesteal:item")) return false;
        if (item.hasKey("excellentcrates:crate_key")) return false;
        if (item.hasKey("rankVoucher")) return false;
        if(itemStack.getType() == Material.AIR) return false;
        if(itemStack.getType() == Material.RED_DYE) return false;
        if(dupe.blacklist.getConfig().getBoolean("Items.items." + itemStack.getType())) return false;

        if(isShulkerBox(itemStack)) return false;


        return true;
    }


    public boolean isShulkerBox(ItemStack item) {
        Material material = item.getType();
        return material.toString().endsWith("_SHULKER_BOX") || material.toString().endsWith("SHULKER_BOX");
    }

    public boolean hasInventorySpace(Player player, ItemStack itemStack) {
        int remainingSpace = 0;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack == null || stack.getType().isAir()) {
                remainingSpace += itemStack.getMaxStackSize();
            } else if (stack.isSimilar(itemStack)) {
                remainingSpace += itemStack.getMaxStackSize() - stack.getAmount();
            }
        }
        return remainingSpace >= itemStack.getAmount();
    }
}