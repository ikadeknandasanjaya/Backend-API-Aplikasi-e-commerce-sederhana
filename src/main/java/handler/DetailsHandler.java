package main.java.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.OrderDetail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.GetOrderDetailsByOrderId.getOrderDetailsByOrderId;

public class DetailsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String orderId = null;

        // Mengambil parameter untuk http://localhost:8075/users/orders/details?orderId=1 dengan memisahkan setiap value jika lebih. Ex : param1=value1&param2=value2
        String[] queryParams = query.split("&");
        // Mengambil orderID dengan mencari di parameter orderId=1
        for (String param : queryParams) {
            //pisahkan untuk =
            String[] keyValue = param.split("=");
            // cek untuk orderId dan 1 sudah masuk dan untuk index 0 sudah orderId
            if (keyValue.length == 2 && keyValue[0].equals("orderId")) {
                // Jika benar stored index kedua [1] ke orderId yang merupakan id ordernya
                orderId = keyValue[1];
                break;
            }
        }

        if (orderId != null) {
            try {
                List<OrderDetail> orderDetails = getOrderDetailsByOrderId(orderId);
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();

                JsonArray orderDetailsJson = new JsonArray();
                for (OrderDetail orderDetail : orderDetails) {
                    JsonObject orderDetailJson = gson.toJsonTree(orderDetail, OrderDetail.class).getAsJsonObject();
                    orderDetailsJson.add(orderDetailJson);
                }

                String response = gson.toJson(orderDetailsJson);
                sendResponse(exchange, response, 200);
            } catch (SQLException e) {
                e.printStackTrace();
                String errorResponse = "Terjadi kesalahan dalam mengambil data detail order";
                sendResponse(exchange, errorResponse, 500);
            }
        } else {
            String errorResponse = "Parameter orderId tidak ditemukan";
            sendResponse(exchange, errorResponse, 400);
        }
    }
}
