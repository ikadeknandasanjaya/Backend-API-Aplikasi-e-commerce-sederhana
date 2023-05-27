package main.java.util;

import main.java.ECommerceAPI;
import main.java.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUser {
    public static boolean addUser(User user) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        String query = "INSERT INTO users (id, first_name, last_name, email, phone_number, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getFirst_name());
            statement.setString(3, user.getLast_name());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhone_number());
            statement.setString(6, user.getType());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
