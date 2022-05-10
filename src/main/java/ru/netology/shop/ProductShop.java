package shop.shop;

import client.ProductShopClient;
import shop.entites.Product;

import java.util.Optional;

public interface ProductShop {
    Optional<Product> search(String name);

    void showAllProducts(SortType sortType);

    int createBasket(int userId);

    boolean showBasket(int basketId);

    boolean addToBasket(int basketId, Product product, int amount);

    boolean deleteFromBasket(int basketIdId, String productName, int amount);

    int buy();

    void backProduct(int check);

    ProductShopClient getClient(String login, String password);
}
