package Shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User {
    private String name;
    private int sum;
    private int check;

    private Map<String, List<Product>> products = new HashMap<>();

    public User(String name, int sum) {
        this.name = name;
        this.sum = sum;
    }

    public void setProductMap(Map<String, List<Product>> productMap) {
        Map<String, List<Product>> products = new HashMap<>(productMap);
        this.products = products;
    }

    public Map<String, List<Product>> getProducts() {
        return products;
    }

    public void showProducts() {
        Set<Map.Entry<String, List<Product>>> entries = products.entrySet();
        for (Map.Entry<String, List<Product>> entry : entries) {
            System.out.println("Товар: " + entry.getKey() + " количество товара: " + entry.getValue().size());
        }
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public int getSum() {
        return sum;
    }

    public int getCheck() {
        return check;
    }
}
