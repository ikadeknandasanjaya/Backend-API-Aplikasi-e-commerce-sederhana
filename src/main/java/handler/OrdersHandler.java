package main.java.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.Order;
import main.java.model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.GetUserOrder.getUserOrders;
import static main.java.util.GetUserProducts.getUserProducts;

public class OrdersHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String userId = null;

        // Parsing parameter userId dari query string
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
                // Mengambil data pesanan dari database untuk pengguna dengan ID tertentu
                List<Order> orders = getUserOrders(userId);

                // Mengubah data pesanan menjadi format JSON

                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();

                JsonArray ordersJson = new JsonArray();
                for (Order order : orders) {
                    JsonObject orderJson = gson.toJsonTree(order, Order.class).getAsJsonObject();

                    // Mengambil produk yang dimiliki oleh pengguna berdasarkan ID pengguna
                    List<Product> userProducts = getUserProducts(order.getBuyer());
                    JsonArray productsJson = new JsonArray();
                    for (Product product : userProducts) {
                        JsonObject productJson = gson.toJsonTree(product, Product.class).getAsJsonObject();
                        productsJson.add(productJson);
                    }

                    // Menambahkan data produk ke dalam objek pesanan
                    orderJson.add("products", productsJson);
                    ordersJson.add(orderJson);
                }

                String response = gson.toJson(ordersJson);
                // Mengirim respon dengan data pesanan dan produk dalam format JSON
                sendResponse(exchange, ordersJson.toString(), 200);
            } catch (SQLException e) {
                e.printStackTrace();
                // Mengirim respon error jika terjadi kesalahan
                String errorResponse = "Terjadi kesalahan dalam mengambil data pesanan";
                sendResponse(exchange, errorResponse, 500);
            }
        } else {
            // Parameter userId tidak ditemukan
            String errorResponse = "Parameter userId tidak ditemukan";
            sendResponse(exchange, errorResponse, 400);
        }
    }
}