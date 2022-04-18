package shop;

import exceptions.ProductNotFoundException;

import java.util.*;

public class ProductBasket {

    private int totalPrice = 0;
    private Map<String, List<Product>> productMap = new HashMap<>();

    public boolean add(Product product, int amount) throws ProductNotFoundException {

        Searcher searcher = SearcherImpl.getSearcher();
        int amountOfProducts = searcher.searchAmountOfProducts(product.getName());
        if (amountOfProducts < amount) {
            System.out.println("=============================================" +
                    "\nНа данный момент на складе всего: " + amountOfProducts + " шт. данного товара" +
                    "\n=============================================");
            return false;
        } else if (amount == 0) {
            System.out.println("Количество товара не может быть равно 0");
            return false;
        }
        int price = 0;
        if (productMap.containsKey(product.getName())) {
            List<Product> productList = productMap.get(product.getName());
            for (int i = 0; i < amount; i++) {
                productList.add(product);
                price = product.getPrice();
                totalPrice += price;
            }
        } else {
            List<Product> productList = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                productList.add(product);
                price = product.getPrice();
                totalPrice += price;
            }
            productMap.put(product.getName(), productList);
        }
        return true;
    }

    public Map<String, List<Product>> getProductMap() {
        return productMap;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean show() {

        if (productMap.isEmpty()) {
            System.out.println("пусто...");
            return false;
        } else {
            Set<Map.Entry<String, List<Product>>> entries = productMap.entrySet();
            for (Map.Entry<String, List<Product>> entry : entries) {
                System.out.println("Товар: " + entry.getKey() + " количество товара: " + entry.getValue().size());
            }
            System.out.println("Общая стоимость: " + totalPrice);
        }
        return true;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean delete(String name, int amount) {
        if (amount > 0) {
            if (productMap.containsKey(name)) {
                List<Product> productList = productMap.get(name);
                if (amount <= productList.size()) {
                    int edge = productList.size() - 1 - amount;
                    for (int i = productList.size() - 1; i > edge; i--) {
                        totalPrice -= productList.get(i).getPrice();
                        productList.remove(i);
                    }
                    if (productList.isEmpty()) {
                        productMap.remove(name);
                    }
                    return true;
                } else {
                    System.out.println("Нельзя удалить " + amount
                            + " элементов, так как в корзине находится: " + productList.size());
                    return false;
                }
            } else {
                System.out.println("Такого продукта нет в корзине");
                return false;
            }
        } else {
            System.out.println("Недопустим ввод 0 и отрицательных значений");
            return false;
        }
    }
}
