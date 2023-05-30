import main.ECommerceAPI;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        ECommerceAPI api = new ECommerceAPI();
        api.mulaiServer();
    }
}

