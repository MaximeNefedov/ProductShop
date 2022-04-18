package shop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProductAccountant implements Accountant {
    private static ProductAccountant instance;
    private Map<String, ProductType> directory;

    private ProductAccountant() {
        this.directory = new HashMap<>();
    }

    public static ProductAccountant getProductAccountant() {
        if (instance == null) {
            instance = new ProductAccountant();
        }
        return instance;
    }


    @Override
    public void addRegistration(String name, ProductType productType) {
        directory.put(name, productType);
    }

    @Override
    public void showDirectory() {
        Set<Map.Entry<String, ProductType>> entries = directory.entrySet();
        for (Map.Entry<String, ProductType> entry : entries) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public ProductType getProductType(String name) {
        return directory.get(name);
    }
}
