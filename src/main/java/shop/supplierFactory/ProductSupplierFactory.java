package shop.supplierFactory;
import shop.AbleToBeSupplied;
import shop.suppliers.ProductSupplier;
import shop.Supplier;

public class ProductSupplierFactory implements SupplierFactory {

    private AbleToBeSupplied shop;

    public ProductSupplierFactory(AbleToBeSupplied shop) {
        this.shop = shop;
    }

    @Override
    public Supplier getSupplier() {
        return new ProductSupplier(shop);
    }
}
