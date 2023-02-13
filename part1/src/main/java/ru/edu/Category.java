package ru.edu;

import java.util.*;

enum Category {
    SUMMER(new LinkedHashSet<>(List.of(new Product("skirt", 520.35, 4.5),
            new Product("shorts", 670.2, 4.2),
            new Product("top", 810.94532, 4.24989)))),
    WINTER(new LinkedHashSet<> (List.of(new Product("fur-coat", 50_000, 3.8),
            new Product("down-jacket", 6_000, 4.6),
            new Product("scarf", 1_000, 4.8)))),
    SPRING(new LinkedHashSet<> (List.of(new Product("windbreaker", 3_000, 3.5),
            new Product("jeans", 1_500, 4.5),
            new Product("boots", 7_000, 4.8)))),
    AUTUMN(new LinkedHashSet<>(List.of(new Product("jacket", 3_000, 3.5),
            new Product("sweater", 2_800, 4.33),
            new Product("pants", 4_000, 4.11))));

    private Set<Product> products;

    Category(Set<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public Set<Product> addProduct(Product product) {
        products.add(product);
        return products;
    }

    public void sortProducts(List<Product> sortedProducts){
       products = new LinkedHashSet<>(sortedProducts);
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static Category valueOf(int num) {
        return (num >= 0 && num < values().length) ? Category.values()[num] : null;
    }

    public static Category getCategoryByProduct(Product product) {
        return Arrays.stream(Category.values()).filter(category -> category.products.contains(product)).findAny().orElse(null);
    }
}
