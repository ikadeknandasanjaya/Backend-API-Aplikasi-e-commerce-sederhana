package util;


import routes.ECommerceAPI;
import model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetReviewsByOrderId {
    public static List<Review> getReviewsByOrderId(String orderId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        ECommerceAPI api = new ECommerceAPI();

        String sql = "SELECT * FROM reviews WHERE order_id = ?";

        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int star = resultSet.getInt("star");
                    String description = resultSet.getString("description");
                    Review review = new Review(orderId, star, description);
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }
}
