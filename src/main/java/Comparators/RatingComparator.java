package Comparators;

import java.util.Comparator;
import Shop.Product;


public class RatingComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        if (o1.getProductRating() > o2.getProductRating()) {
            return -1;
        } else if (o1.getProductRating() < o2.getProductRating()) {
            return 1;
        } else {
            return 0;
        }
    }
}
