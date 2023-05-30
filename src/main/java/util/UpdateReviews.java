package util;

import main.ECommerceAPI;
import model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateReviews {
    public static void updateReview(Review review) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        // Buat koneksi ke database
        Connection conn = api.koneksi();

        // Buat pernyataan SQL untuk memperbarui review
        String query = "UPDATE reviews SET star = ?, description = ? WHERE order_id = ?";

        // Persiapkan pernyataan SQL
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, review.getStar());
        stmt.setString(2, review.getDescription());
        stmt.setInt(3, review.getOrderId());

        // Eksekusi pernyataan SQL untuk memperbarui review
        stmt.executeUpdate();

        // Tutup koneksi dan sumber daya
        stmt.close();
        conn.close();
    }
}
