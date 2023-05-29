package main.java.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.AddUser.addUser;
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
                String response = gson.toJson(jsonArray);
                sendResponse(exchange, response, 200);
            } catch (SQLException e) {
                e.printStackTrace();
                String responseError = "Terjadi kesalahan dalam mengambil data pengguna";
                sendResponse(exchange, responseError, 500);
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                Gson gson = new Gson();
                User newUser = gson.fromJson(requestBody.toString(), User.class);
                boolean success = addUser(newUser);

                if (success) {
                    String response = "Data pengguna berhasil ditambahkan";
                    sendResponse(exchange, response, 200);
                } else {
                    String responseError = "Gagal menambahkan data pengguna";
                    sendResponse(exchange, responseError, 500);
                }
            } catch (IOException e) {
                e.printStackTrace();
                String responseError = "Terjadi kesalahan dalam menambahkan data pengguna";
                sendResponse(exchange, responseError, 500);
            } catch (SQLException e) {
                e.printStackTrace();
                String responseError = "Terjadi kesalahan dalam akses ke database";
                sendResponse(exchange, responseError, 500);
            }
        } else {
            String response = "Metode HTTP yang diminta tidak diizinkan";
            sendResponse(exchange, response, 405);
        }
    }
}