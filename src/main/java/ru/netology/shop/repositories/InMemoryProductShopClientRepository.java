package ru.netology.shop.repositories;

import ru.netology.shop.client.ProductShopClient;

import java.util.HashMap;
import java.util.Map;

public class InMemoryProductShopClientRepository implements ProductShopClientRepository {
    private final Map<String, ProductShopClient> clients;

    public InMemoryProductShopClientRepository() {
        this.clients = new HashMap<>();
    }

    @Override
    public ProductShopClient findByName(String name) {
        return clients.get(name);
    }

    @Override
    public boolean save(ProductShopClient productShopClient) {
        return clients.putIfAbsent(productShopClient.getLogin(), productShopClient) == null;
    }
}
