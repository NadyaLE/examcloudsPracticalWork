package ru.edu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Basket: " + products;
    }

    public Basket addProduct(Product product) {
        products.add(product);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(products, basket.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
}
