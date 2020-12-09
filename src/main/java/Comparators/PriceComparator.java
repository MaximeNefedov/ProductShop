package Comparators;
import java.util.*;
import Shop.Product;
public class PriceComparator implements Comparator<Product> {


    @Override
    public int compare(Product o1, Product o2) {
//        Сортировка по возрастанию цены
        return Integer.compare(o2.getPrice(), o1.getPrice());
    }
}
