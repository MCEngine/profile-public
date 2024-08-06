package com.github.mcengine.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MCEngineProfileMYSQL {
    public static Connection connection;

    public static void init(String host, String database, String user, String password, String port) {
        connection = MCEngineApiMYSQL.getConnection(host, database, user, password, port);
    }

    public static void checkConnection() {
        if (connection == null) {
            // Handle null connection appropriately, e.g., throw an exception
            throw new IllegalArgumentException("Connection cannot be null");
        }
    }

    public static String getProfile(UUID uuid, String table) {
        String query = "SELECT * FROM " + table + " WHERE uuid = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, uuid);
    
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Assuming you want to return a JSON or serialized representation of the profile data
                    // Replace this with appropriate logic to extract and format the data
                    return resultSet.getString("profileData");
                } else {
                    return null; // Profile not found
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., log the error, return a default value, or rethrow
            e.printStackTrace(); // For demonstration purposes, replace with proper error handling
            return null;
        }
    }

    public static void createProfile(UUID uuid, String table, String profileData) {
        String query = "INSERT INTO " + table + " (uuid, amount) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, uuid);
            statement.setString(2, profileData);

            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., log the error, return a boolean indicating success/failure
            System.err.println("Error creating profile: " + e.getMessage()); // Replace with proper logging
            // Consider throwing a custom exception or returning a specific error code
        }
    }
}