package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;
import static util.GetAllProducts.getAllProducts;

public class ProductsAllHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                List<Product> products = getAllProducts();
                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();
                JsonArray jsonArray = new JsonArray();
                for (Product product : products) {
                    JsonElement productJson = gson.toJsonTree(product);
                    jsonArray.add(productJson);
                }
                String response = gson.toJson(jsonArray);
                sendResponse(exchange, response, 200);
            } catch (SQLException e) {
                e.printStackTrace();
                String responseError = "Terjadi kesalahan dalam mengambil data produk";
                sendResponse(exchange, responseError, 500);
            }
        } else {
            String response = "Metode HTTP yang diminta tidak diizinkan";
            sendResponse(exchange, response, 405);
        }
    }
}