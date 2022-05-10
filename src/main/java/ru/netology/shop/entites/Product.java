package ru.netology.shop.entites;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final String name;
    private final ProductType type;
    private BigDecimal price;
    private int rating;

    public Product cloneProduct() {
        Product product = new Product(getName(), getType(), getPrice(), getRating());
        return product;
    }

    public Product(String name, ProductType type, BigDecimal price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Product(String name, ProductType type, BigDecimal price, int productRating) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.rating = productRating;
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price &&
                rating == product.rating &&
                Objects.equals(name, product.name) &&
                type == product.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "Товар: " + name + ","
                + " Стоимость: " + price + ","
                + " Рейтинг: " + rating;

    }
}
