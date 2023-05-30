package util;


import main.ECommerceAPI;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserById {
    public static User getUserById(String userId) throws SQLException {
        User user = null;
        ECommerceAPI api = new ECommerceAPI();
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection konek = api.koneksi();
             PreparedStatement param = konek.prepareStatement(sql)) {
            param.setString(1, userId);
            try (ResultSet hasil = param.executeQuery()) {
                if (hasil.next()) {
                    int id = hasil.getInt("id");
                    String first_name = hasil.getString("first_name");
                    String last_name = hasil.getString("last_name");
                    String email = hasil.getString("email");
                    String phone_number = hasil.getString("phone_number");
                    String type = hasil.getString("type");
                    user = new User(id, first_name, last_name, email, phone_number, type);
                }
            }
        }
        return user;
    }
}
