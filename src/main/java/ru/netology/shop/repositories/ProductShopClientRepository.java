package shop.repositories;

import client.ProductShopClient;

public interface ProductShopClientRepository {
    ProductShopClient findByName(String name);
    void save(ProductShopClient productShopClient);
}
