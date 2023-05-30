package util;


import main.ECommerceAPI;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetOrderDetailsByOrderId {

    public static List<OrderDetail> getOrderDetailsByOrderId(String orderId) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        ECommerceAPI api = new ECommerceAPI();
        String sql = "SELECT * FROM order_details WHERE order_id = ?";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    int price = resultSet.getInt("price");
                    OrderDetail orderDetail = new OrderDetail(orderId, productId, quantity, price);
                    orderDetails.add(orderDetail);
                }
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return orderDetails;
    }
}
