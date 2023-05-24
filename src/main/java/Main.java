package main.java;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        ECommerceAPI api = new ECommerceAPI();
        api.mulaiServer();
    }
}

