package util;

import main.ECommerceAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAddress {
    public static void deleteAddress(String addressId) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        String query = "DELETE FROM addresses WHERE users = ?";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, addressId);

            statement.executeUpdate();
        }
    }
}