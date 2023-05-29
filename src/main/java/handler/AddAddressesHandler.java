package main.java.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.Address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.AddAddress.addAddress;

public class AddAddressesHandler implements HttpHandler {
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
                Address newAddress = gson.fromJson(requestBody.toString(), Address.class);

                boolean success = addAddress(newAddress);

                if (success) {
                    String response = "Alamat berhasil ditambahkan";
                    sendResponse(exchange, response, 200);
                } else {
                    String response = "Gagal menambahkan alamat";
                    sendResponse(exchange, response, 500);
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                String response = "Terjadi kesalahan dalam memproses permintaan";
                sendResponse(exchange, response, 500);
            }
        } else {
            String response = "Metode permintaan tidak diizinkan";
            sendResponse(exchange, response, 405);
        }
    }
}