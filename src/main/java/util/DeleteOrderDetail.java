package util;

import routes.ECommerceAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteOrderDetail {
    public static boolean deleteOrderDetail(int orderDetailId) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        String query = "DELETE FROM order_details WHERE order_id = ?";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderDetailId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
