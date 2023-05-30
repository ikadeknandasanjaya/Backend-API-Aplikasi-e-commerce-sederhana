package handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static handler.SendResponse.sendResponse;
import static util.AddProduct.addProduct;
import static util.DeleteProduct.deleteProduct;
import static util.GetUserProducts.getUserProducts;
import static util.UpdateProduct.updateProduct;

public class ProductsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            try {
                List<Product> products = getUserProducts(userId);

                Gson gson = new GsonBuilder()
                        .disableHtmlEscaping()
                        .setPrettyPrinting()
                        .serializeNulls()
                        .create();

                String response = gson.toJson(products);
                sendResponse(exchange, response, 200);
            } catch (SQLException e) {
                e.printStackTrace();
                String errorResponse = "Terjadi kesalahan dalam mengambil data produk";
                sendResponse(exchange, errorResponse, 500);
            }
        } else if ("POST".equals(exchange.getRequestMethod())) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            Gson gson = new GsonBuilder().create();
            Product newProduct = gson.fromJson(requestBody, Product.class);

            try {
                boolean addSuccess = addProduct(newProduct);

                String response;
                int statusCode;
                if (addSuccess) {
                    response = "Produk berhasil ditambahkan";
                    statusCode = 200;
                } else {
                    response = "Gagal menambahkan produk";
                    statusCode = 500;
                }

                sendResponse(exchange, response, statusCode);
            } catch (SQLException e) {
                e.printStackTrace();
                String errorResponse = "Terjadi kesalahan dalam menambahkan produk";
                sendResponse(exchange, errorResponse, 500);
            }
        } else if ("PUT".equals(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            String productId = path.substring(path.lastIndexOf("/") + 1);

            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            Gson gson = new GsonBuilder().create();
            Product updatedProduct = gson.fromJson(requestBody, Product.class);
            updatedProduct.setId(Integer.parseInt(productId));

            boolean updateSuccess = updateProduct(updatedProduct);

            String response;
            int statusCode;
            if (updateSuccess) {
                response = "Produk berhasil diperbarui";
                statusCode = 200;
            } else {
                response = "Gagal memperbarui produk";
                statusCode = 500;
            }

            sendResponse(exchange, response, statusCode);
        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            String productId = path.substring(path.lastIndexOf("/") + 1);

            boolean deleteSuccess = deleteProduct(productId);

            String response;
            int statusCode;
            if (deleteSuccess) {
                response = "Produk berhasil dihapus";
                statusCode = 200;
            } else {
                response = "Gagal menghapus produk";
                statusCode = 500;
            }

            sendResponse(exchange, response, statusCode);
        } else {
            String response = "Metode HTTP yang diminta tidak diizinkan";
            sendResponse(exchange, response, 405);
        }
    }
}