package com.github.mcengine.api.listeners;

import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.entity.Player;

public class MCEngineProfileListenerJoin implements Listener {
    public static Class<?> dbClazz;

    public static void init(String className) {
        try {
            dbClazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception or rethrow it
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String table = "profiles";
        
        try {
            // Assume getProfile is a static method. If it's an instance method, you'll need to create an instance of dbClazz.
            Method getProfileMethod = dbClazz.getMethod("getProfile", UUID.class, String.class);
            String rs = (String) getProfileMethod.invoke(null, uuid, table);  // Use null for static method, or an instance of dbClazz for instance method
            
            // Process the result (rs) as needed
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception or rethrow it
        }
    }
}
