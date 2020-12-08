package Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSupplier implements Supplier {

//    Open-closed principle
//    Поставщику продуктов не важно, с каким магазином ему придется работать, ему важно,
//    чтобы магазин реализовывал интерфейс AbleToBeSupplied с методом, предоставляющим доступ к продуктовому складу

    private AbleToBeSupplied productShop;
    private Accountant accountant;

    public ProductSupplier(AbleToBeSupplied productShop) {
        this.productShop = productShop;
        accountant = ProductAccountant.getProductAccountant();
    }

    @Override
    public void addProduct(Product product, int amount) {
        Map<ProductType, Map<String, List<Product>>> warehouse = productShop.getWarehouse();

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

    private List<Product> fillList(List<Product> products, Product product, int amount) {
        for (int i = 0; i < amount; i++) {
            Product clonedProduct = product.cloneProduct();
            products.add(clonedProduct);
        }
        return products;
    }
}
