package util;


import routes.ECommerceAPI;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

    public class UpdateUser {
        public static boolean updateUser(String userId, User updatedUser) {
            try {
                ECommerceAPI api = new ECommerceAPI();
                Connection connection = api.koneksi();
                String query = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone_number = ?, type = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, updatedUser.getFirst_name());
                statement.setString(2, updatedUser.getLast_name());
                statement.setString(3, updatedUser.getEmail());
                statement.setString(4, updatedUser.getPhone_number());
                statement.setString(5, updatedUser.getType());
                statement.setString(6, userId);
                int rowsUpdated = statement.executeUpdate();
                statement.close();
                connection.close();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
