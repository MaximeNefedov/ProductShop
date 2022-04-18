package shop;

import exceptions.ProductNotFoundException;
import exceptions.ShopOutOfProductException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class WarehouseAdministrator {
    public void addToWarehouse(Map<String, List<Product>> products) {
        Searcher searcher = SearcherImpl.getSearcher();
        searcher.addProducts(products);
    }

    public void deleteFromWareHouse(ProductBasket productBasket) throws ShopOutOfProductException, ProductNotFoundException {
        Map<String, List<Product>> productMap = productBasket.getProductMap();
        Set<Map.Entry<String, List<Product>>> entries = productMap.entrySet();
        Searcher searcher = SearcherImpl.getSearcher();
        for (Map.Entry<String, List<Product>> entry : entries) {
            String name = entry.getKey();
            int size = entry.getValue().size();
            searcher.deleteProduct(name, size);
        }
    }
}
