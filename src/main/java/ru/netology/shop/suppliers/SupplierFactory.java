package ru.netology.shop.suppliers;

import ru.netology.shop.exceptions.InvalidProductTypeException;
import ru.netology.shop.entites.ProductType;

public interface SupplierFactory {
    Supplier getSupplier(ProductType productType) throws InvalidProductTypeException;
}
