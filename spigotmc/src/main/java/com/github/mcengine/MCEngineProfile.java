package com.github.mcengine;

import java.util.Optional;
import java.lang.Class;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.mcengine.api.MCEngineProfileMYSQL;
import com.github.mcengine.api.listeners.MCEngineProfileListenerJoin;

public class MCEngineProfile extends JavaPlugin {
    private static MCEngineProfile instance;

    public static Class<?> dbClazz;

    // Function to get the Class object from a class name
    public static Class<?> getClass(String className) {
        try {
            // Return the Class object associated with the class name
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String getEnvOrConfig(String envVarName, String configKey) {
        String value = System.getenv(envVarName);
        return Optional.ofNullable(value).orElseGet(() -> instance.getConfig().getString(configKey));
    }

    @Override
    public void onEnable() {
        instance = this;
        String sqlType = getConfig().getString("sqlType");

        if (sqlType.equalsIgnoreCase("mysql")) {
            dbClazz = getClass("com.github.mcengine.MCEngineApiMYSQL");
            String DB_HOST = getEnvOrConfig("DB_HOST", "DB.HOST");
            String DB_USER = getEnvOrConfig("DB_USER", "DB.USER");
            String DB_PASS = getEnvOrConfig("DB_PASS", "DB.PASS");
            String DB_PORT = getEnvOrConfig("DB_PORT", "DB.PORT");
            String DB_NAME = getEnvOrConfig("DB_NAME", "DB.NAME");
            MCEngineProfileMYSQL.init(DB_HOST, DB_NAME, DB_USER, DB_PASS, DB_PORT);
            MCEngineProfileListenerJoin.init("com.github.mcengine.MCEngineApiMYSQL");
        } else {
            getLogger().warning("Unsupported SQL type: " + sqlType);
        }
    }

    @Override
    public void onDisable() {}
}