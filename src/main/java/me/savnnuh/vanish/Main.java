package me.savnnuh.vanish;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    private Set<UUID> vanished;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        vanished = new HashSet<>();
        System.out.println("[Vanish] plugin geladen");
    }

    @Override
    public void onDisable() {
        System.out.println("[Vanish] plugin uitgeschakeld");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vanish") && sender instanceof Player) {
            Player player = (Player) sender;
            if (vanished.contains(player.getUniqueId())) {
                vanished.remove(player.getUniqueId());
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(this, player));
                player.sendMessage(ChatColor.GREEN + "Je bent succesvol uit vanish gegaan.");
                return true;
            }
            vanished.add(player.getUniqueId());
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.hidePlayer(this, player));
            player.sendMessage(ChatColor.GREEN + "Je bent succesvol in vanish gegaan.");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (UUID uuid : vanished) {
            Player vanishedPlayer = Bukkit.getPlayer(uuid);
            if (vanishedPlayer != null) event.getPlayer().hidePlayer(this, vanishedPlayer);
        }
    }
}
