package main.java.util;

import main.java.ECommerceAPI;
import main.java.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddReview {
    public static boolean addReview(Review review) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        String query = "INSERT INTO reviews (id_order, star, description) VALUES (?, ?, ?)";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, review.getOrderId());
            statement.setInt(2, review.getStar());
            statement.setString(3, review.getDescription());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
