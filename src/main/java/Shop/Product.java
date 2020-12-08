package Shop;

import java.util.Objects;

public class Product {
    private final String name;
    private final ProductType type;
    private int price;
    private int productRating;

    public Product cloneProduct() {
        Product product = new Product(getName(), getType(), getPrice(), getProductRating());
        return product;
    }

    public Product(String name, ProductType type, int price, int productRating) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.productRating = productRating;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setProductRating(int productRating) {
        this.productRating = productRating;
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

    public int getProductRating() {
        return productRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price &&
                productRating == product.productRating &&
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
                + " Рейтинг: " + productRating;

    }
}
