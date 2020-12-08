package Shop;
import Exceptions.ProductNotFoundException;
import Exceptions.ShopOutOfProductException;

import java.util.*;

public class ProductShop implements Shop, AbleToBeSupplied {

//    Interface Segregation Principle
//    Я посчитал, что не следует дробить интерфейс Shop на несколько частей, так как думаю, что каждый магазин обязан
//    иметь данные методы для взаимодействия с пользователем, однако, если речь идет по поставке товаров, то пользователю
//    явно не нужно знать, что магазин еще и может предоставлять доступ к продуктовому складу для поставки продуктов
//
//    Отсюда следуюет еще один принцип: Dependency Inversion Principle.
//    Покупатель и поставщик товара работают с продуктовым магазином по разным контрактам

    private static ProductShop instance;
    private Map<ProductType, Map<String, List<Product>>> warehouse;
    private ProductBasket productBasket;

    private ProductShop() {
        warehouse = new HashMap<>();
    }


    public static ProductShop getProductShop() {
        if (instance == null) {
            instance = new ProductShop();
        }
        return instance;
    }

    public Map<ProductType, Map<String, List<Product>>> getWarehouse() {
        return warehouse;
    }

    public void showAllProducts() {
        Set<Map.Entry<ProductType, Map<String, List<Product>>>> entries = warehouse.entrySet();
        for (Map.Entry<ProductType, Map<String, List<Product>>> entry : entries) {
            System.out.println(entry.getKey().description + ":");
            Map<String, List<Product>> value = entry.getValue();
            Set<Map.Entry<String, List<Product>>> entries1 = value.entrySet();
            for (Map.Entry<String, List<Product>> stringListEntry : entries1) {
                stringListEntry.getKey();
                List<Product> value1 = stringListEntry.getValue();
                int counter = 0;
                for (Product product : value1) {
                    counter++;
                    if (counter == value1.size()) {
                        System.out.println(product + ", Количество товара: " + counter + " шт");
                    }
                }
            }
        }
    }

    @Override
    public void sortProducts(int x) {
        ProductSorter sorter = new ProductSorter();
        sorter.sort(x);
    }

    @Override
    public int buy() throws ShopOutOfProductException, ProductNotFoundException {
        System.out.println("Для соверешения покупки Вам необходимо ввести свои данные:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();
        System.out.println("Введите Ваш бюджет");
        int sum = scanner.nextInt();
        User user = new User(name, sum);
        Transaction transaction = new Transaction();
        return transaction.pay(productBasket, user);
    }

    @Override
    public void backProduct(int check) {
        Transaction transaction = new Transaction();
        transaction.backMoney(check);
    }

    @Override
    public Product search(String name) throws ShopOutOfProductException, ProductNotFoundException {
        if (warehouse.isEmpty()) {
            System.out.println("Магазин пуст");
        }
        Searcher searcher = SearcherImpl.getSearcher();
        return searcher.searchProduct(name);
    }

    @Override
    public boolean addToBasket(Product product, int amount) throws ProductNotFoundException {
        if (productBasket == null) {
            productBasket = new ProductBasket();
        }
        return productBasket.add(product, amount);
    }

    @Override
    public boolean deleteFromBasket(String name, int amount) {
        return productBasket.delete(name, amount);
    }

    @Override
    public boolean showBasket() {
        return productBasket.show();
    }
}
