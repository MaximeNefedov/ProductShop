package ru.netology.shop.repositories;

import ru.netology.shop.client.ProductShopClient;

public interface ProductShopClientRepository {
    ProductShopClient findByName(String name);

    boolean save(ProductShopClient productShopClient);
}
