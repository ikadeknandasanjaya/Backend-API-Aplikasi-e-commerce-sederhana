package main.java.util;

import main.java.ECommerceAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteOrder {
    public static boolean deleteOrder(String orderId) {
        try {
            ECommerceAPI api = new ECommerceAPI();
            Connection connection = api.koneksi();
            String query = "DELETE FROM orders WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(orderId));
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
