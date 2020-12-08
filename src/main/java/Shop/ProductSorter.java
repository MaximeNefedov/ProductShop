package Shop;

import Comparators.PriceComparator;
import Comparators.RatingComparator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ProductSorter {
    public void sort(int x) {

        Map<Product, Integer> products;
        if (x == 1) {
            PriceComparator comparator = new PriceComparator();
            products = new TreeMap<>(comparator);
        } else if (x == 2) {
            RatingComparator comparator = new RatingComparator();
            products = new TreeMap<>(comparator);
        } else {
            System.out.println("Выбран неверный режим сортировки");
            return;
        }
        AbleToBeSupplied shop = ProductShop.getProductShop();
        Set<Map.Entry<ProductType, Map<String, List<Product>>>> entries = shop.getWarehouse().entrySet();;
        for (Map.Entry<ProductType, Map<String, List<Product>>> entry : entries) {
            Map<String, List<Product>> value = entry.getValue();
            Set<Map.Entry<String, List<Product>>> entries1 = value.entrySet();
            for (Map.Entry<String, List<Product>> stringListEntry : entries1) {
                List<Product> value1 = stringListEntry.getValue();
                if (!value1.isEmpty()) {
                    int size = value1.size();
                    Product product = value1.get(0);
                    products.put(product, size);
                }
            }
        }

        ProductType productType = null;
        Set<Map.Entry<Product, Integer>> entries1 = products.entrySet();
        for (Map.Entry<Product, Integer> productIntegerEntry : entries1) {
            if (productType == null) {
                productType = productIntegerEntry.getKey().getType();
                System.out.println(productIntegerEntry.getKey().getType().description + ":");
                System.out.println(productIntegerEntry.getKey() + " , в наличии: "
                        + productIntegerEntry.getValue() + " шт.");
            } else if (!productType.equals(productIntegerEntry.getKey().getType())) {
                productType = productIntegerEntry.getKey().getType();
                System.out.println(productIntegerEntry.getKey().getType().description + ":");
                System.out.println(productIntegerEntry.getKey() + " , в наличии: "
                        + productIntegerEntry.getValue() + " шт.");
            } else {
                System.out.println(productIntegerEntry.getKey() + " , в наличии: "
                        + productIntegerEntry.getValue() + " шт.");
            }
        }
    }
}
