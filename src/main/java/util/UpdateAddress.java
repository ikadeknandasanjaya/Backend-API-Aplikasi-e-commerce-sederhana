package util;

import main.ECommerceAPI;
import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAddress {
    public static boolean updateAddress(String addressId, Address updatedAddress) {
        String query = "UPDATE addresses SET users = ?, type = ?, line1 = ?, line2 = ?, city = ?, province = ?, postcode = ? WHERE users = ?";
        ECommerceAPI api = new ECommerceAPI();

        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, updatedAddress.getUsers());
            statement.setString(2, updatedAddress.getType());
            statement.setString(3, updatedAddress.getLine1());
            statement.setString(4, updatedAddress.getLine2());
            statement.setString(5, updatedAddress.getCity());
            statement.setString(6, updatedAddress.getProvince());
            statement.setString(7, updatedAddress.getPostcode());
            statement.setString(8, addressId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}