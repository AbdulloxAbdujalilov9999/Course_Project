package com.epam.app.entity;

public class Product {
    private Long id;
    private String name;
    private String category;
    private Long price;
    private Long totalAmount;
    public Product() {
    }

    public Product(Long id, String name, String category, Long price, Long totalAmount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "NAME: " + name + "\n" +
                "CATEGORY: " + category + "\n" +
                "PRICE: " + price + "\n" +
                "TOTAL AMOUNT: " + totalAmount + "\n" +
                "--------------------\n";
    }
}
