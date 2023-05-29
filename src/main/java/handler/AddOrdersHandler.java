package main.java.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.ECommerceAPI;
import main.java.model.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static main.java.util.AddOrder.addOrder;

public class AddOrdersHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            try {
                // Set request header Content-Type
                exchange.getResponseHeaders().set("Content-Type", "application/json");

                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    requestBody.append(line);
                }
                br.close();

                Gson gson = new Gson();
                Order newOrder = gson.fromJson(requestBody.toString(), Order.class);

                boolean success = addOrder(newOrder);

                if (success) {
                    String response = "Pesanan berhasil ditambahkan";
                    ECommerceAPI.sendResponse(exchange, response, 200);
                } else {
                    String response = "Gagal menambahkan pesanan";
                    ECommerceAPI.sendResponse(exchange, response, 500);
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                String response = "Terjadi kesalahan dalam memproses permintaan";
                ECommerceAPI.sendResponse(exchange, response, 500);
            }
        } else {
            String response = "Metode permintaan tidak diizinkan";
            ECommerceAPI.sendResponse(exchange, response, 405);
        }
    }
}