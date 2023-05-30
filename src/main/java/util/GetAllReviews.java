package util;


import main.ECommerceAPI;
import model.Review;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetAllReviews {
    public static List<Review> getAllReviews() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        ECommerceAPI api = new ECommerceAPI();
        // Buat koneksi ke database
        Connection conn = api.koneksi();

        // Buat pernyataan SQL untuk mendapatkan semua reviews
        String query = "SELECT * FROM reviews";

        // Eksekusi query dan dapatkan hasilnya
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Iterasi melalui hasil dan tambahkan review ke daftar
        while (rs.next()) {
            String orderId = rs.getString("order_id");
            int star = rs.getInt("star");
            String description = rs.getString("description");

            Review review = new Review(orderId, star, description);
            reviews.add(review);
        }

        // Tutup koneksi dan sumber daya
        rs.close();
        stmt.close();
        conn.close();

        return reviews;
    }
}
