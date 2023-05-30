package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Address;
import util.GetAddressById;
import util.GetUserAddresses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;
import static util.AddAddress.addAddress;
import static util.DeleteAddress.deleteAddress;


public class AddressesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/address")) {
            if (exchange.getRequestMethod().equals("GET")) {
                handleGetAllAddresses(exchange);
            } else if (exchange.getRequestMethod().equals("POST")) {
                handleCreateAddress(exchange);
            } else {
                String errorResponse = "Metode HTTP yang diminta tidak didukung";
                sendResponse(exchange, errorResponse, 405);
            }
        } else if (path.matches("/address/\\d+")) {
            String[] pathSegments = path.split("/");
            String addressId = pathSegments[2];
            if (exchange.getRequestMethod().equals("GET")) {
                handleGetAddressById(exchange, addressId);
            } else if (exchange.getRequestMethod().equals("DELETE")) {
                handleDeleteAddress(exchange, addressId);
            } else {
                String errorResponse = "Metode HTTP yang diminta tidak didukung";
                sendResponse(exchange, errorResponse, 405);
            }
        } else {
            String errorResponse = "Endpoint tidak valid";
            sendResponse(exchange, errorResponse, 404);
        }
    }

    private void handleGetAllAddresses(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String userId = null;

        String[] queryParams = query.split("&");
        for (String param : queryParams) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals("userId")) {
                userId = keyValue[1];
                break;
            }
        }

        if (userId != null) {
            try {
                List<Address> addresses = GetUserAddresses.getUserAddresses(userId);
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
        } else {
            String errorResponse = "Parameter userId tidak ditemukan";
            sendResponse(exchange, errorResponse, 400);
        }
    }

    private void handleGetAddressById(HttpExchange exchange, String addressId) throws IOException {
        try {
            Address address = GetAddressById.getAddressById(addressId);

            if (address != null) {
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                String response = gson.toJson(address);

                sendResponse(exchange, response, 200);
            } else {
                String errorResponse = "Alamat dengan ID yang diberikan tidak ditemukan";
                sendResponse(exchange, errorResponse, 404);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam mengambil data alamat";
            sendResponse(exchange, errorResponse, 500);
        }
    }

    private void handleCreateAddress(HttpExchange exchange) throws IOException {
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
            addAddress(address);

            String response = "Alamat berhasil dibuat";
            sendResponse(exchange, response, 201);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam membuat alamat";
            sendResponse(exchange, errorResponse, 500);
        }
    }

    private void handleDeleteAddress(HttpExchange exchange, String addressId) throws IOException {
        try {
            deleteAddress(addressId);

            String response = "Alamat berhasil dihapus";
            sendResponse(exchange, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam menghapus alamat";
            sendResponse(exchange, errorResponse, 500);
        }
    }
}
