package com.github.mcengine;

import java.sql.Connection;

import org.bukkit.plugin.java.JavaPlugin;

public class MCEngineProfile extends JavaPlugin {
    public static Connection connection;

    public static String getEnvOrConfig(String envVarName, String configKey) {
        String value = System.getenv(envVarName);
        return Optional.ofNullable(value).orElseGet(() -> getConfig().getString(configKey));
    }

    @Override
    public void onEnable() {
        String sqlType = getConfig().getString("sqlType");

        if (sqlType.equalsIgnoreCase("mysql")) {
            Class clazz = getClass("com.github.mcengine.MCEngineApiMYSQL");
            String DB_HOST = getEnvOrConfig("DB_HOST", "DB_HOST");
            String DB_USER = getEnvOrConfig("DB_USER", "DB_USER");
            String DB_PASS = getEnvOrConfig("DB_PASS", "DB_PASS");
            String DB_PORT = getEnvOrConfig("DB_PORT", "DB_PORT");
            String DB_NAME = getEnvOrConfig("DB_NAME", "DB_NAME");
            connection = MCEngineApiMYSQL.getConnection(DB_HOST, DB_NAME, DB_USER, DB_PASS, DB_PORT);
        } else {
            getLogger().warning("Unsupported SQL type: " + sqlType);
        }
    }

    @Override
    public void onDisable() {}

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
}