package shop.shop;

import client.ProductShopClient;
import shop.Transaction;
import shop.collectors.WarehouseCollector;
import shop.entites.Product;
import shop.entites.ProductBasket;
import shop.entites.ProductType;
import shop.repositories.*;

import java.util.*;

public class ProductShopImpl implements ProductShop, ProductConsumer {
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final ProductShopClientRepository productShopClientRepository;
    private final List<ProductShopClient> activeProductShopClients;
//    private final Map<Integer, ProductBasket> productBaskets;
    private int basketIdCounter;
    private ProductBasket productBasket;

    public ProductShopImpl() {
        this.productRepository = new InMemoryProductRepository();
        this.transactionRepository = InMemoryTransactionRepositories.getDefault();
        this.productShopClientRepository = new InMemoryProductShopClientRepository();
//        this.productBaskets = new HashMap<>();
        this.basketIdCounter = 0;
        this.activeProductShopClients = new ArrayList<>();
    }

    // ProductConsumer
    @Override
    public void receiveProducts(List<Product> products) {
        productRepository.saveAll(products);
    }

    public void showAllProducts(SortType sortType) {
        Map<String, List<Product>> warehouse = productRepository.getWarehouse();
        Map<ProductType, Map<Product, Integer>> warehouseMap = warehouse.entrySet().stream().collect(new WarehouseCollector(sortType));
        StringBuilder stringBuilder = new StringBuilder();
        for (var warehouseEntry : warehouseMap.entrySet()) {
            ProductType productType = warehouseEntry.getKey();
            stringBuilder.append(productType.getDescription()).append(":").append("\n");
            int productsCounter = 0;
            for (var productInfoEntry : warehouseEntry.getValue().entrySet()) {
                Product product = productInfoEntry.getKey();
                Integer productCount = productInfoEntry.getValue();
                stringBuilder.append(
                        String.format("%d)%s, количество: %d шт., цена: %d, рейтинг: %d\n",
                                ++productsCounter, product.getName(), productCount, product.getPrice(), product.getRating()
                        )
                );
            }
        }
        System.out.println(stringBuilder);
    }

    // ProductShop
    @Override
    public int buy() {
//        System.out.println("Для соверешения покупки Вам необходимо ввести свои данные:");
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Введите имя: ");
//        String name = scanner.nextLine();
//        System.out.println("Введите Ваш бюджет");
//        int sum = scanner.nextInt();
//        User user = new User(name, sum);
//        Transaction transaction = new Transaction();
//        return transaction.pay(productBasket, user);
        return 0;
    }

    @Override
    public void backProduct(int check) {
        Transaction transaction = new Transaction();
        transaction.backMoney(check);
    }

    @Override
    public Optional<Product> search(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public int createBasket(int basketId) {
//        int basketId = basketIdCounter++;
//
//        productBaskets.put(basketId, new ProductBasket(id));
//        return basketId;
        return 0;
    }

    @Override
    public boolean addToBasket(int basketId, Product product, int amount) {
//        if (productBasket == null) {
//            productBasket = new ProductBasket(id);
//        }
//        return productBasket.add(product, amount);
        return false;
    }

    @Override
    public boolean deleteFromBasket(int basketId, String name, int amount) {
        return productRepository.removeProduct(name, amount);
    }

    @Override
    public boolean showBasket(int basketId) {
//        return productBaskets.get(basketId).show();
    }


    @Override
    public ProductShopClient getClient(String login, String password) {
        ProductShopClient client = productShopClientRepository.findByName(login);
        if (client == null) {
            
        } else if (client.getPassword().equals(password)) {

        } else {
            throw new RuntimeException();
        }
        return null;
    }
}
