package com.github.mcengine;

import java.sql.Connection;

import org.bukkit.plugin.java.JavaPlugin;

public class MCEngineProfile extends JavaPlugin {
    public static Connection connection;

    @Override
    public void onEnable() {
        String sqlType = getConfig().getString("sqlType");

        if (sqlType.equalsIgnoreCase("mysql")) {
            Class clazz = getClass("com.github.mcengine.MCEngineApiMYSQL");
            String DB_HOST = System.getenv("DB_HOST");
            if (DB_HOST == null) {
                DB_HOST = getConfig().getString("DB_HOST");
            }
            String DB_USER = System.getenv("DB_USER");
            if (DB_USER == null) {
                DB_USER = getConfig().getString("DB_USER");
            }
            String DB_PASS = System.getenv("DB_PASS");
            if (DB_PASS == null) {
                DB_PASS = getConfig().getString("DB_PASS");
            }
            String DB_PORT = System.getenv("DB_PORT");
            if (DB_PORT == null) {
                DB_PORT = getConfig().getString("DB_PORT");
            }
            String DB_NAME = System.getenv("DB_NAME");
            if (DB_NAME == null) {
                DB_NAME = getConfig().getString("DB_NAME");
            }
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