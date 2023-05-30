package util;

import routes.ECommerceAPI;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddOrderDetail {
    public static boolean addOrderDetail(OrderDetail orderDetail) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        String query = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderDetail.getOrderId());
            statement.setInt(2, orderDetail.getProductId());
            statement.setInt(3, orderDetail.getQuantity());
            statement.setInt(4, orderDetail.getPrice());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}