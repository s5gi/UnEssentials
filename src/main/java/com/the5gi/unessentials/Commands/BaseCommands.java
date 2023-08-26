package com.the5gi.unessentials.Commands;

import java.util.ArrayList;
import java.util.List;

import com.the5gi.unessentials.UnEssentials;
import com.the5gi.unessentials.Util.Config;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public class BaseCommands {
    public static final CommandExecutor unessentials;

    public static final TabCompleter unessentialsTabCompleter;

    static {
        unessentials = ((sender, command, label, args) -> {
            if (args.length == 0) {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cRunning &oUnEssentials &r&c&lv" + UnEssentials.plugin.getDescription().getVersion()));
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("unessentials.reload")) {
                    long before = System.currentTimeMillis();
                    Config.reload();
                    long after = System.currentTimeMillis();
                    long time = after - before;
                    sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cReloaded in " + time + "ms"));
                    UnEssentials.plugin.getLogger().info("&cReloaded in " + time + "ms");
                } else {
                    sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cUnknown Argument"));
                }
            }
            return false;
        });
        unessentialsTabCompleter = ((sender, command, label, args) -> {
            List<String> l = new ArrayList<>();
            if (args.length == 1) {
                l.add("reload");
            } else if (args.length >= 2) {
                l.add("any_arguments_after_and_including_this_will_not_do_anything");
            }
            return l;
        });
    }
}
