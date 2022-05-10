package shop.suppliers;

import exceptions.InvalidProductException;

public interface Supplier {
    void addProducts(String name, int amount) throws InvalidProductException;
}
