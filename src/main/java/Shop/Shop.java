package Shop;

import Exceptions.ProductNotFoundException;
import Exceptions.ShopOutOfProductException;

public interface Shop {
    int buy() throws ShopOutOfProductException, ProductNotFoundException;
    void backProduct(int check);
    boolean addToBasket(Product product, int amount) throws ProductNotFoundException;
    boolean showBasket();
    boolean deleteFromBasket(String name, int amount);
    Product search(String name) throws ProductNotFoundException, ShopOutOfProductException;
    void showAllProducts();
    void sortProducts(int x);
}
