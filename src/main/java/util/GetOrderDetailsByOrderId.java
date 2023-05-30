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
        // membuat objek dari kelas main.ECommerceAPI untuk mengambil metode didalamnya
        ECommerceAPI api = new ECommerceAPI();

        // query sql untuk mendapatkan orderid
        String sql = "SELECT * FROM order_details WHERE order_id = ?";

        // mencoba koneksikan ke database
        try (Connection connection = api.koneksi();
             // menggunakan PreparedStatement untuk eksekusi kueri sql sebelumnya dan memasukan orderId
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // set paarameter pertama dengan orderId dengan set.String
            statement.setString(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int orderID = resultSet.getInt("order_id");
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    int price = resultSet.getInt("price");

                    //stored data yang ditangkap dari database dan tambahkan ke arraylist
                    OrderDetail orderDetail = new OrderDetail(orderId, productId, quantity, price);
                    orderDetails.add(orderDetail);
                }
            }
            // catch untuk menangkap error saat koneksi SQL
            catch (SQLException e) {
                // membetikan RTE exception jika error
                throw new RuntimeException(e);
            }
        }
        return orderDetails;
    }
}
