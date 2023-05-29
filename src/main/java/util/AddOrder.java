package main.java.util;

import main.java.ECommerceAPI;
import main.java.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddOrder {
    public static boolean addOrder(Order order) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        String query = "INSERT INTO orders (id, buyer, note, total, discount, is_paid) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getId());
            statement.setString(2, String.valueOf(order.getBuyer()));
            statement.setString(3, order.getNote());
            statement.setDouble(4, order.getTotal());
            statement.setDouble(5, order.getDiscount());
            statement.setBoolean(6, order.isPaid());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
