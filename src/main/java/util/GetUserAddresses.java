package util;


import main.ECommerceAPI;
import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetUserAddresses {
    public static List<Address> getUserAddresses(String userId) throws SQLException {
        List<Address> addresses = new ArrayList<>();
        ECommerceAPI api = new ECommerceAPI();
        String query = "SELECT * FROM addresses WHERE users = ?";
        try (Connection connection = api.koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String type = resultSet.getString("TYPE");
                    String line1 = resultSet.getString("line1");
                    String line2 = resultSet.getString("line2");
                    String city = resultSet.getString("city");
                    String province = resultSet.getString("province");
                    String postcode = resultSet.getString("postcode");
                    Address address = new Address(userId, type, line1, line2, city, province, postcode);
                    addresses.add(address);
                }
            }
        }
        return addresses;
    }
}

