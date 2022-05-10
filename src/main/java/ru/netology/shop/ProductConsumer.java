package ru.netology.shop;

import ru.netology.shop.entites.Product;

import java.util.List;

public interface ProductConsumer {
    void receiveCurrentProduct(String productName, List<Product> products);
}
