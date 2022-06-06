package ru.netology.shop;

import ru.netology.shop.client.ProductShopClient;
import ru.netology.shop.entites.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductShop {
    List<Product> findProduct(String name, int amount);

    void showAllProducts(SortType sortType);

    int pay(String clientName);

    void refund(int check);

    void setClient(ProductShopClient productShopClient);

    void updateClientBalance(String login, BigDecimal cash);

    ProductShopClient getClient(String login, String password);

    void closeClient(String login);
}
