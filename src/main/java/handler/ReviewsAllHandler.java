package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Review;
import util.AddReview;
import util.GetAllReviews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;


public class ReviewsAllHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            handleGetAllReviews(exchange);
        } else if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            handleAddReview(exchange);
        } else {
            String errorResponse = "Metode HTTP yang tidak valid";
            sendResponse(exchange, errorResponse, 405);
        }
    }

    private void handleGetAllReviews(HttpExchange exchange) throws IOException {
        try {
            List<Review> reviews = GetAllReviews.getAllReviews();
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();

            String response = gson.toJson(reviews);
            sendResponse(exchange, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam mengambil data reviews";
            sendResponse(exchange, errorResponse, 500);
        }
    }

    private void handleAddReview(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        Gson gson = new GsonBuilder().create();
        Review review = gson.fromJson(requestBody.toString(), Review.class);

        try {
            AddReview.addReview(review);

            String response = "Review berhasil ditambahkan";
            sendResponse(exchange, response, 201);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam menambahkan review";
            sendResponse(exchange, errorResponse, 500);
        }
    }
}