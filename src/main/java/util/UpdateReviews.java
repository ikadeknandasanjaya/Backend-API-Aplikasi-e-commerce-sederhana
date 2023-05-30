package util;

import routes.ECommerceAPI;
import model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateReviews {
    public static void updateReview(Review review) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        Connection conn = api.koneksi();
        String query = "UPDATE reviews SET star = ?, description = ? WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, review.getStar());
        stmt.setString(2, review.getDescription());
        stmt.setInt(3, review.getOrderId());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
