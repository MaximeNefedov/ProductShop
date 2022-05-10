package ru.netology.shop.suppliers;

import ru.netology.shop.exceptions.InvalidProductTypeException;
import ru.netology.shop.ProductConsumer;
import ru.netology.shop.entites.ProductType;

import java.math.BigDecimal;
import java.util.Map;

public class ProductSupplierFactory implements SupplierFactory {
    private final ProductConsumer productConsumer;

    public ProductSupplierFactory(ProductConsumer productConsumer) {
        this.productConsumer = productConsumer;
    }

    @Override
    public Supplier getSupplier(ProductType productType) throws InvalidProductTypeException {
        switch (productType) {
            case VEGETABLES -> {
                return new VegetableSupplier(productConsumer, productType);
            }
            case MEAT -> {
                return new MeatSupplier(productConsumer, productType);
            }
            case SWEETS -> {
                return new CandiesSupplier(productConsumer, productType);
            }
            case BEVERAGES -> {
                return new BeverageSupplier(productConsumer, productType);
            }
            case FRUIT -> {
                return new FruitSupplier(productConsumer, productType);
            }
            case MILK_PRODUCTS -> {
                return new MilkProductsSupplier(productConsumer, productType);
            }
            default -> throw new InvalidProductTypeException("Категория данных товар не предоставляется");
        }
    }

    private static class VegetableSupplier extends AbstractSupplier {
        public VegetableSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                            "Овощи", new BigDecimal("100.99"),
                            "Овощи вкусные", new BigDecimal("150.35"),
                            "Овощи очень вкусные", new BigDecimal("199.99")
                    )
            );
        }
    }

    private static class MeatSupplier extends AbstractSupplier {

        public MeatSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "Мираторг", new BigDecimal("1499.98"),
                    "Петелинка", new BigDecimal("700.55")
            ));
        }
    }

    private static class CandiesSupplier extends AbstractSupplier {

        public CandiesSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "Альпенгольд", new BigDecimal("599.99"),
                    "Lindor", new BigDecimal("749.99")
            ));
        }
    }

    private static class MilkProductsSupplier extends AbstractSupplier {

        public MilkProductsSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "Веселый молочник", new BigDecimal("87.99"),
                    "Слобода", new BigDecimal("49.99")
            ));
        }
    }

    private static class BeverageSupplier extends AbstractSupplier {

        public BeverageSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "7 UP", new BigDecimal("87.00"),
                    "Pepsi", new BigDecimal("68.99"),
                    "Coca Cola", new BigDecimal("72.00")
            ));
        }
    }

    private static class FruitSupplier extends AbstractSupplier {

        public FruitSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "Персик", new BigDecimal("34.99"),
                    "Яблкоко", new BigDecimal("22.99"),
                    "Дыня", new BigDecimal("55.45")
            ));
        }
    }
}
