package util;


import main.ECommerceAPI;
import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAddress {
    public static boolean updateAddress(Address address) {
        String query = "UPDATE addresses SET users = ?, TYPE = ?, line1 = ?, line2 = ?, city = ?, province = ?, postcode = ? WHERE id = ?";
        ECommerceAPI api = new ECommerceAPI();

        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, address.getUsers());
            statement.setString(2, address.getType());
            statement.setString(3, address.getLine1());
            statement.setString(4, address.getLine2());
            statement.setString(5, address.getCity());
            statement.setString(6, address.getProvince());
            statement.setString(7, address.getPostcode());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
