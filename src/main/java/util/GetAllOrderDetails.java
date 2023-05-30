package util;


import main.ECommerceAPI;
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

        // Buat koneksi ke database
        Connection conn = api.koneksi();

        // Buat pernyataan SQL untuk mendapatkan semua detail pesanan
        String query = "SELECT * FROM order_details";

        // Eksekusi query dan dapatkan hasilnya
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Iterasi melalui hasil dan tambahkan detail pesanan ke daftar
        while (rs.next()) {
            String orderId = String.valueOf(rs.getInt("order_id"));
            int productId = rs.getInt("product_id");
            int quantity = rs.getInt("quantity");
            int price = rs.getInt("price");

            OrderDetail orderDetail = new OrderDetail(orderId, productId, quantity, price);
            orderDetails.add(orderDetail);
        }

        // Tutup koneksi dan sumber daya
        rs.close();
        stmt.close();
        conn.close();

        return orderDetails;
    }
}