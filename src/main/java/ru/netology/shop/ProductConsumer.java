package shop.shop;

import shop.entites.Product;

import java.util.List;

public interface ProductConsumer {
    void receiveProducts(List<Product> products);
}
