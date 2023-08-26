package com.the5gi.unessentials.Chat.Listeners;

import com.the5gi.unessentials.UnEssentials;
import com.the5gi.unessentials.Util.Config;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatReformatting implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onMessage(AsyncPlayerChatEvent event) {
        if (Config.getFieldBool(Config.Chat.CHAT_FORMAT_ENABLED)) {
            event.setCancelled(true);
            String messagePre = UnEssentials.color(
                    PlaceholderAPI.setPlaceholders(event
                                    .getPlayer(),
                            Config.getFieldString(Config.Chat.CHAT_FORMAT).replace("%MESSAGE%", event.getMessage()).replace("%NAME%", event.getPlayer().getName())));
            UnEssentials.plugin.getServer().broadcastMessage(messagePre);
        }
    }
}
