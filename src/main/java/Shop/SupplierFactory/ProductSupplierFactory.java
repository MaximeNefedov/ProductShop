package Shop.SupplierFactory;
import Shop.AbleToBeSupplied;
import Shop.Suppliers.ProductSupplier;
import Shop.Supplier;

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
