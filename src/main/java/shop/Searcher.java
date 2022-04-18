package shop;

import exceptions.ProductNotFoundException;
import exceptions.ShopOutOfProductException;

import java.util.List;
import java.util.Map;

public interface Searcher {
    Product searchProduct(String name) throws ProductNotFoundException, ShopOutOfProductException;
    int searchAmountOfProducts(String name) throws ProductNotFoundException;
    void deleteProduct(String name, int amount) throws ShopOutOfProductException, ProductNotFoundException;
    void addProducts(Map<String, List<Product>> productMap);
}
