package Shop.SupplierFactory;

import Shop.AbleToBeSupplied;
import Shop.Supplier;
import Shop.Suppliers.VegetableSupplier;

public class VegetableSupplierFactory implements SupplierFactory {

    private AbleToBeSupplied shop;

    public VegetableSupplierFactory(AbleToBeSupplied shop) {
        this.shop = shop;
    }

    @Override
    public Supplier getSupplier() {
        return new VegetableSupplier(shop);
    }
}
