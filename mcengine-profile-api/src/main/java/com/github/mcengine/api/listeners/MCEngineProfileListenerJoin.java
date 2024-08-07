package com.github.mcengine.api.listeners;

import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.entity.Player;

import com.github.mcengine.api.MCEngineApiUtil;

public class MCEngineProfileListenerJoin implements Listener {
    public static Class<?> dbClazz;

    public static void init(String className) {
        try {
            dbClazz = MCEngineApiUtil.getClass(className);
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
            Method getProfile = dbClazz.getMethod("getProfile", UUID.class, String.class);
            String rs = (String) getProfile.invoke(null, uuid, table);  // Use null for static method, or an instance of dbClazz for instance method

            if (rs == null) {
                Method createProfile = dbClazz.getMethod("createProfile", UUID.class, String.class, String.class);
                createProfile.invoke(null, uuid, table, 0);  // Use null for static method, or an instance of dbClazz for instance method
            }
            // Process the result (rs) as needed
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception or rethrow it
        }
    }
}
