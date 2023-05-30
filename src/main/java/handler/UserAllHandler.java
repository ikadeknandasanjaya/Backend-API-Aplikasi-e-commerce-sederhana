package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.User;
import util.AddUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;
import static util.GetAllUser.getAllUsers;
import static util.GetBuyerUsers.getBuyerUsers;
import static util.GetSellerUsers.getSellerUsers;

public class UserAllHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();

            if ("/users".equals(path) && query != null) {
                if (query.contains("type=buyer")) {
                    try {
                        List<User> buyers = getBuyerUsers();
                        Gson gson = new GsonBuilder()
                                .disableHtmlEscaping()
                                .setPrettyPrinting()
                                .serializeNulls()
                                .create();
                        JsonArray jsonArray = new JsonArray();
                        for (User buyer : buyers) {
                            JsonElement buyerJson = gson.toJsonTree(buyer);
                            jsonArray.add(buyerJson);
                        }
                        String response = gson.toJson(jsonArray);
                        sendResponse(exchange, response, 200);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String responseError = "Terjadi kesalahan dalam mengambil data pengguna buyer";
                        sendResponse(exchange, responseError, 500);
                    }
                } else if (query.contains("type=seller")) {
                    try {
                        List<User> sellers = getSellerUsers();
                        Gson gson = new GsonBuilder()
                                .disableHtmlEscaping()
                                .setPrettyPrinting()
                                .serializeNulls()
                                .create();
                        JsonArray jsonArray = new JsonArray();
                        for (User seller : sellers) {
                            JsonElement sellerJson = gson.toJsonTree(seller);
                            jsonArray.add(sellerJson);
                        }
                        String response = gson.toJson(jsonArray);
                        sendResponse(exchange, response, 200);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String responseError = "Terjadi kesalahan dalam mengambil data pengguna seller";
                        sendResponse(exchange, responseError, 500);
                    }
                }
            } else {
                try {
                    List<User> users = getAllUsers();
                    Gson gson = new GsonBuilder()
                            .disableHtmlEscaping()
                            .setPrettyPrinting()
                            .serializeNulls()
                            .create();
                    JsonArray jsonArray = new JsonArray();
                    for (User user : users) {
                        JsonElement userJson = gson.toJsonTree(user);
                        jsonArray.add(userJson);
                    }
                    String response = gson.toJson(jsonArray);
                    sendResponse(exchange, response, 200);
                } catch (SQLException e) {
                    e.printStackTrace();
                    String responseError = "Terjadi kesalahan dalam mengambil data pengguna";
                    sendResponse(exchange, responseError, 500);
                }
            }
        } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            if ("/users".equals(path)) {
                try {
                    // Read the request body and parse it as a User object
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());
                    Gson gson = new Gson();
                    User newUser = gson.fromJson(requestBody, User.class);

                    // Call the addUser method to add the new user
                    boolean added = AddUser.addUser(newUser);

                    if (added) {
                        String response = "User berhasil ditambahkan";
                        sendResponse(exchange, response, 200);
                    } else {
                        String responseError = "Gagal menambahkan user";
                        sendResponse(exchange, responseError, 500);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    String responseError = "Terjadi kesalahan dalam menambahkan pengguna";
                    sendResponse(exchange, responseError, 500);
                }
            }
        }
    }
}
