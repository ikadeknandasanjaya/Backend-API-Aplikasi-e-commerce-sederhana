package main.java;

public class Product {
    private int id;
    private String seller;
    private String title;
    private String description;
    private double price;
    private int stock;

    public Product(int id, String seller, String title, String description, double price, int stock) {
        this.id = id;
        this.seller = seller;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // Getter dan setter untuk setiap atribut

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}