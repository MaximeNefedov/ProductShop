package Comparators;

import java.util.Comparator;
import Shop.Product;


public class RatingComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
//        Сортировка по возрастанию рейтинга
        return Integer.compare(o2.getProductRating(), o1.getProductRating());
    }
}
