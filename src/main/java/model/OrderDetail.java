package model;

public class OrderDetail {
        private int orderId;
        private int productId;
        private int quantity;
        private int price;


    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

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

