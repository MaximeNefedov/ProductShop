import exceptions.InvalidProductException;
import exceptions.InvalidProductTypeException;
import pages.ProductShopPages;
import shop.shop.ProductConsumer;
import shop.shop.ProductShop;
import shop.shop.ProductShopImpl;
import shop.entites.ProductType;
import shop.ShopSupplySystem;

public class ShopApplication {
    public static void main(String[] args) throws InvalidProductException, InvalidProductTypeException {
        ProductShop productShop = new ProductShopImpl();
        supplyProducts((ProductConsumer) productShop);
        String login = "max";
        String password = "123";
        ProductShopClient shopClient = productShop.getClient(login, password);
        shopClient.start();
    }

    private static void supplyProducts(ProductConsumer productConsumer) throws InvalidProductTypeException, InvalidProductException {
        System.out.println("Начинается доставка продуктов в магазин");
        ShopSupplySystem shopSupplySystem = new ShopSupplySystem(productConsumer);
        shopSupplySystem.registerDefaultSuppliers();

        shopSupplySystem.supplyCurrentProduct("Овощи вкусные", ProductType.VEGETABLES, 20);
        shopSupplySystem.supplyCurrentProduct("Овощи", ProductType.VEGETABLES, 30);
        shopSupplySystem.supplyCurrentProduct("Овощи очень вкусные", ProductType.VEGETABLES, 40);

//        shopSupplySystem.supplyCurrentProduct("Мираторг", ProductType.MEAT, 10);
//        shopSupplySystem.supplyCurrentProduct("Петелинка", ProductType.MEAT, 10);
//
//        shopSupplySystem.supplyCurrentProduct("Альпенгольд", ProductType.SWEETS, 10);
//        shopSupplySystem.supplyCurrentProduct("Lindor", ProductType.SWEETS, 10);
//
//        shopSupplySystem.supplyCurrentProduct("Веселый молочник", ProductType.MILK_PRODUCTS, 10);
//        shopSupplySystem.supplyCurrentProduct("Слобода", ProductType.MILK_PRODUCTS, 10);
//
//        shopSupplySystem.supplyCurrentProduct("7 UP", ProductType.BEVERAGES, 10);
//        shopSupplySystem.supplyCurrentProduct("Pepsi", ProductType.BEVERAGES, 10);
//        shopSupplySystem.supplyCurrentProduct("Coca Cola", ProductType.BEVERAGES, 10);
//
//        shopSupplySystem.supplyCurrentProduct("Персик", ProductType.FRUIT, 10);
//        shopSupplySystem.supplyCurrentProduct("Яблкоко", ProductType.FRUIT, 10);
//        shopSupplySystem.supplyCurrentProduct("Дыня", ProductType.FRUIT, 10);
        System.out.println("Продукты доставлены в магазин");
    }
}
