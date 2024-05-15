package com.the5gi.unessentials.Commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.the5gi.unessentials.UnEssentials;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameModesCommands implements TabCompleter {
    public static final CommandExecutor GMS;

    public static final CommandExecutor GMC;

    public static final CommandExecutor GMSP;

    public static final CommandExecutor GMA;

    static {
        GMS = ((sender, command, label, args) -> {
            if (!sender.hasPermission("unessentials.gms")) {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
                return true;
            }
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYour gamemode was updated to SURVIVAL."));
                    return false;
                }
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cConsole Cannot execute this command!"));
                return false;
            }
            if (args.length >= 1 && isPlayerOnline(args[0])) {
                Player player = UnEssentials.plugin.getServer().getPlayer(args[0]);
                assert player != null;
                player.setGameMode(GameMode.SURVIVAL);
                String apostropheCheck = player.getName().endsWith("s") ? "'" : "'s";
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + player.getName() + apostropheCheck + " gamemode was set to SURVIVAL."));
                player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYour gamemode was updated to SURVIVAL."));
                return false;
            }
            return false;
        });
        GMC = ((sender, command, label, args) -> {
            if (!sender.hasPermission("unessentials.gmc")) {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
                return true;
            }
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYour gamemode was updated to CREATIVE."));
                    return false;
                }
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cConsole Cannot execute this command!"));
                return false;
            }
            if (args.length >= 1 && isPlayerOnline(args[0])) {
                Player player = UnEssentials.plugin.getServer().getPlayer(args[0]);
                assert player != null;
                player.setGameMode(GameMode.CREATIVE);
                String apostropheCheck = player.getName().endsWith("s") ? "'" : "'s";
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + player.getName() + apostropheCheck + " gamemode was set to CREATIVE."));
                player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYour gamemode was updated to CREATIVE."));
                return false;
            }
            return false;
        });
        GMSP = ((sender, command, label, args) -> {
            if (!sender.hasPermission("unessentials.gmsp")) {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
                return true;
            }
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYour gamemode was updated to SPECTATOR."));
                    return false;
                }
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cConsole Cannot execute this command!"));
                return false;
            }
            if (args.length >= 1 && isPlayerOnline(args[0])) {
                Player player = UnEssentials.plugin.getServer().getPlayer(args[0]);
                assert player != null;
                player.setGameMode(GameMode.SPECTATOR);
                String apostropheCheck = player.getName().endsWith("s") ? "'" : "'s";
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + player.getName() + apostropheCheck + " gamemode was set to SPECTATOR."));
                player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYour gamemode was updated to SPECTATOR."));
                return false;
            }
            return false;
        });
        GMA = ((sender, command, label, args) -> {
            if (!sender.hasPermission("unessentials.gms")) {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
                return true;
            }
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYour gamemode was updated to ADVENTURE."));
                    return false;
                }
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cConsole Cannot execute this command!"));
                return false;
            }
            if (args.length >= 1 && isPlayerOnline(args[0])) {
                Player player = UnEssentials.plugin.getServer().getPlayer(args[0]);
                assert player != null;
                player.setGameMode(GameMode.ADVENTURE);
                String apostropheCheck = player.getName().endsWith("s") ? "'" : "'s";
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + player.getName() + apostropheCheck + " gamemode was set to ADVENTURE."));
                player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYour gamemode was updated to ADVENTURE."));
                return false;
            }
            return false;
        });
    }

    private static boolean isPlayerOnline(String name) {
        for (Player player : UnEssentials.plugin.getServer().getOnlinePlayers()) {
            if (player.getName().equals(name))
                return true;
        }
        return false;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> l = new ArrayList<>();
        if (args.length == 2) {
            l.add("any_arguments_after_and_including_this_will_not_do_anything");
        } else if (args.length == 1) {
            l.addAll(getNames());
        }
        return l;
    }

    private Collection<String> getNames() {
        Collection<String> s = new ArrayList<>();
        for (Player player : UnEssentials.plugin.getServer().getOnlinePlayers())
            s.add(player.getName());
        return s;
    }
}
