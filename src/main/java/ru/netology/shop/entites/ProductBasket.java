package ru.netology.shop.entites;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class ProductBasket {
    private BigDecimal totalPrice;
    private Map<String, List<Product>> productMap = new HashMap<>();

    public ProductBasket() {
        this.totalPrice = new BigDecimal(0);
    }

    public void add(List<Product> products) {
        Product product = products.get(0);
        BigDecimal productPrice = product.getPrice();
        totalPrice = totalPrice.add(productPrice.multiply(new BigDecimal(10)));
        productMap.compute(product.getName(), (name, productsList) -> {
            if (productsList == null) {
                productsList = products;
            } else {
                productsList.addAll(products);
            }
            return productsList;
        });
    }

    public void remove(String name, int amount) {
        if (amount > 0) {
            if (productMap.containsKey(name)) {
                List<Product> productList = productMap.get(name);
                if (amount <= productList.size()) {
                    int edge = productList.size() - 1 - amount;
                    for (int i = productList.size() - 1; i > edge; i--) {
                        totalPrice = totalPrice.subtract(productList.get(i).getPrice());
                        productList.remove(i);
                    }
                    if (productList.isEmpty()) {
                        productMap.remove(name);
                    }
                } else {
                    System.out.println("Нельзя удалить " + amount
                            + " элементов, так как в корзине находится: " + productList.size());
                }
            } else {
                System.out.println("Такого продукта нет в корзине");
            }
        } else {
            System.out.println("Недопустим ввод 0 и отрицательных значений");
        }
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void clear() {
        productMap.clear();
        totalPrice = BigDecimal.ZERO;
    }

    public boolean isEmpty() {
        return totalPrice.compareTo(BigDecimal.ZERO) == 0;
    }

    public Map<String, List<Product>> getProductMap() {
        return productMap;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (productMap.isEmpty()) {
            stringBuilder.append("Корзина пуста");
        } else {
            for (var entry : productMap.entrySet()) {
                stringBuilder.append("Товар: ").append(entry.getKey())
                        .append(" количество товара: ").append(entry.getValue().size())
                        .append("\n");
            }
            stringBuilder.append("Общая стоимость: ").append(totalPrice);
        }
        return stringBuilder.toString();
    }
}
