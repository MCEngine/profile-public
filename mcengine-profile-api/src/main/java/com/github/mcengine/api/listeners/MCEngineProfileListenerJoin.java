package com.github.mcengine.api.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.mcengine.api.MCEngineProfileMYSQL;

import org.bukkit.entity.Player;

public class MCEngineProfileListenerJoin {
    public static Class<?> dbClazz;

    public static void init(String className) {
        dbClazz = MCEngineProfile.getClass(className);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String table = "profiles";
        int profileData = 0;
        // get connection from main class
        String rs = dbClazz.getProfile(uuid, table);
    }
}