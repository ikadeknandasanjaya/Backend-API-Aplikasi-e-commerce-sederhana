package util;

import routes.ECommerceAPI;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilterProducts {
    public static List<Product> filterProducts(List<Product> products, String field, String cond, int val) throws SQLException {
        List<Product> filteredProducts = new ArrayList<>();
        String query = "SELECT * FROM products WHERE " + field + " >= ?";
        ECommerceAPI api = new ECommerceAPI();
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, val);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String seller = resultSet.getString("seller");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                Product product = new Product(id, seller, title, description, price, stock);
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }
}
