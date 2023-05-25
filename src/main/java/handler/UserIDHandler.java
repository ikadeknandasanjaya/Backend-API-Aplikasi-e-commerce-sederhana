package main.java.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.User;

import java.io.IOException;
import java.sql.SQLException;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.GetUserById.getUserById;

public class UserIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String userId = path.substring(path.lastIndexOf("/") + 1);

        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                // Mengambil pengguna berdasarkan ID oleh method getUserById Ngepass userID
                User user = getUserById(userId);

                if (user != null) {
                    // Menjadikan format Json
                    String dataJson = user.ubahJson();
                    // Mengirim respon
                    sendResponse(exchange, dataJson, 200);
                } else {
                    // Memberikan output 404 jika id tidak ada
                    String respon = "User dengan ID yang dicari tidak ditemukan";
                    sendResponse(exchange, respon, 404);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Mengirim respon error ke jika terjadi kesalahan
                String responError = "Terjadi kesalahan dalam mengambil data user";
                sendResponse(exchange, responError, 500);
            }
        } else if ("PUT".equals(exchange.getRequestMethod())) {

        } else if ("DELETE".equals(exchange.getRequestMethod())) {

        } else {
            // Method HTTP yang diminta tidak ada
            String respon = ("Method HTTP yang diminta tidak ada");
            sendResponse(exchange, respon, 405);
        }
    }
}