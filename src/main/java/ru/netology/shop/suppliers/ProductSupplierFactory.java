package shop.suppliers;

import exceptions.InvalidProductTypeException;
import shop.shop.ProductConsumer;
import shop.entites.ProductType;

import java.util.Map;

public class ProductSupplierFactory implements SupplierFactory {
    private ProductConsumer productConsumer;

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
                            "Овощи", 100,
                            "Овощи вкусные", 150,
                            "Овощи очень вкусные", 200
                    )
            );
        }
    }

    private static class MeatSupplier extends AbstractSupplier {

        public MeatSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "Мираторг", 1500,
                    "Петелинка", 700
            ));
        }
    }

    private static class CandiesSupplier extends AbstractSupplier {

        public CandiesSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "Альпенгольд", 600,
                    "Lindor", 700
            ));
        }
    }

    private static class MilkProductsSupplier extends AbstractSupplier {

        public MilkProductsSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "Веселый молочник", 87,
                    "Слобода", 45
            ));
        }
    }

    private static class BeverageSupplier extends AbstractSupplier {

        public BeverageSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "7 UP", 87,
                    "Pepsi", 68,
                    "Coca Cola", 72
            ));
        }
    }

    private static class FruitSupplier extends AbstractSupplier {

        public FruitSupplier(ProductConsumer consumer, ProductType productType) {
            super(consumer, productType);
            availableProducts.putAll(Map.of(
                    "Персик", 34,
                    "Яблкоко", 22,
                    "Дыня", 55
            ));
        }
    }
}
