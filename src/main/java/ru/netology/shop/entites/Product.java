package shop.entites;

import java.util.Objects;

public class Product {
    private final String name;
    private final ProductType type;
    private int price;
    private int rating;

    public Product cloneProduct() {
        Product product = new Product(getName(), getType(), getPrice(), getRating());
        return product;
    }

    public Product(String name, ProductType type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Product(String name, ProductType type, int price, int productRating) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.rating = productRating;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ProductType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
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
