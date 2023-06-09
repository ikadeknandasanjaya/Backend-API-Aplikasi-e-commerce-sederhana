package routes;

import com.sun.net.httpserver.HttpServer;
import handler.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ECommerceAPI {
    //       Inisialisasi DB di sqlite
    private static final String basisData = "jdbc:sqlite:D:/Kadek/Semester 2/PBO/tugas2/Backend-API-Aplikasi-e-commerce-sederhana/database/apiecommerce.db";


    //       Koneksi ke DB di sqlite
    public static Connection koneksi() throws SQLException {
        return DriverManager.getConnection(basisData);
    }

    //      Method untuk memulai server
    public void mulaiServer() throws IOException {
//            Inisialisasi port dengan NIM saya diakhir
        int port = 8075;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
//            Print listening to port untuk menandakan server sudah dimulai
        System.out.println("Listening to port " + port + " ...");

        // endpoint untuk routes API
        server.createContext("/users", new UserAllHandler());
        server.createContext("/users/", new UserIDHandler());
        server.createContext("/orders/", new OrderHandler());
        server.createContext("/products", new ProductsAllHandler());
        server.createContext("/products/", new ProductsHandler());
        server.createContext("/address", new AddressesAllHandler());
        server.createContext("/address/", new AddressesHandler());
        server.createContext("/reviews", new ReviewsAllHandler());
        server.createContext("/reviews/", new ReviewsHandler());
        server.createContext("/orders/details", new DetailsAllHandler());
        server.createContext("/orders/details/", new DetailsHandler());

        server.setExecutor(null);
        server.start();
    }
}




