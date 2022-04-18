package shop;

import exceptions.ProductNotFoundException;
import exceptions.ShopOutOfProductException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearcherImpl implements Searcher {

    private static Searcher instance;

    private Map<ProductType, Map<String, List<Product>>> warehouse;

    private SearcherImpl(Map<ProductType, Map<String, List<Product>>> warehouse) {
        this.warehouse = warehouse;
    }

    public static Searcher getSearcher() {
        if (instance == null) {
             instance = new SearcherImpl(ProductShop.getProductShop().getWarehouse());
        }
        return instance;
    }


    @Override
    public Product searchProduct(String name) throws ProductNotFoundException, ShopOutOfProductException {
        ProductAccountant productAccountant = ProductAccountant.getProductAccountant();
        ProductType productType = productAccountant.getProductType(name);
        if (productType == null) {
            throw new ProductNotFoundException();
        }
        Map<String, List<Product>> productsByType = warehouse.get(productType);
        List<Product> products = productsByType.get(name);
        if (products.isEmpty()) {
            throw new ShopOutOfProductException();
        } else {
            int counter = products.size() - 1;
            System.out.println("Товар: " + "\"" + products.get(counter).getName() + "\"" +
                    ", стоимость: " + products.get(counter).getPrice() +
                    "\n=============================================");
            return products.get(counter);
        }
    }

    public int searchAmountOfProducts(String name) throws ProductNotFoundException {
        ProductAccountant productAccountant = ProductAccountant.getProductAccountant();
        ProductType productType = productAccountant.getProductType(name);
        if (productType == null) {
            throw new ProductNotFoundException();
        }
        Map<String, List<Product>> productsByType = warehouse.get(productType);
        List<Product> products = productsByType.get(name);
        return products.size();
    }

    @Override
    public void deleteProduct(String name, int amount) throws ShopOutOfProductException, ProductNotFoundException {
        ProductAccountant productAccountant = ProductAccountant.getProductAccountant();
        ProductType productType = productAccountant.getProductType(name);
        if (productType == null) {
            throw new ProductNotFoundException();
        }
        Map<String, List<Product>> productsByType = warehouse.get(productType);
        List<Product> products = productsByType.get(name);
        int edge = (products.size() - 1) - amount;
        for (int i = products.size() - 1; i > edge; i--) {
            products.remove(i);
        }
    }

    @Override
    public void addProducts(Map<String, List<Product>> clientsMap) {
        ProductAccountant productAccountant = ProductAccountant.getProductAccountant();
        Set<Map.Entry<String, List<Product>>> clientsEntries = clientsMap.entrySet();
        for (Map.Entry<String, List<Product>> clientsEntry : clientsEntries) {
            ProductType productType = productAccountant.getProductType(clientsEntry.getKey());
            Map<String, List<Product>> shopsMap = warehouse.get(productType);

            List<Product> shopsProductList = shopsMap.get(clientsEntry.getKey());
            List<Product> clientsProductList = clientsEntry.getValue();

            shopsProductList.addAll(clientsProductList);
        }

    }
}
