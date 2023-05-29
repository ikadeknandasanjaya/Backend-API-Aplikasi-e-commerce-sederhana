package main.java.util;

import main.java.ECommerceAPI;
import main.java.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProduct {
    public static boolean addProduct(Product product) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        String query = "INSERT INTO products (id, seller, title, description, price, stocks) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, product.getId());
            statement.setInt(2, product.getSeller());
            statement.setString(3, product.getTitle());
            statement.setString(4, product.getDescription());
            statement.setString(5, product.getPrice());
            statement.setInt(6, product.getStock());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
