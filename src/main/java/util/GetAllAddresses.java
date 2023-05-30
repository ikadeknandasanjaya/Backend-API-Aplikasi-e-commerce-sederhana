package util;

import routes.ECommerceAPI;
import model.Address;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetAllAddresses {
    public static List<Address> getAllAddresses() throws SQLException {
        List<Address> addresses = new ArrayList<>();
        ECommerceAPI api = new ECommerceAPI();
        Connection conn = api.koneksi();
        String query = "SELECT * FROM addresses";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String users = String.valueOf(rs.getInt("users"));
            String type = rs.getString("type");
            String line1 = rs.getString("line1");
            String line2 = rs.getString("line2");
            String city = rs.getString("city");
            String province = rs.getString("province");
            String postcode = rs.getString("postcode");

            Address address = new Address(users, type, line1, line2, city, province, postcode);
            addresses.add(address);
        }
        rs.close();
        stmt.close();
        conn.close();

        return addresses;
    }
}
