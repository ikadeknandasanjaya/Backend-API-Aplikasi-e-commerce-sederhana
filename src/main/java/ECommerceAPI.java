package main.java;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ECommerceAPI {
    //       Inisialisasi DB di sqlite
    private static final String basisData = "jdbc:sqlite:/D:\\sqlite\\apiecommerce.db";

    //       Koneksi ke DB di sqlite
    private static Connection koneksi() throws SQLException {
        return DriverManager.getConnection(basisData);
    }

    //      Method untuk memulai server
    public void mulaiServer() throws IOException {
//            Inisialisasi port dengan NIM saya diakhir
        int port = 8075;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
//            Print listening to port untuk menandakan server sudah dimulai
        System.out.println("Listening to port " + port + " ...");

        // endpoint untuk routes API
        server.createContext("/users/products", new UserProductHandler());        server.createContext("/users", new UserAllHandler());
        server.createContext("/users/", new UserHandler());
        server.createContext("/buyer", new BuyerHandler());
        server.createContext("/seller", new SellerHandler());
        server.createContext("/users/orders", new OrdersHandler());

        server.setExecutor(null);
        server.start();
    }

//    Untuk menangani respon
    private static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }


    //      Handler untuk users dengan type Buyer
    static class BuyerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    // Mengambil daftar pengguna dengan tipe "Buyer"
                    List<User> users = getUsersByType("Buyer");

                    if (users.isEmpty()) {
                        // Jika tidak ada pengguna dengan tipe "Buyer", kirim respons dengan status 404 Not Found
                        String respon404 = "User dengan tipe Buyer tidak ditemukan";
                        sendResponse(exchange, respon404, 404);
                    } else {
                        // Mengonversi daftar pengguna menjadi JSON
                        Gson gson = new GsonBuilder()
                                .disableHtmlEscaping()
                                .setPrettyPrinting()
                                .serializeNulls()
                                .create();
                        JsonArray jsonArray = new JsonArray();
                        for (User user : users) {
                            JsonElement userJson = gson.toJsonTree(user);
                            jsonArray.add(userJson);
                        }
                        // Untuk sementara tidak saya gunakan method yang ada gsonnya di kelas User karena error
                        String dataJson = gson.toJson(jsonArray);

                        // Mengirim respon
                        sendResponse(exchange, dataJson, 200);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Mengirim respon error jika terjadi kesalahan
                    String responError = "Terjadi kesalahan dalam mengambil daftar pengguna";
                    sendResponse(exchange, responError, 500);
                }
            } else {
                // Method HTTP yang diminta tidak ada
                String respon = "Method HTTP yang diminta tidak ada";
                sendResponse(exchange, respon, 405);
            }
        }
    }

    //      Handler untuk users dengan type Buyer
    static class SellerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    // Mengambil daftar pengguna dengan tipe "Seller"
                    List<User> users = getUsersByType("Seller");

                    if (users.isEmpty()) {
                        // Jika tidak ada pengguna dengan tipe "Seller", kirim respons dengan status 404 Not Found
                        String respon = "User dengan tipe Seller tidak ditemukan";
                        sendResponse(exchange, respon, 404);
                    } else {
                        // Mengonversi daftar pengguna menjadi JSON
                        Gson gson = new GsonBuilder()
                                .disableHtmlEscaping()
                                .setPrettyPrinting()
                                .serializeNulls()
                                .create();
                        JsonArray jsonArray = new JsonArray();
                        for (User user : users) {
                            JsonElement userJson = gson.toJsonTree(user);
                            jsonArray.add(userJson);
                        }
                        // Untuk sementara tidak saya gunakan method yang ada gsonnya di kelas User karena error
                        String respon = gson.toJson(jsonArray);

                        // Mengirim respon
                        sendResponse(exchange, respon, 200);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Mengirim respon error jika terjadi kesalahan
                    String errorRespon = "Terjadi kesalahan dalam mengambil daftar pengguna";
                    sendResponse(exchange, errorRespon, 500);
                }
            } else {
                // Method HTTP yang diminta tidak ada
                String respon = "Method HTTP yang diminta tidak ada";
                sendResponse(exchange, respon, 405);
            }
        }
    }

    //      Method untuk mendapatkan user dengan tipenya
    private static List<User> getUsersByType(String type) throws SQLException {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM users WHERE type = ?";
        try (Connection connection = koneksi();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, type);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String first_name = resultSet.getString("first_name");
                    String last_name = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String phone_number = resultSet.getString("phone_number");
                    String tipe = resultSet.getString("type");
                    User user = new User(id, first_name, last_name, email, phone_number, tipe);
                    users.add(user);
                }
            }
        }

        return users;
    }


    //      Handler untuk mendapatkan user dengan ID
    static class UserHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    // Mengambil pengguna berdasarkan ID oleh method getUserById Ngepass userID
                    User user = getUserById(userId);

                    if (user != null) {
                        // Menjadikan format Json
                        String dataJson = user.ubahJson();
                        // Mengirim respon
                        sendResponse(exchange, dataJson, 200);
                    } else {
                        // Memberikan output 404 jika id tidak ada
                        String respon = "User dengan ID yang dicari tidak ditemukan";
                        sendResponse(exchange, respon, 404);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Mengirim respon error ke jika terjadi kesalahan
                    String responError = "Terjadi kesalahan dalam mengambil data user";
                    sendResponse(exchange, responError, 500);
                }
            } else if ("PUT".equals(exchange.getRequestMethod())) {

            } else if ("DELETE".equals(exchange.getRequestMethod())) {

            } else {
                // Method HTTP yang diminta tidak ada
                String respon = ("Method HTTP yang diminta tidak ada");
                sendResponse(exchange, respon, 405);
            }
        }


        // Method buat mengambil user berdasarkan ID sesuai endpoint users/{id}
        private User getUserById(String userId) throws SQLException {
//                Objek user dan inisialisasi null terlebih dahulu
            User user = null;

//                Kueri sql untuk menselect users berdasarkan id
            String sql = "SELECT * FROM users WHERE id = ?";


            try (Connection konek = koneksi();
//                     Membuat objek dari PreparedStatement untuk ktia gunakan kueri sql didalamnya untuk menghindari SQL Injection
                 PreparedStatement param = konek.prepareStatement(sql)) {
//                     Mengisi nilai parameter dengan index dimulai dari 1
                param.setString(1, userId);
//                  Resulset untuk mengambil hasil dari kueri
                try (ResultSet hasil = param.executeQuery()) {
                    if (hasil.next()) {
                        int id = hasil.getInt("id");
                        String first_name = hasil.getString("first_name");
                        String last_name = hasil.getString("last_name");
                        String email = hasil.getString("email");
                        String phone_number = hasil.getString("phone_number");
                        String type = hasil.getString("type");
                        user = new User(id, first_name, last_name, email, phone_number, type);
                    }
                }
            }
            return user;
        }
    }

    static class UserAllHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    List<User> users = getAllUsers();
                    Gson gson = new GsonBuilder()
                            .disableHtmlEscaping()
                            .setPrettyPrinting()
                            .serializeNulls()
                            .create();
                    JsonArray jsonArray = new JsonArray();
                    for (User user : users) {
                        JsonElement userJson = gson.toJsonTree(user);
                        jsonArray.add(userJson);
                    }
                    String respon = gson.toJson(jsonArray);
                    sendResponse(exchange, respon, 200);
                } catch (SQLException e) {
                    e.printStackTrace();
                    String responError = "Terjadi kesalahan dalam mengambil data pengguna";
                    sendResponse(exchange, responError, 500);
                }
            } else {
                String respon = "Method HTTP yang diminta tidak diizinkan";
                sendResponse(exchange, respon, 405);
            }
        }
    }

    private static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM users";
        try (Connection connection = koneksi();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String phone_number = resultSet.getString("phone_number");
                String type = resultSet.getString("type");
                User user = new User(id, first_name, last_name, email, phone_number, type);
                users.add(user);
            }
        }

        return users;
    }


    public class UserProductHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String userId = null;


            // Parsing parameter userId dari query string
            String[] queryParams = query.split("&");
            for (String param : queryParams) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && keyValue[0].equals("userId")) {
                    userId = keyValue[1];
                    break;
                }
            }

            if (userId != null) {
                try {
                    // Mengambil produk yang dimiliki oleh pengguna dengan ID tertentu
                    List<Product> products = getUserProducts(userId);

                    // Mengubah data produk menjadi format JSON
                    Gson gson = new GsonBuilder()
                            .disableHtmlEscaping()
                            .setPrettyPrinting()
                            .serializeNulls()
                            .create();
                    JsonArray jsonArray = new JsonArray();
                    for (Product product : products) {
                        JsonElement userJson = gson.toJsonTree(product);
                        jsonArray.add(userJson);
                    }
                    String respon = gson.toJson(jsonArray);

                    // Mengirim respon dengan data produk dalam format JSON
                    sendResponse(exchange, respon, 200);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Mengirim respon error jika terjadi kesalahan
                    String errorResponse = "Terjadi kesalahan dalam mengambil data produk";
                    sendResponse(exchange, errorResponse, 500);
                }
            } else {
                // Parameter userId tidak ditemukan
                String errorResponse = "Parameter userId tidak ditemukan";
                sendResponse(exchange, errorResponse, 400);
            }
        }

        private List<Product> getUserProducts(String userId) throws SQLException {
            List<Product> products = new ArrayList<>();

            // Query untuk mendapatkan produk yang dimiliki oleh pengguna dengan ID tertentu
            String query = "SELECT * FROM products WHERE seller = ?";

            try (Connection connection = koneksi();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String seller = resultSet.getString("seller");
                        String title = resultSet.getString("title");
                        String description = resultSet.getString("description");
                        double price = resultSet.getDouble("price");
                        int stock = resultSet.getInt("stock");
                        Product product = new Product(id, seller, title, description, price, stock);
                        products.add(product);
                    }
                }
            }
            return products;
        }
    }

        public class OrdersHandler implements HttpHandler {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String query = exchange.getRequestURI().getQuery();
                String userId = null;

                // Parsing parameter userId dari query string
                String[] queryParams = query.split("&");
                for (String param : queryParams) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2 && keyValue[0].equals("userId")) {
                        userId = keyValue[1];
                        break;
                    }
                }

                if (userId != null) {
                    try {
                        // Mengambil data pesanan dari database untuk pengguna dengan ID tertentu
                        List<Order> orders = getUserOrders(userId);

                        // Mengubah data pesanan menjadi format JSON

                        Gson gson = new GsonBuilder()
                                .disableHtmlEscaping()
                                .setPrettyPrinting()
                                .serializeNulls()
                                .create();

                        JsonArray ordersJson = new JsonArray();
                        for (Order order : orders) {
                            JsonObject orderJson = gson.toJsonTree(order, Order.class).getAsJsonObject();

                            // Mengambil produk yang dimiliki oleh pengguna berdasarkan ID pengguna
                            List<Product> userProducts = getUserProducts(order.getBuyer());
                            JsonArray productsJson = new JsonArray();
                            for (Product product : userProducts) {
                                JsonObject productJson = gson.toJsonTree(product, Product.class).getAsJsonObject();
                                productsJson.add(productJson);
                            }

                            // Menambahkan data produk ke dalam objek pesanan
                            orderJson.add("products", productsJson);
                            ordersJson.add(orderJson);
                        }

                        String response = gson.toJson(ordersJson);
                        // Mengirim respon dengan data pesanan dan produk dalam format JSON
                        sendResponse(exchange, ordersJson.toString(), 200);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Mengirim respon error jika terjadi kesalahan
                        String errorResponse = "Terjadi kesalahan dalam mengambil data pesanan";
                        sendResponse(exchange, errorResponse, 500);
                    }
                } else {
                    // Parameter userId tidak ditemukan
                    String errorResponse = "Parameter userId tidak ditemukan";
                    sendResponse(exchange, errorResponse, 400);
                }
            }

            private List<Order> getUserOrders(String userId) throws SQLException {
                List<Order> orders = new ArrayList<>();

                // Query untuk mendapatkan data pesanan berdasarkan ID pengguna
                String query = "SELECT * FROM orders WHERE buyer = ?";

                try (Connection connection = koneksi();
                     PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, userId);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            int buyer = resultSet.getInt("buyer");
                            String note = resultSet.getString("note");
                            double total = resultSet.getDouble("total");
                            double discount = resultSet.getDouble("discount");
                            boolean isPaid = resultSet.getBoolean("is_paid");
                            Order order = new Order(id, buyer, note, total, discount, isPaid);
                            orders.add(order);
                        }
                    }
                }
                return orders;
            }

            private List<Product> getUserProducts(int userId) throws SQLException {
                List<Product> products = new ArrayList<>();

                // Query untuk mendapatkan produk yang dimiliki oleh pengguna dengan ID tertentu
                String query = "SELECT * FROM products WHERE seller = ?";

                try (Connection connection = koneksi();
                     PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, userId);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String seller = resultSet.getString("seller");
                            String title = resultSet.getString("title");
                            String description = resultSet.getString("description");
                            double price = resultSet.getDouble("price");
                            int stock = resultSet.getInt("stock");
                            Product product = new Product(id, seller, title, description, price, stock);
                            products.add(product);
                        }
                    }
                }
                return products;
            }
        }
    }

