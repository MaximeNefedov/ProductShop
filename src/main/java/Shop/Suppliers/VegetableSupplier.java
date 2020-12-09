package Shop.Suppliers;
import Shop.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VegetableSupplier extends ProductSupplier {


    public VegetableSupplier(AbleToBeSupplied productShop) {
        super(productShop);
    }

    @Override
    public void addProduct(Product product, int amount) {
        if (!product.getType().equals(ProductType.VEGETABLES)) {
            System.out.println("Поставщик доставлет только овощи");
        } else {
            Accountant accountant = ProductAccountant.getProductAccountant();
            Map<ProductType, Map<String, List<Product>>> warehouse = getProductShop().getWarehouse();
            Map<String, List<Product>> productInfo = warehouse.get(product.getType());
            accountant.addRegistration(product.getName(), product.getType());
            if (productInfo == null || productInfo.isEmpty()) {
                Map<String, List<Product>> products = new HashMap<>();
                List<Product> productList = new ArrayList<>();

                List<Product> filledProductList = fillList(productList, product, amount);

                products.put(product.getName(), filledProductList);
                warehouse.put(product.getType(), products);
            } else {
                List<Product> productList = productInfo.get(product.getName());
                if (productList == null) {
                    productList = new ArrayList<>();
                    productList.add(product);
                }
                List<Product> filledProductList = fillList(productList, product, amount);
                productInfo.put(product.getName(), filledProductList);
                warehouse.put(product.getType(), productInfo);
            }
        }
    }
    protected List<Product> fillList(List<Product> products, Product product, int amount) {
        for (int i = 0; i < amount; i++) {
            Product clonedProduct = product.cloneProduct();
            products.add(clonedProduct);
        }
        return products;
    }

}
