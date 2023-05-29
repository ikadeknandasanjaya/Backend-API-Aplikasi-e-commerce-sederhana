package main.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import main.java.handler.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

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
        server.createContext("/users/addresses", new AddressesHandler());
        server.createContext("/users/seller", new SellerHandler());
        server.createContext("/users/buyer", new BuyerHandler());
        server.createContext("/users/reviews", new ReviewsHandler());
        server.createContext("/users/orders/details", new DetailsHandler());

        server.setExecutor(null);
        server.start();
    }

    //    Untuk menangani respon
    public static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}




