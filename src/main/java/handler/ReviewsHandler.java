package main.java.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.model.Review;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static main.java.ECommerceAPI.sendResponse;
import static main.java.util.GetReviewsByOrderId.getReviewsByOrderId;

public class ReviewsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            String query = exchange.getRequestURI().getQuery();
            String orderId = null;
            String[] queryParams = query.split("&");
            for (String param : queryParams) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && keyValue[0].equals("orderId")) {
                    orderId = keyValue[1];
                    break;
                }
            }

            if (orderId != null) {
                try {
                    // Mengambil data review dari database untuk order dengan ID tertentu
                    List<Review> reviews = getReviewsByOrderId(orderId);

                    // Mengubah data review menjadi format JSON
                    Gson gson = new GsonBuilder()
                            .disableHtmlEscaping()
                            .setPrettyPrinting()
                            .serializeNulls()
                            .create();

                    JsonArray reviewsJson = new JsonArray();
                    for (Review review : reviews) {
                        JsonObject reviewJson = gson.toJsonTree(review, Review.class).getAsJsonObject();
                        reviewsJson.add(reviewJson);
                    }
                    String response = gson.toJson(reviewsJson);
                    sendResponse(exchange, response, 200);
                } catch (SQLException e) {
                    e.printStackTrace();
                    String errorResponse = "Terjadi kesalahan dalam mengambil data review";
                    sendResponse(exchange, errorResponse, 500);
                }
            } else {
                String errorResponse = "Parameter orderId tidak ditemukan";
                sendResponse(exchange, errorResponse, 400);
            }
        } else {
            String errorResponse = "Metode HTTP yang tidak valid";
            sendResponse(exchange, errorResponse, 405);
        }
    }
    }
