package util;

import routes.ECommerceAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUser {
    public static boolean deleteUser(String userId) {
        try {
            ECommerceAPI api = new ECommerceAPI();
            Connection connection = api.koneksi();
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            int rowsDeleted = statement.executeUpdate();
            statement.close();
            connection.close();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
