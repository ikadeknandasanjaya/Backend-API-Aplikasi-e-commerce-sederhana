package main.java.util;

import main.java.ECommerceAPI;
import main.java.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetUserOrder {
    public static List<Order> getOrder(String orderId) throws SQLException {
        List<Order> orders = new ArrayList<>();

        // Query untuk mendapatkan data pesanan berdasarkan ID pembeli
        String query = "SELECT * FROM orders WHERE buyer = ?";
        ECommerceAPI api = new ECommerceAPI();
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int buyer = resultSet.getInt("buyer");
                    String note = resultSet.getString("note");
                    double total = resultSet.getDouble("total");
                    double discount = resultSet.getDouble("discount");
                    boolean isPaid = resultSet.getBoolean("is_paid");
                    Order order = new Order(id, buyer, note, total, discount, isPaid);
                    orders.add(order);
                }
            }
        }
        return orders;
    }
}