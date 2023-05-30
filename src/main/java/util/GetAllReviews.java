package util;


import routes.ECommerceAPI;
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
        Connection conn = api.koneksi();
        String query = "SELECT * FROM reviews";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String orderId = rs.getString("order_id");
            int star = rs.getInt("star");
            String description = rs.getString("description");

            Review review = new Review(orderId, star, description);
            reviews.add(review);
        }
        rs.close();
        stmt.close();
        conn.close();

        return reviews;
    }
}
