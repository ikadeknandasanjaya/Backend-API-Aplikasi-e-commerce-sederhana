package util;


import routes.ECommerceAPI;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProduct {
    public static boolean updateProduct(Product updatedProduct) {
        try {
            ECommerceAPI api = new ECommerceAPI();
            Connection connection = api.koneksi();
            String query = "UPDATE products SET seller = ?, title = ?, description = ?, price = ?, stock = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, updatedProduct.getSeller());
            statement.setString(2, updatedProduct.getTitle());
            statement.setString(3, updatedProduct.getDescription());
            statement.setString(4, updatedProduct.getPrice());
            statement.setInt(5, updatedProduct.getStock());
            statement.setInt(6, updatedProduct.getId());
            int rowsUpdated = statement.executeUpdate();
            statement.close();
            connection.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
