package main.java.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.GetAllUser.getAllUsers;

public class UserAllHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
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
                String respon = gson.toJson(jsonArray);
                sendResponse(exchange, respon, 200);
            } catch (SQLException e) {
                e.printStackTrace();
                String responError = "Terjadi kesalahan dalam mengambil data pengguna";
                sendResponse(exchange, responError, 500);
            }
        } else {
            String respon = "Method HTTP yang diminta tidak diizinkan";
            sendResponse(exchange, respon, 405);
        }
    }
}