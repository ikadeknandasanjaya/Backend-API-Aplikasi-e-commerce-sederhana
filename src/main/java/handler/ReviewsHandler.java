package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Review;
import util.DeleteReview;
import util.GetReviewsByOrderId;
import util.UpdateReviews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;


public class ReviewsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathSegments = path.split("/");

        if (pathSegments.length < 3) {
            String errorResponse = "Parameter reviewId tidak ditemukan";
            sendResponse(exchange, errorResponse, 400);
            return;
        }

        String reviewId = pathSegments[2];

        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            handleGetReviewById(exchange, reviewId);
        } else if (exchange.getRequestMethod().equalsIgnoreCase("PUT")) {
            handleUpdateReview(exchange, reviewId);
        } else if (exchange.getRequestMethod().equalsIgnoreCase("DELETE")) {
            handleDeleteReview(exchange, reviewId);
        } else {
            String errorResponse = "Metode HTTP yang tidak valid";
            sendResponse(exchange, errorResponse, 405);
        }
    }

    private void handleGetReviewById(HttpExchange exchange, String reviewId) throws IOException {
        try {
            // Mengambil data review dari database berdasarkan reviewId
            List<Review> review = GetReviewsByOrderId.getReviewsByOrderId(reviewId);

            if (review != null) {
                // Mengubah data review menjadi format JSON
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();

                String response = gson.toJson(review);
                sendResponse(exchange, response, 200);
            } else {
                String errorResponse = "Review dengan ID yang diberikan tidak ditemukan";
                sendResponse(exchange, errorResponse, 404);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam mengambil data review";
            sendResponse(exchange, errorResponse, 500);
        }
    }

    private void handleUpdateReview(HttpExchange exchange, String reviewId) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        Gson gson = new GsonBuilder().create();
        Review review = gson.fromJson(requestBody.toString(), Review.class);
        review.setOrderId(Integer.parseInt(reviewId));

        try {
            UpdateReviews.updateReview(review);

            String response = "Review berhasil diperbarui";
            sendResponse(exchange, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam memperbarui review";
            sendResponse(exchange, errorResponse, 500);
        }
    }

    private void handleDeleteReview(HttpExchange exchange, String reviewId) throws IOException {
        try {
            DeleteReview.deleteReview(Integer.parseInt(reviewId));

            String response = "Review berhasil dihapus";
            sendResponse(exchange, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
            String errorResponse = "Terjadi kesalahan dalam menghapus review";
            sendResponse(exchange, errorResponse, 500);
        }
    }
}