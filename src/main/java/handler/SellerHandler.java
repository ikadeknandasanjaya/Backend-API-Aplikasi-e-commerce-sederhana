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
import static main.java.util.GetUserByType.getUsersByType;

    public class SellerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    // Mengambil daftar pengguna dengan tipe "Seller"
                    List<User> users = getUsersByType("Seller");

                    if (users.isEmpty()) {
                        // Jika tidak ada pengguna dengan tipe "Seller", kirim respons dengan status 404 Not Found
                        String respon = "User dengan tipe Seller tidak ditemukan";
                        sendResponse(exchange, respon, 404);
                    } else {
                        // Mengonversi daftar pengguna menjadi JSON
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
                        // Untuk sementara tidak saya gunakan method yang ada gsonnya di kelas User karena error
                        String respon = gson.toJson(jsonArray);

                        // Mengirim respon
                        sendResponse(exchange, respon, 200);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Mengirim respon error jika terjadi kesalahan
                    String errorRespon = "Terjadi kesalahan dalam mengambil daftar pengguna";
                    sendResponse(exchange, errorRespon, 500);
                }
            } else {
                // Method HTTP yang diminta tidak ada
                String respon = "Method HTTP yang diminta tidak ada";
                sendResponse(exchange, respon, 405);
            }
        }
    }


