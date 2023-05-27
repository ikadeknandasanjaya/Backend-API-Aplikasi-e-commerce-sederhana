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
                List<Order> orders = getUserOrders(userId);

                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();

                JsonArray ordersJson = new JsonArray();
                for (Order order : orders) {
                    JsonObject orderJson = gson.toJsonTree(order, Order.class).getAsJsonObject();

                    List<Product> userProducts = getUserProducts(order.getBuyer());
                    JsonArray productsJson = new JsonArray();
                    for (Product product : userProducts) {
                        JsonObject productJson = gson.toJsonTree(product, Product.class).getAsJsonObject();
                        productsJson.add(productJson);
                    }
                    orderJson.add("products", productsJson);
                    ordersJson.add(orderJson);
                }

                String response = gson.toJson(ordersJson);
                sendResponse(exchange, response, 200);
            } catch (SQLException e) {
                e.printStackTrace();
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