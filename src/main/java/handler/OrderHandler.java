package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;
import static util.AddOrder.addOrder;
import static util.DeleteOrder.deleteOrder;
import static util.GetUserOrder.getOrder;
import static util.UpdateOrder.updateOrder;

public class OrderHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            // Ambil order berdasarkan ID dan kirimkan sebagai respons
            String path = exchange.getRequestURI().getPath();
            String orderId = path.substring(path.lastIndexOf("/") + 1);

            try {
                List<Order> order = getOrder(orderId);

                if (order != null) {
                    Gson gson = new GsonBuilder()
                            .disableHtmlEscaping()
                            .setPrettyPrinting()
                            .serializeNulls()
                            .create();

                    String response = gson.toJson(order);
                    sendResponse(exchange, response, 200);
                } else {
                    // Order tidak ditemukan
                    String errorResponse = "Order tidak ditemukan";
                    sendResponse(exchange, errorResponse, 404);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                String errorResponse = "Terjadi kesalahan dalam mengambil data order";
                sendResponse(exchange, errorResponse, 500);
            }
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            // Update order berdasarkan ID
            String path = exchange.getRequestURI().getPath();
            String orderId = path.substring(path.lastIndexOf("/") + 1);

            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            Gson gson = new GsonBuilder().create();
            Order updatedOrder = gson.fromJson(requestBody, Order.class);
            updatedOrder.setId(Integer.parseInt(orderId));

            boolean updateSuccess = updateOrder(updatedOrder);

            String response;
            int statusCode;
            if (updateSuccess) {
                response = "Order berhasil diperbarui";
                statusCode = 200;
            } else {
                response = "Gagal memperbarui order";
                statusCode = 500;
            }

            sendResponse(exchange, response, statusCode);
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            // Hapus order berdasarkan ID
            String path = exchange.getRequestURI().getPath();
            String orderId = path.substring(path.lastIndexOf("/") + 1);

            boolean deleteSuccess = deleteOrder(orderId);

            String response;
            int statusCode;
            if (deleteSuccess) {
                response = "Order berhasil dihapus";
                statusCode = 200;
            } else {
                response = "Gagal menghapus order";
                statusCode = 500;
            }

            sendResponse(exchange, response, statusCode);
        } else if ("POST".equals(exchange.getRequestMethod())) {
            // Tambahkan order baru
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            Gson gson = new GsonBuilder().create();
            Order newOrder = gson.fromJson(requestBody, Order.class);

            // Panggil method addOrder untuk menambahkan order ke database
            boolean addSuccess = false;
            try {
                addSuccess = addOrder(newOrder);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String response;
            int statusCode;
            if (addSuccess) {
                response = "Order berhasil ditambahkan";
                statusCode = 200;
            } else {
                response = "Gagal menambahkan order";
                statusCode = 500;
            }

            sendResponse(exchange, response, statusCode);
        } else {
            String response = "Metode HTTP yang diminta tidak diizinkan";
            sendResponse(exchange, response, 405);
        }
    }
}