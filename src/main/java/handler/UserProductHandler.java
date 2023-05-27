package main.java.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.GetUserProducts.getUserProducts;

public class UserProductHandler implements HttpHandler {
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
                // Mengambil produk yang dimiliki oleh pengguna dengan ID tertentu
                List<Product> products = getUserProducts(userId);

                // Mengubah data produk menjadi format JSON
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                JsonArray jsonArray = new JsonArray();
                for (Product product : products) {
                    JsonElement userJson = gson.toJsonTree(product);
                    jsonArray.add(userJson);
                }
                String respon = gson.toJson(jsonArray);

                sendResponse(exchange, respon, 200);
            } catch (SQLException e) {
                e.printStackTrace();
                String errorResponse = "Terjadi kesalahan dalam mengambil data produk";
                sendResponse(exchange, errorResponse, 500);
            }
        } else {
            String errorResponse = "Parameter userId tidak ditemukan";
            sendResponse(exchange, errorResponse, 400);
        }
    }
}