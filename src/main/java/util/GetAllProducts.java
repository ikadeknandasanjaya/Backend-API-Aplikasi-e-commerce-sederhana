package main.java.util;

import main.java.ECommerceAPI;
import main.java.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllProducts {
    public static List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        // Query untuk mendapatkan semua data produk
        String query = "SELECT * FROM products";
        ECommerceAPI api = new ECommerceAPI();
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String seller = resultSet.getString("seller");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                Product product = new Product(id, seller, title, description, price, stock);
                products.add(product);
            }
        }
        return products;
    }
}
