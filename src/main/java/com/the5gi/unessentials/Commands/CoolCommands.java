package com.the5gi.unessentials.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.the5gi.unessentials.UnEssentials;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CoolCommands {
    public static HashMap<Player, Location> deathLocation = new HashMap<>();

    public static final CommandExecutor top;

    public static final CommandExecutor essenchant;

    public static TabCompleter essenchantTabCompleter;

    public static final CommandExecutor hat;

    public static final CommandExecutor fly;

    public static final CommandExecutor chicken;

    public static final CommandExecutor butcher;

    static {
        top = ((sender, command, label, args) -> {
            if (!sender.hasPermission("unessentials.top")) {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
            } else if (sender instanceof Player player) {
                World world = player.getWorld();
                Block block = world.getHighestBlockAt(player.getLocation());
                if (player.getLocation().getY() == block.getY() + 1) {
                    player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou are already on the top!"));
                    return false;
                } else {
                    player.teleport(new Location(world, block.getX() + 0.5D, block.getY() + 1.5D, block.getZ() + 0.5D, player.getLocation().getYaw(), player.getLocation().getPitch()));
                    player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&aTeleported you to the top!"));
                }
            } else {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cConsole cannot execute this command."));
            }
            return false;
        });
        essenchant = ((sender, command, label, args) -> {
            if (!sender.hasPermission("unessentials.enchant")) {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
                return true;
            }
            List<Enchantment> enchantments = List.of(Enchantment.values());
            HashMap<String, Enchantment> enchantmentNames = new HashMap<>();
            for (Enchantment enchantment : enchantments)
                enchantmentNames.put(enchantment.getKey().getKey(), enchantment);
            if (sender instanceof Player player) {
                if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou are not holding an item."));
                    return true;
                }
                if (args.length >= 2) {
                    int enchantLevel;
                    String enchantName = args[0];
                    String enchantLevelS = args[1];
                    if (!enchantmentNames.containsKey(enchantName)) {
                        player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cThe enchantment \"" + UnEssentials.PREFIX + "\" does not exist."));
                        return false;
                    }
                    Enchantment enchantment = enchantmentNames.get(enchantName);
                    try {
                        enchantLevel = Integer.parseInt(enchantLevelS);
                    } catch (NumberFormatException ignore) {
                        player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cPlease enter a real number."));
                        return false;
                    }
                    ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                    assert meta != null;
                    meta.addEnchant(enchantment, enchantLevel, true);
                    player.getInventory().getItemInMainHand().setItemMeta(meta);
                    player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&aEnchantment Applied!"));
                    return false;
                }
                if (args.length == 1)
                    sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cNot enough Arguments!. Expected 2"));
            } else {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cConsole cannot execute this command."));
                return false;
            }
            return false;
        });
        essenchantTabCompleter = ((sender, command, label, args) -> {
            List<String> l = new ArrayList<>();
            if (args.length == 1) {
                List<Enchantment> enchantments = List.of(Enchantment.values());
                for (Enchantment enchantment : enchantments)
                    l.add(enchantment.getKey().getKey());
            } else if (args.length >= 3) {
                l.add("any_arguments_after_and_including_this_will_not_do_anything");
            }
            return l;
        });
        hat = ((sender, command, label, args) -> {
            if (sender instanceof Player player) {
                if (player.hasPermission("unessentials.hat")) {
                    if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                        ItemStack prevHat = player.getInventory().getHelmet();
                        player.getInventory().setHelmet(player.getInventory().getItemInMainHand());
                        player.getInventory().getItemInMainHand().setAmount(0);
                        player.getInventory().setItemInMainHand(prevHat);
                        player.playSound(player, Sound.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
                        return false;
                    }
                    sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou have nothing in your hand!"));
                    return true;
                }
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
                return true;
            }
            sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cConsole cannot execute this command."));
            return false;
        });
        fly = ((sender, command, label, args) -> {
            if (sender.hasPermission("unessentials.fly")) {
                if (sender instanceof Player player) {
                    player.setAllowFlight(!player.getAllowFlight());
                    if (player.getAllowFlight()) {
                        player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou can now fly!"));
                    } else {
                        player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou can no longer fly."));
                    }
                } else {
                    sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cConsole cannot execute this command."));
                }
            } else {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
            }
            return false;
        });
        chicken = ((sender, command, label, args) -> {
            if (sender instanceof Player player) {
                if (player.hasPermission("unessentials.chicken"))
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN, true);
            }
            return false;
        });
        butcher = ((sender, command, label, args) -> {
            if (sender.hasPermission("unessentials.butcher")) {
                if (sender instanceof Player player) {
                    if (args.length == 1) {
                        int radius;
                        try {
                            radius = Integer.parseInt(args[0]);
                        } catch (NumberFormatException ignore) {
                            player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&aPlease enter a valid number!"));
                            return false;
                        }
                        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
                        int entityCount = 0;
                        for (Entity entity : nearbyEntities) {
                            if (entity instanceof LivingEntity livingEntity) {
                                if (!(entity instanceof Player)) livingEntity.remove();
                                entityCount++;
                            }
                        }
                        player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&c&lKilled&r&c&o " + entityCount + "&r&c entities."));
                    } else {
                        sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cNot enough Arguments!. Expected 1"));
                        return false;
                    }
                } else {
                    sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cConsole cannot execute this command."));
                    return false;
                }
            } else {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
                return false;
            }
            return false;
        });
    }

    public static Listener deathListener = new Listener() {
        public int hashCode() {
            return super.hashCode();
        }

        @EventHandler
        public void onDeath(PlayerDeathEvent event) {
            CoolCommands.deathLocation.put(event.getEntity(), event.getEntity().getLocation());
        }
    };

    public static final CommandExecutor back;

    public static final CommandExecutor name;

    static {
        back = ((sender, command, label, args) -> {
            if (sender.hasPermission("unessentials.back")) {
                if (sender instanceof Player player) {
                    if (deathLocation.containsKey(player)) {
                        player.teleport(deathLocation.get(player));
                        player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou have been teleported to your last death location."));
                        deathLocation.remove(player);
                    } else {
                        player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou have not died yet."));
                    }
                } else {
                    sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
                }
            } else {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
            }
            return false;
        });
        name = ((sender, command, label, args) -> {
            if (sender.hasPermission("unessentials.name")) {
                if (args.length < 1) {
                    sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cNot enough Arguments!. Expected at least 1"));
                    return false;
                }
                if (sender instanceof Player player) {
                    if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        ItemMeta meta = itemStack.getItemMeta();
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            builder.append(args[i]);
                            if (i != args.length - 1)
                                builder.append(" ");
                        }
                        assert meta != null;
                        meta.setDisplayName(UnEssentials.color(builder.toString()));
                        itemStack.setItemMeta(meta);
                        player.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cSet."));
                    }
                } else {
                    sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
                }
            } else {
                sender.sendMessage(UnEssentials.color(UnEssentials.PREFIX + "&cYou do not have permission to execute this command!"));
            }
            return false;
        });
    }
}
