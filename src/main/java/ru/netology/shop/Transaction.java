package ru.netology.shop;

import ru.netology.shop.client.ProductShopClient;
import ru.netology.shop.entites.Product;
import ru.netology.shop.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Transaction {
    private final int id;
    private final Map<String, List<Product>> productMap;
    private final ProductRepository productRepository;
    private final ProductShopClient productShopClient;
    private final BigDecimal sum;
    private TransactionStatus status;

    public Transaction(int id,
                       ProductRepository productRepository,
                       ProductShopClient productShopClient) {
        this.id = id;
        this.productRepository = productRepository;
        this.productShopClient = productShopClient;
        this.productMap = new HashMap<>(productShopClient.getProductBasket().getProductMap());
        this.sum = productShopClient.getProductBasket().getTotalPrice();
    }

    public void pay() {
        for (var entry : productMap.entrySet()) {
            String productName = entry.getKey();
            int amount = entry.getValue().size();
            productRepository.removeProduct(productName, amount);
        }
        BigDecimal totalPrice = productShopClient.getProductBasket().getTotalPrice();
        BigDecimal shopClientBalance = productShopClient.getBalance();
        productShopClient.setBalance(shopClientBalance.subtract(totalPrice));
        status = TransactionStatus.COMPLETED;
    }

    public void rollback() {
        for (var entry : productMap.entrySet()) {
            String productName = entry.getKey();
            List<Product> products = entry.getValue();
            productRepository.saveAllByName(productName, products);
        }
        System.out.printf("Клиент %s вернул следующие товары:%n%s%nКлиенту возвращены денежные средства в размере: %s%n",
                productShopClient.getLogin(),
                productMap.entrySet().stream().map(new Function<Map.Entry<String, List<Product>>, String>() {
                    int productCounter = 0;
                    @Override
                    public String apply(Map.Entry<String, List<Product>> entry) {
                        String productName = entry.getKey();
                        List<Product> currentProductList = entry.getValue();
                        return ++productCounter + ") " + productName + ", количество: " + currentProductList.size();
                    }
                }).reduce(((s, s2) -> s + "\n" + s2)).orElseThrow(RuntimeException::new),
                sum.toString());
        productMap.clear();
        BigDecimal balance = productShopClient.getBalance();
        productShopClient.setBalance(balance.add(sum));
        status = TransactionStatus.ROLLED_BACK;
    }

    public int getId() {
        return id;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public enum TransactionStatus {
        COMPLETED, ROLLED_BACK
    }

    @Override
    public String toString() {
        return "Транзакция: " + id + ", статус: " + status + "\n" +
                "клиент: " + productShopClient.getLogin() + "\n" +
                "сумма оплаты: " + sum.doubleValue();
    }
}
