package util;


import routes.ECommerceAPI;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetAllOrderDetails {
    public static List<OrderDetail> getAllOrderDetails() throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        ECommerceAPI api = new ECommerceAPI();
        Connection conn = api.koneksi();
        String query = "SELECT * FROM order_details";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String orderId = String.valueOf(rs.getInt("order_id"));
            int productId = rs.getInt("product_id");
            int quantity = rs.getInt("quantity");
            int price = rs.getInt("price");

            OrderDetail orderDetail = new OrderDetail(orderId, productId, quantity, price);
            orderDetails.add(orderDetail);
        }
        rs.close();
        stmt.close();
        conn.close();

        return orderDetails;
    }
}