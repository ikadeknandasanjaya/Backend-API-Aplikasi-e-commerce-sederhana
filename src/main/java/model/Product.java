package model;

public class Product {
    private int id;
    private int seller;
    private String title;
    private String description;
    private String price;
    private int stock;

    public Product(int id, String seller, String title, String description, double price, int stock) {
        this.id = id;
        this.seller = Integer.parseInt(seller);
        this.title = title;
        this.description = description;
        this.price = String.valueOf(price);
        this.stock = stock;
    }

    // Getter dan setter untuk setiap atribut

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeller() {
        return seller;
    }

    public void setSeller(int seller) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}