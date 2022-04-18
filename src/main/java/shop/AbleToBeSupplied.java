package shop;

import java.util.List;
import java.util.Map;

public interface AbleToBeSupplied {
    Map<ProductType, Map<String, List<Product>>> getWarehouse();

}
