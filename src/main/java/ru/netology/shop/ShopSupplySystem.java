package shop;

import exceptions.InvalidProductException;
import exceptions.InvalidProductTypeException;
import shop.entites.ProductType;
import shop.shop.ProductConsumer;
import shop.suppliers.ProductSupplierFactory;
import shop.suppliers.Supplier;

import java.util.EnumMap;
import java.util.Map;

public class ShopSupplySystem {
    private final Map<ProductType, Supplier> suppliers;
    private final ProductConsumer productConsumer;

    public ShopSupplySystem(ProductConsumer productConsumer) {
        this.productConsumer = productConsumer;
        this.suppliers = new EnumMap<>(ProductType.class);
    }

    public void registerDefaultSuppliers() throws InvalidProductTypeException {
        ProductSupplierFactory factory = new ProductSupplierFactory(productConsumer);
        ProductType candies = ProductType.SWEETS;
        ProductType milkProducts = ProductType.MILK_PRODUCTS;
        ProductType beverages = ProductType.BEVERAGES;
        ProductType vegetables = ProductType.VEGETABLES;
        ProductType meat = ProductType.MEAT;
        ProductType fruit = ProductType.FRUIT;
        suppliers.put(candies, factory.getSupplier(candies));
        suppliers.put(milkProducts, factory.getSupplier(milkProducts));
        suppliers.put(beverages, factory.getSupplier(beverages));
        suppliers.put(vegetables, factory.getSupplier(vegetables));
        suppliers.put(meat, factory.getSupplier(meat));
        suppliers.put(fruit, factory.getSupplier(fruit));
    }

    public void supplyCurrentProduct(String productName, ProductType productType, int amount) throws InvalidProductException {
        Supplier supplier = suppliers.get(productType);
        supplier.addProducts(productName, amount);
    }
}
