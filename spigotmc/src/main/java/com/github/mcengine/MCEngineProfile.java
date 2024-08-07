package com.github.mcengine;

import java.util.Optional;
import java.lang.Class;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.mcengine.api.MCEngineApiUtil;
import com.github.mcengine.api.MCEngineProfileMYSQL;
import com.github.mcengine.api.listeners.MCEngineProfileListenerJoin;

public class MCEngineProfile extends JavaPlugin {
    private static MCEngineProfile instance;

    public static Class<?> dbClazz;

    @Override
    public void onEnable() {
        instance = this;
        String sqlType = getConfig().getString("sqlType");

        if (sqlType.equalsIgnoreCase("mysql")) {
            initMySQL();
        } else {
            getLogger().warning("Unsupported SQL type: " + sqlType);
        }
    }

    public static void initMySQL() {
        try {
            dbClazz = MCEngineApiUtil.getClass("com.github.mcengine.MCEngineApiMYSQL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String DB_HOST = MCEngineApiUtil.getEnvOrConfig(instance, "DB_HOST", "DB.HOST");
        String DB_USER = MCEngineApiUtil.getEnvOrConfig(instance, "DB_USER", "DB.USER");
        String DB_PASS = MCEngineApiUtil.getEnvOrConfig(instance, "DB_PASS", "DB.PASS");
        String DB_PORT = MCEngineApiUtil.getEnvOrConfig(instance, "DB_PORT", "DB.PORT");
        String DB_NAME = MCEngineApiUtil.getEnvOrConfig(instance, "DB_NAME", "DB.NAME");
        MCEngineProfileMYSQL.init(DB_HOST, DB_NAME, DB_USER, DB_PASS, DB_PORT);
        MCEngineProfileListenerJoin.init("com.github.mcengine.MCEngineApiMYSQL");
    }

    @Override
    public void onDisable() {}
}