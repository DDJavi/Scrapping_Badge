package com.borjamoll.scrapping.data;


//@Setter @Getter
public class Product {
    private String product;
    private double price;
    private String stars;

    public Product(String product, double price, String stars) {
        this.product = product;
        this.price = price;
        this.stars = stars;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
