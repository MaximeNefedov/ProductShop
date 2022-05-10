package ru.netology.shop.repositories;

import ru.netology.shop.client.ProductShopClient;

public interface ProductShopClientRepository {
    ProductShopClient findByName(String name);
    void save(ProductShopClient productShopClient);
}
