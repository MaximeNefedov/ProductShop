package shop.suppliers;

import exceptions.InvalidProductException;
import shop.entites.ProductType;
import shop.entites.Product;
import shop.shop.ProductConsumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//    Open-closed principle
//    Поставщику продуктов не важно, с каким магазином ему придется работать, ему важно,
//    чтобы магазин реализовывал интерфейс AbleToBeSupplied с методом, предоставляющим доступ к продуктовому складу
public abstract class AbstractSupplier implements Supplier {
    protected final Map<String, Integer> availableProducts;
    private final ProductConsumer productConsumer;
    protected final ProductType productType;

    public AbstractSupplier(ProductConsumer consumer, ProductType productType, Map<String, Integer> availableProducts) {
        this.productConsumer = consumer;
        this.productType = productType;
        this.availableProducts = availableProducts;
    }

    public AbstractSupplier(ProductConsumer consumer, ProductType productType) {
        this.productConsumer = consumer;
        this.productType = productType;
        this.availableProducts = new HashMap<>();
    }

    @Override
    public void addProducts(String name, int amount) throws InvalidProductException {
        List<Product> products = createProducts(name, amount);
        productConsumer.receiveProducts(products);
    }

    protected List<Product> createProducts(String name, int amount) throws InvalidProductException {
        Integer price = availableProducts.get(name);
        if (price == null) throw new InvalidProductException(String.format("Товар \"%s\" не поддерживается", name));
        else {
            List<Product> products = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                products.add(new Product(name, productType, price));
            }
            return products;
        }
    }
}
