package ru.edu;

import java.util.ArrayList;
import java.util.List;

public class Basket{
    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Basket:" + products;
    }

    public Basket addProduct(Product product){
        products.add(product);
        return this;
    }
}
