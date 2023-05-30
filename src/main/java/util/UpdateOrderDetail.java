package util;

import main.ECommerceAPI;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateOrderDetail {
    public static boolean updateOrderDetail(OrderDetail orderDetail) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        String query = "UPDATE order_details SET product_id = ?, quantity = ?, price = ? WHERE order_id = ?";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderDetail.getProductId());
            statement.setInt(2, orderDetail.getQuantity());
            statement.setInt(3, orderDetail.getPrice());
            statement.setInt(4, orderDetail.getOrderId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
