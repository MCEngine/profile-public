package com.github.mcengine.api.listeners;

import java.sql.Connection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import com.github.mcengine.MCEngineProfile;

public class MCEngineProfileListenerJoin {
    public static Connection connection = MCEngineProfile.connection;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String table = "profiles";
        int profileData = 0;
        // get connection from main class
        getProfile(uuid, table, connection);
    }
}