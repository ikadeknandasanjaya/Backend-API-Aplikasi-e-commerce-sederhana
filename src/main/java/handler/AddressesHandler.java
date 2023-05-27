package main.java.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.Address;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.GetUserAddresses.getUserAddresses;

public class AddressesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
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
                List<Address> addresses = getUserAddresses(userId);
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                JsonArray addressesJson = new JsonArray();
                for (Address address : addresses) {
                    JsonObject addressJson = gson.toJsonTree(address).getAsJsonObject();
                    addressesJson.add(addressJson);
                }
                String response = gson.toJson(addressesJson);

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
}
