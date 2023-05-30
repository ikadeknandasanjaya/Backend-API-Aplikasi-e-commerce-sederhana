package util;


import routes.ECommerceAPI;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateOrder {
    public static boolean updateOrder(Order order) {
        try {
            ECommerceAPI api = new ECommerceAPI();
            Connection connection = api.koneksi();
            String query = "UPDATE orders SET buyer = ?, note = ?, total = ?, discount = ?, is_paid = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(order.getBuyer()));
            statement.setString(2, order.getNote());
            statement.setDouble(3, order.getTotal());
            statement.setDouble(4, order.getDiscount());
            statement.setBoolean(5, order.isPaid());
            statement.setInt(6, order.getId());
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
