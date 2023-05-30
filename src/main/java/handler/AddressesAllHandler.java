package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Address;
import util.AddAddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;
import static util.GetAllAddresses.getAllAddresses;

public class AddressesAllHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if (method.equals("GET")) {
            if (path.equals("/address")) {
                handleGetAllAddresses(exchange);
            } else {
                String errorResponse = "Endpoint tidak valid";
                sendResponse(exchange, errorResponse, 404);
            }
        } else if (method.equals("POST")) {
            if (path.equals("/address")) {
                handleAddAddress(exchange);
            } else {
                String errorResponse = "Endpoint tidak valid";
                sendResponse(exchange, errorResponse, 404);
            }
        } else {
            String errorResponse = "Metode HTTP tidak didukung";
            sendResponse(exchange, errorResponse, 405);
        }
    }

    private void handleGetAllAddresses(HttpExchange exchange) throws IOException {
        // Code untuk mengambil semua alamat pengguna
        try {
            List<Address> addresses = getAllAddresses();
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();
            String response = gson.toJson(addresses);

            sendResponse(exchange, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam mengambil data alamat";
            sendResponse(exchange, errorResponse, 500);
        }
    }

    private void handleAddAddress(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        Gson gson = new GsonBuilder().create();
        Address address = gson.fromJson(requestBody.toString(), Address.class);

        try {
            // Code untuk menambahkan alamat baru ke database
            AddAddress.addAddress(address);

            String response = "Alamat berhasil ditambahkan";
            sendResponse(exchange, response, 201);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam menambahkan alamat";
            sendResponse(exchange, errorResponse, 500);
        }
    }
}
