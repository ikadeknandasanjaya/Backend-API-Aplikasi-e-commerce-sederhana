package util;

import main.ECommerceAPI;
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
        // Buat koneksi ke database
        Connection conn = api.koneksi();

        // Buat pernyataan SQL untuk mendapatkan semua alamat
        String query = "SELECT * FROM addresses";

        // Eksekusi query dan dapatkan hasilnya
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Iterasi melalui hasil dan tambahkan alamat ke daftar
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

        // Tutup koneksi dan sumber daya
        rs.close();
        stmt.close();
        conn.close();

        return addresses;
    }
}
