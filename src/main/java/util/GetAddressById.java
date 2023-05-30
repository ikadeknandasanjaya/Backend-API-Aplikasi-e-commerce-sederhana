package util;

import routes.ECommerceAPI;
import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class GetAddressById {
        public static Address getAddressById(String addressId) throws SQLException {
            ECommerceAPI api = new ECommerceAPI();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            Address address = null;

            Connection connection = null;
            try {
                connection = api.koneksi();
                String query = "SELECT * FROM addresses WHERE users = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, addressId);
                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String users = resultSet.getString("users");
                    String type = resultSet.getString("type");
                    String line1 = resultSet.getString("line1");
                    String line2 = resultSet.getString("line2");
                    String city = resultSet.getString("city");
                    String province = resultSet.getString("province");
                    String postcode = resultSet.getString("postcode");

                    address = new Address(users, type, line1, line2, city, province, postcode);
                    address.setUsers(Integer.parseInt(addressId));
                }
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }

            return address;
        }
    }

