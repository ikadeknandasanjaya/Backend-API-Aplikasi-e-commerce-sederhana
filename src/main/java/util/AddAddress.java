package util;

import main.ECommerceAPI;
import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddAddress {
    public static boolean addAddress(Address address) throws SQLException {
        ECommerceAPI api = new ECommerceAPI();
        try (Connection connection = api.koneksi();) {
            String query = "INSERT INTO addresses (type, line1, line2, city, province, postcode) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, address.getType());
            statement.setString(2, address.getLine1());
            statement.setString(3, address.getLine2());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getProvince());
            statement.setString(6, address.getPostcode());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
