package ru.netology.shop;

import ru.netology.shop.client.ProductShopClient;
import ru.netology.shop.collectors.WarehouseCollector;
import ru.netology.shop.entites.Product;
import ru.netology.shop.entites.ProductBasket;
import ru.netology.shop.entites.ProductType;
import ru.netology.shop.repositories.*;

import java.math.BigDecimal;
import java.util.*;

public class ProductShopImpl implements ProductShop, ProductConsumer {
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;
    private final ProductShopClientRepository productShopClientRepository;
    private final Map<String, ProductShopClient> activeProductShopClients;
    private int transactionIdCounter;

    public ProductShopImpl() {
        this.productRepository = new InMemoryProductRepository();
        this.transactionRepository = InMemoryTransactionRepositories.getDefault();
        this.productShopClientRepository = new InMemoryProductShopClientRepository();
        this.activeProductShopClients = new HashMap<>();
    }

    // ProductConsumer
    public void receiveCurrentProduct(String productName, List<Product> products) {
        productRepository.saveAllByName(productName, products);
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
                        String.format("%d)%s, количество: %d шт., цена: %s, рейтинг: %d\n",
                                ++productsCounter, product.getName(), productCount, product.getPrice(), product.getRating()
                        )
                );
            }
        }
        System.out.println(stringBuilder);
    }

    @Override
    public int pay(String clientName) {
        ProductShopClient productShopClient = activeProductShopClients.get(clientName);
        ProductBasket productBasket = productShopClient.getProductBasket();
        Transaction transaction = new Transaction(++transactionIdCounter, productRepository, productShopClient);
        transaction.pay();
        transactionRepository.save(transaction.getId(), transaction);
        System.out.println(transaction);
        productBasket.clear();
        return transaction.getId();
    }

    @Override
    public void refund(int check) {
        Transaction transaction = transactionRepository.findByCheck(check);
        if (transaction == null) {
            System.out.println("Недействительный чек");
        } else if (transaction.getStatus() == Transaction.TransactionStatus.ROLLED_BACK) {
            System.out.println("Откат транзакции невозможен");
        } else {
            transaction.rollback();
        }
    }

    @Override
    public List<Product> findProduct(String name, int amount) {
        // Список, в котором содержатся все имющиеся продукты с данным названием
        List<Product> products = productRepository.findByName(name);
        if (products == null) {
            System.out.printf("Товар с именем \"%s\" не найден%n", name);
            products = Collections.emptyList();
        } else {
            int availableAmount = products.size();
            if (availableAmount < amount) {
                System.out.printf("В магазине доступно только %d единиц товара \"%s\"%n",
                        availableAmount, name);
                products = Collections.emptyList();
            } else {
                products = new ArrayList<>(products.subList(0, amount));
            }
        }
        return products;
    }

    @Override
    public ProductShopClient getClient(String login, String password) {
        // Узнать, активен ли все еще пользователь
        ProductShopClient productShopClient = activeProductShopClients.get(login);
        if (productShopClient == null) {
            // Если нет, то найти клиента в репозитории
            ProductShopClient clientFromRepository = productShopClientRepository.findByName(login);
            if (clientFromRepository == null) {
                // Если клиент не найден - создать нового
                ProductShopClient newClient = ProductShopClient.builder()
                        .login(login)
                        .password(password)
//                        .balance(BigDecimal.ZERO)
                        .balance(new BigDecimal("10000"))
                        .productBasket(new ProductBasket())
                        .productShop(this)
                        .build();
                // Сохранить его в активных пользователях
                productShopClientRepository.save(newClient);
                // Добавить в репозиторий
                activeProductShopClients.put(login, newClient);
                return newClient;
            } else if (clientFromRepository.getPassword().equals(password)) {
                // Если клиент найден в репозитории - проверить совпадение паролей
                return clientFromRepository;
            } else {
                // Если пароли не совпали - выбросить исключение
                throw new RuntimeException();
            }
        } else {
            // Если пользователь все еще активен - просто вернуть его
            return productShopClient;
        }
    }
}
