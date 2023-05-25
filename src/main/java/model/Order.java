package main.java.model;

public class Order {
    private int id;
    private int buyer;
    private String note;
    private double total;
    private double discount;
    private boolean isPaid;

    public Order(int id, int buyer, String note, double total, double discount, boolean isPaid) {
        this.id = id;
        this.buyer = buyer;
        this.note = note;
        this.total = total;
        this.discount = discount;
        this.isPaid = isPaid;
    }

    public int getId() {
        return id;
    }

    public String getBuyer() {
        return String.valueOf(buyer);
    }

    public String getNote() {
        return note;
    }

    public double getTotal() {
        return total;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isPaid() {
        return isPaid;
    }
}
