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
import static util.FilterProducts.filterProducts;
import static util.GetAllProducts.getAllProducts;

public class ProductsAllHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String query = exchange.getRequestURI().getQuery();
            if (query != null) {
                String[] queryParams = query.split("&");
                String field = null;
                String cond = null;
                String val = null;
                for (String param : queryParams) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0];
                        String value = keyValue[1];
                        if (key.equals("field")) {
                            field = value;
                        } else if (key.equals("cond")) {
                            cond = value;
                        } else if (key.equals("val")) {
                            val = value;
                        }
                    }
                }
                if (field != null && cond != null && val != null) {
                    try {
                        List<Product> products = getAllProducts();
                        List<Product> filteredProducts = filterProducts(products, field, cond, Integer.parseInt(val));

                        Gson gson = new GsonBuilder()
                                .disableHtmlEscaping()
                                .setPrettyPrinting()
                                .serializeNulls()
                                .create();
                        JsonArray jsonArray = new JsonArray();
                        for (Product product : filteredProducts) {
                            JsonElement productJson = gson.toJsonTree(product);
                            jsonArray.add(productJson);
                        }
                        String response = gson.toJson(jsonArray);
                        sendResponse(exchange, response, 200);
                        return;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String responseError = "Terjadi kesalahan dalam mengambil data produk";
                        sendResponse(exchange, responseError, 500);
                        return;
                    }
                }
            }

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
