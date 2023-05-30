package util;

import main.ECommerceAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteReview {
    public static void deleteReview(int orderId) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        // Buat koneksi ke database
        Connection conn = api.koneksi();

        // Buat pernyataan SQL untuk menghapus review
        String query = "DELETE FROM reviews WHERE order_id = ?";

        // Persiapkan pernyataan SQL
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, orderId);

        // Eksekusi pernyataan SQL untuk menghapus review
        stmt.executeUpdate();

        // Tutup koneksi dan sumber daya
        stmt.close();
        conn.close();
    }
}
