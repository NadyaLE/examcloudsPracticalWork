package ru.edu;

import java.util.ResourceBundle;

/**
 * Hello world!
 */
public class Product {
    private String name;
    private double price;
    private double rating;

    @Override
    public String toString() {
        return name +
                "   " + price +
                "   " + rating;
    }

    public Product(String name, double price, double rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
