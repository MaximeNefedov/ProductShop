package shop.repositories;

import shop.entites.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository {
    int findAmountByName(String name);

    Optional<Product> findByName(String name);

    boolean removeProduct(String name, int amount);

    void save(Product product);

    void saveAll(List<Product> products);

    Map<String, List<Product>> getWarehouse();
}
