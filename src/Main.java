import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:sqlite:/D:\\sqlite\\apiecommerce.db";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            String sql = "SELECT * FROM users";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                String email = result.getString("email");
                String phone_number = result.getString("phone_number");
                String type = result.getString("type");

                System.out.println(first_name + " " + last_name + " " + email + " " + phone_number + " " + type);
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to SQL Database");
            e.printStackTrace();
        }
    }
}