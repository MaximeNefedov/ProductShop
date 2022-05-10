package ru.netology.shop.repositories;

import ru.netology.shop.entites.Product;

import java.util.List;
import java.util.Map;

public interface ProductRepository {
    int findAmountByName(String name);

    List<Product> findByName(String name);

    boolean removeProduct(String name, int amount);

    void save(Product product);

    void saveAllByName(String name, List<Product> products);

    Map<String, List<Product>> getWarehouse();
}
