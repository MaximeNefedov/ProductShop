package shop.suppliers;

import exceptions.InvalidProductTypeException;
import shop.entites.ProductType;

public interface SupplierFactory {
    Supplier getSupplier(ProductType productType) throws InvalidProductTypeException;
}
