package util;

import main.ECommerceAPI;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDetail {
    public static boolean addDetail(OrderDetail detail) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        String query = "INSERT INTO detail (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, detail.getOrderId());
            statement.setInt(2, detail.getProductId());
            statement.setInt(3, detail.getQuantity());
            statement.setInt(4, detail.getPrice());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
