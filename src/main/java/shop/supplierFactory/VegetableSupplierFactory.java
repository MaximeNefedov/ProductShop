package shop.supplierFactory;

import shop.AbleToBeSupplied;
import shop.Supplier;
import shop.suppliers.VegetableSupplier;

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
