package ru.netology.shop.suppliers;

import ru.netology.shop.exceptions.InvalidProductException;

public interface Supplier {
    void addProducts(String name, int amount) throws InvalidProductException;
}
