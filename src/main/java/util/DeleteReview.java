package util;

import main.ECommerceAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteReview {
    public static void deleteReview(int orderId) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        Connection conn = api.koneksi();
        String query = "DELETE FROM reviews WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, orderId);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
