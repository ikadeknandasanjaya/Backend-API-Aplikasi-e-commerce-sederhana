package main.java.model;

public class OrderDetail {
        private int orderId;
        private int productId;
        private int quantity;
        private int price;




    public OrderDetail(String orderId, int productId, int quantity, int price) {
            this.orderId = Integer.parseInt(orderId);
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        public int getOrderId() {
            return orderId;
        }

        public int getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }

    public int getPrice() {
        return price;
    }
    }

