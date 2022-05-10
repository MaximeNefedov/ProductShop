package shop.repositories;

import shop.entites.Product;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, List<Product>> warehouse;

    public InMemoryProductRepository() {
        warehouse = new HashMap<>();
    }

    @Override
    public int findAmountByName(String name) {
        return 0;
    }

    @Override
    public Optional<Product> findByName(String name) {
        List<Product> products = warehouse.get(name);
        Product product = null;
        if (products != null && !products.isEmpty()) {
            product = products.get(0);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public boolean removeProduct(String name, int amount) {
        return false;
    }

    @Override
    public void save(Product product) {
        String productName = product.getName();
        warehouse.compute(productName, (name, productsList) -> {
            if (productsList == null) {
                productsList = new LinkedList<>();
            }
            productsList.add(product);
            return productsList;
        });
    }

    @Override
    public void saveAll(List<Product> products) {
        if (products != null && !products.isEmpty()) {
            Product product = products.get(0);
            String productName = product.getName();
            warehouse.compute(productName, (name, productsList) -> {
                if (productsList == null) {
                    productsList = products;
                } else {
                    productsList.addAll(products);
                }
                return productsList;
            });
        }
    }

    @Override
    public Map<String, List<Product>> getWarehouse() {
        return warehouse;
    }
}
