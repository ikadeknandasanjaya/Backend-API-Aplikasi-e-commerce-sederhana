package main.java.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.ECommerceAPI;
import main.java.model.Review;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static main.java.util.AddReview.addReview;

public class AddReviewsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            try {
                exchange.getResponseHeaders().set("Content-Type", "application/json");

                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    requestBody.append(line);
                }
                br.close();

                Gson gson = new Gson();
                Review newReview = gson.fromJson(requestBody.toString(), Review.class);

                boolean success = addReview(newReview);

                if (success) {
                    String response = "Review berhasil ditambahkan";
                    ECommerceAPI.sendResponse(exchange, response, 200);
                } else {
                    String response = "Gagal menambahkan review";
                    ECommerceAPI.sendResponse(exchange, response, 500);
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                String response = "Terjadi kesalahan dalam memproses permintaan";
                ECommerceAPI.sendResponse(exchange, response, 500);
            }
        } else {
            String response = "Metode permintaan tidak diizinkan";
            ECommerceAPI.sendResponse(exchange, response, 405);
        }
    }
}
