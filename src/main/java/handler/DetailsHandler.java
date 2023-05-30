package handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.OrderDetail;
import util.DeleteOrderDetail;
import util.UpdateOrderDetail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;
import static util.GetOrderDetailsByOrderId.getOrderDetailsByOrderId;

public class DetailsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathSegments = path.split("/");

        if (pathSegments.length < 4) {
            String errorResponse = "Parameter orderDetailId tidak ditemukan";
            sendResponse(exchange, errorResponse, 400);
            return;
        }

        String orderDetailId = pathSegments[3];

        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            handleGetOrderDetail(exchange, orderDetailId);
        } else if (exchange.getRequestMethod().equalsIgnoreCase("PUT")) {
            handleUpdateOrderDetail(exchange, orderDetailId);
        } else if (exchange.getRequestMethod().equalsIgnoreCase("DELETE")) {
            handleDeleteOrderDetail(exchange, orderDetailId);
        } else {
            String errorResponse = "Metode HTTP yang tidak valid";
            sendResponse(exchange, errorResponse, 405);
        }
    }

    private void handleGetOrderDetail(HttpExchange exchange, String orderDetailId) throws IOException {
        try {
            // Mendapatkan order detail berdasarkan ID
            List<OrderDetail> orderDetail =getOrderDetailsByOrderId(orderDetailId);

            if (orderDetail != null) {
                // Mengubah order detail menjadi format JSON
                Gson gson = new Gson();
                String response = gson.toJson(orderDetail);
                sendResponse(exchange, response, 200);
            } else {
                String errorResponse = "Order detail dengan ID yang diberikan tidak ditemukan";
                sendResponse(exchange, errorResponse, 404);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam mengambil order detail";
            sendResponse(exchange, errorResponse, 500);
        }
    }

    private void handleUpdateOrderDetail(HttpExchange exchange, String orderDetailId) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        Gson gson = new Gson();
        OrderDetail updatedOrderDetail = gson.fromJson(requestBody.toString(), OrderDetail.class);
        updatedOrderDetail.setOrderId(Integer.parseInt(orderDetailId));

        try {
            boolean success = UpdateOrderDetail.updateOrderDetail(updatedOrderDetail);

            if (success) {
                String response = "Order detail berhasil diperbarui";
                sendResponse(exchange, response, 200);
            } else {
                String errorResponse = "Gagal memperbarui order detail";
                sendResponse(exchange, errorResponse, 500);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam memperbarui order detail";
            sendResponse(exchange, errorResponse, 500);
        }
    }

    private void handleDeleteOrderDetail(HttpExchange exchange, String orderDetailId) throws IOException {
        try {
            boolean success = DeleteOrderDetail.deleteOrderDetail(Integer.parseInt(orderDetailId));

            if (success) {
                String response = "Order detail berhasil dihapus";
                sendResponse(exchange, response, 200);
            } else {
                String errorResponse = "Gagal menghapus order detail";
                sendResponse(exchange, errorResponse, 500);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam menghapus order detail";
            sendResponse(exchange, errorResponse, 500);
        }
    }
}
