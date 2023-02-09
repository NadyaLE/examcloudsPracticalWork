package ru.edu;

import java.util.Arrays;

enum Category{
    SUMMER( new Product[]{new Product("skirt", 520.35, 4.5),
            new Product("shorts", 670.2, 4.2),
            new Product("top", 810.94532, 4.24989)}),
    WINTER( new Product[]{new Product("fur-coat", 50_000, 3.8),
            new Product("down-jacket", 6_000, 4.6),
            new Product("scarf",1_000 , 4.8)}),
    SPRING( new Product[]{new Product("windbreaker",
            3_000, 3.5),
            new Product("jeans", 1_500, 4.5),
            new Product("boots",7_000 , 4.8)}),
    AUTUMN( new Product[]{new Product("jacket", 3_000, 3.5),
            new Product("sweater", 2_800, 4.33),
            new Product("pants",4_000 , 4.11)});

    private final Product[] products;
    Category(Product[] products) {
        this.products = products;
    }

    public Product[] getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public static Category valueOf(int num){
        return (num >= 0 && num < values().length)? Category.values()[num]:null;
    }

    public static Category getCategoryByProduct(Product product){
        return Arrays.stream(Category.values()).filter(category -> Arrays.asList(category.products).contains(product)).findAny().orElse(null);
    }
}
