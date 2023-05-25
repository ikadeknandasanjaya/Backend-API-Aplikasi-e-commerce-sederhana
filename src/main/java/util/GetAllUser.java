package main.java.util;

import main.java.ECommerceAPI;
import main.java.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllUser {
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        ECommerceAPI api = new ECommerceAPI();
        String query = "SELECT * FROM users";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String phone_number = resultSet.getString("phone_number");
                String type = resultSet.getString("type");
                User user = new User(id, first_name, last_name, email, phone_number, type);
                users.add(user);
            }
        }

        return users;
    }
}
