package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.OrderDetail;
import util.AddOrderDetail;
import util.GetAllOrderDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;


public class DetailsAllHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("GET")) {
            handleGetAllDetails(exchange);
        } else if (method.equalsIgnoreCase("POST")) {
            handleAddDetail(exchange);
        } else {
            String errorResponse = "Metode HTTP yang tidak valid";
            sendResponse(exchange, errorResponse, 405);
        }
    }

    private void handleGetAllDetails(HttpExchange exchange) throws IOException {
        try {
            List<OrderDetail> orderDetails = GetAllOrderDetails.getAllOrderDetails();

            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();
            String response = gson.toJson(orderDetails);

            sendResponse(exchange, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam mengambil data detail pesanan";
            sendResponse(exchange, errorResponse, 500);
        }
    }

    private void handleAddDetail(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        Gson gson = new GsonBuilder().create();
        OrderDetail orderDetail = gson.fromJson(requestBody.toString(), OrderDetail.class);

        try {
            AddOrderDetail.addOrderDetail(orderDetail);

            String response = "Detail pesanan berhasil ditambahkan";
            sendResponse(exchange, response, 201);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam menambahkan detail pesanan";
            sendResponse(exchange, errorResponse, 500);
        }
    }
}
