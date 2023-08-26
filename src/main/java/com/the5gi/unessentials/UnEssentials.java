package com.the5gi.unessentials;

import com.the5gi.unessentials.Chat.Listeners.ChatReformatting;
import com.the5gi.unessentials.Commands.BaseCommands;
import com.the5gi.unessentials.Commands.CoolCommands;
import com.the5gi.unessentials.Commands.GameModesCommands;
import com.the5gi.unessentials.Util.Config;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class UnEssentials extends JavaPlugin {
    public static boolean PAPI = false;

    public static boolean LUCKPERMS = false;

    public static String PREFIX = ChatColor.translateAlternateColorCodes('&', "&c&l[UnEssentials]&r ");

    public static Plugin plugin;

    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            PAPI = true;
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null)
            LUCKPERMS = true;
        plugin = this;
        Config.onStart();
        PREFIX = color(Config.getFieldString(Config.BaseLevel.PLUGIN_PREFIX));
        getServer().getScheduler().scheduleSyncRepeatingTask(this, UnEssentials::tickCycle, 20L, 0L);
        getServer().getPluginManager().registerEvents(new ChatReformatting(), this);
        getServer().getPluginManager().registerEvents(CoolCommands.deathListener, this);
        Objects.requireNonNull(getCommand("gms")).setExecutor(GameModesCommands.GMS);
        Objects.requireNonNull(getCommand("gmc")).setExecutor(GameModesCommands.GMC);
        Objects.requireNonNull(getCommand("gma")).setExecutor(GameModesCommands.GMA);
        Objects.requireNonNull(getCommand("gmsp")).setExecutor(GameModesCommands.GMSP);
        Objects.requireNonNull(getCommand("gms")).setTabCompleter(new GameModesCommands());
        Objects.requireNonNull(getCommand("gmc")).setTabCompleter(new GameModesCommands());
        Objects.requireNonNull(getCommand("gma")).setTabCompleter(new GameModesCommands());
        Objects.requireNonNull(getCommand("gmsp")).setTabCompleter(new GameModesCommands());
        Objects.requireNonNull(getCommand("essenchant")).setExecutor(CoolCommands.essenchant);
        Objects.requireNonNull(getCommand("essenchant")).setTabCompleter(CoolCommands.essenchantTabCompleter);
        Objects.requireNonNull(getCommand("chicken")).setExecutor(CoolCommands.chicken);
        Objects.requireNonNull(getCommand("hat")).setExecutor(CoolCommands.hat);
        Objects.requireNonNull(getCommand("fly")).setExecutor(CoolCommands.fly);
        Objects.requireNonNull(getCommand("unessentials")).setExecutor(BaseCommands.unessentials);
        Objects.requireNonNull(getCommand("unessentials")).setTabCompleter(BaseCommands.unessentialsTabCompleter);
        Objects.requireNonNull(getCommand("unessentials")).setAliases(List.of("uness"));
        Objects.requireNonNull(getCommand("top")).setExecutor(CoolCommands.top);
        Objects.requireNonNull(getCommand("butcher")).setExecutor(CoolCommands.butcher);
        Objects.requireNonNull(getCommand("back")).setExecutor(CoolCommands.back);
        Objects.requireNonNull(getCommand("name")).setExecutor(CoolCommands.name);
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void tickCycle() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            String preName = player.getName();;
            if (UnEssentials.PAPI && Config.getFieldBool(Config.Tab.TAB_FORMAT_ENABLED)) preName = color(PlaceholderAPI.setPlaceholders(player, Config.getFieldString(Config.Tab.TAB_FORMAT).replace("%NAME%", player.getName())));
            player.setPlayerListName(preName);
        }
    }
}
