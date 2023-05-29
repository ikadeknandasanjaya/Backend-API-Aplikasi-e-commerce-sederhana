package main.java.handler;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.*;
import main.java.util.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.DeleteUser.deleteUser;
import static main.java.util.GetUserOrder.getOrder;
import static main.java.util.UpdateUser.updateUser;

public class UserIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathSegments = path.split("/");

        if (pathSegments.length < 3) {
            String errorResponse = "Parameter userId tidak ditemukan";
            sendResponse(exchange, errorResponse, 400);
            return;
        }

        String userId = pathSegments[2];

        if (path.endsWith("/products")) {
            handleUserProducts(exchange, userId);
        } else if (path.endsWith("/orders")) {
            handleUserOrders(exchange, userId);
        } else if (path.endsWith("/reviews")) {
            handleUserReviews(exchange, userId);
        } else {
            handleUserDetails(exchange, userId);
        }
    }

    private void handleUserDetails(HttpExchange exchange, String userId) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                User user = GetUserById.getUserById(userId);
                List<Address> addresses = GetUserAddresses.getUserAddresses(userId);

                if (user != null) {
                    Gson gson = new GsonBuilder()
                            .disableHtmlEscaping()
                            .setPrettyPrinting()
                            .serializeNulls()
                            .create();

                    JsonObject userJson = gson.toJsonTree(user).getAsJsonObject();
                    JsonArray addressesJson = new JsonArray();
                    for (Address address : addresses) {
                        JsonObject addressJson = gson.toJsonTree(address).getAsJsonObject();
                        addressesJson.add(addressJson);
                    }
                    userJson.add("addresses", addressesJson);

                    String response = gson.toJson(userJson);
                    sendResponse(exchange, response, 200);
                } else {
                    String response = "User dengan ID yang dicari tidak ditemukan";
                    sendResponse(exchange, response, 404);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                String responseError = "Terjadi kesalahan dalam mengambil data user dan alamat";
                sendResponse(exchange, responseError, 500);
            }
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            Gson gson = new GsonBuilder().create();
            User updatedUser = gson.fromJson(requestBody, User.class);

            UpdateUser updateUser = new UpdateUser();
            boolean updateSuccess = updateUser(userId, updatedUser);

            String response;
            int statusCode;
            if (updateSuccess) {
                response = "Pengguna berhasil diperbarui";
                statusCode = 200;
            } else {
                response = "Gagal memperbarui pengguna";
                statusCode = 500;
            }
            sendResponse(exchange, response, statusCode);
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            boolean deleteSuccess = deleteUser(userId);
            String response;
            int statusCode;
            if (deleteSuccess) {
                response = "Pengguna berhasil dihapus";
                statusCode = 200;
            } else {
                response = "Gagal menghapus pengguna";
                statusCode = 500;
            }
            sendResponse(exchange, response, statusCode);
        } else {
            String response = "Metode HTTP yang diminta tidak diizinkan";
            sendResponse(exchange, response, 405);
        }
    }

    private void handleUserProducts(HttpExchange exchange, String userId) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                List<Product> products = GetUserProducts.getUserProducts(userId);

                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                JsonArray jsonArray = new JsonArray();
                for (Product product : products) {
                    JsonElement productJson = gson.toJsonTree(product);
                    jsonArray.add(productJson);
                }
                String response = gson.toJson(jsonArray);

                sendResponse(exchange, response, 200);
            } catch (SQLException e) {
                e.printStackTrace();
                String errorResponse = "Terjadi kesalahan dalam mengambil data produk";
                sendResponse(exchange, errorResponse, 500);
            }
        } else {
            String response = "Metode HTTP yang diminta tidak diizinkan";
            sendResponse(exchange, response, 405);
        }
    }

    private void handleUserOrders(HttpExchange exchange, String userId) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                List<Order> orders = (List<Order>) getOrder(userId);

                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                JsonArray jsonArray = new JsonArray();
                for (Order order : orders) {
                    JsonElement orderJson = gson.toJsonTree(order);
                    jsonArray.add(orderJson);
                }
                String response = gson.toJson(jsonArray);

                sendResponse(exchange, response, 200);
            } catch (SQLException e) {
                e.printStackTrace();
                String errorResponse = "Terjadi kesalahan dalam mengambil data pesanan";
                sendResponse(exchange, errorResponse, 500);
            }
        } else {
            String response = "Metode HTTP yang diminta tidak diizinkan";
            sendResponse(exchange, response, 405);
        }
    }

    private void handleUserReviews(HttpExchange exchange, String userId) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                List<Review> reviews = GetReviewsByOrderId.getReviewsByOrderId(userId);

                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                JsonArray jsonArray = new JsonArray();
                for (Review review : reviews) {
                    JsonElement reviewJson = gson.toJsonTree(review);
                    jsonArray.add(reviewJson);
                }
                String response = gson.toJson(jsonArray);

                sendResponse(exchange, response, 200);
            } catch (SQLException e) {
                e.printStackTrace();
                String errorResponse = "Terjadi kesalahan dalam mengambil data review";
                sendResponse(exchange, errorResponse, 500);
            }
        } else {
            String response = "Metode HTTP yang diminta tidak diizinkan";
            sendResponse(exchange, response, 405);
        }
    }
}
