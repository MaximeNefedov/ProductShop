package ru.netology.shop.client;

import ru.netology.shop.entites.Product;
import ru.netology.shop.entites.ProductBasket;
import ru.netology.shop.ProductShop;
import ru.netology.shop.SortType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ProductShopClient {
    private final String login;
    private final String password;
    private final Scanner scanner;
    private final ProductShop productShop;
    private final ProductBasket productBasket;
    private BigDecimal balance;

    private ProductShopClient(String login,
                              String password,
                              ProductShop productShop,
                              ProductBasket productBasket,
                              BigDecimal balance) {
        this.login = login;
        this.password = password;
        this.productShop = productShop;
        this.scanner = new Scanner(System.in);
        this.productBasket = productBasket;
        this.balance = balance;
    }

    public static ProductShopClientBuilder builder() {
        return new ProductShopClientBuilder();
    }

    public static class ProductShopClientBuilder {
        private String login;
        private String password;
        private ProductShop productShop;
        private BigDecimal balance;

        public ProductShopClientBuilder login(String login) {
            this.login = login;
            return this;
        }

        public ProductShopClientBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ProductShopClientBuilder productShop(ProductShop productShop) {
            this.productShop = productShop;
            return this;
        }

        public ProductShopClientBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public ProductShopClient build() {
            validateFields();
            return new ProductShopClient(login, password, productShop, new ProductBasket(), balance);
        }

        private void validateFields() {
            if (login == null || login.isEmpty()) throw new IllegalArgumentException("Некорректный логин");
            if (password == null || password.isEmpty()) throw new IllegalArgumentException("Некорректный пароль");
        }
    }

    public void start() {
        viewMainPage();
    }

    public void close() {
        productShop.closeClient(login);
    }

    private void viewMainPage() {
        System.out.println("Добро пожаловать в продуктовый магазин, Ваш баланс: " + balance.doubleValue());
        boolean isClosed = false;
        while (!isClosed) {
            System.out.println("\n==================== Главное меню ====================" +
                    "\nВведите 1 для отображения всех товаров" +
                    "\nВведите 2 для поиска продукта по названию" +
                    "\nВведите 3 для отображения вашей корзины" +
                    "\nВведите 4 для возврата товара по чеку" +
                    "\nВведите 5 для пополнения счета" +
                    "\nВведите 0 для выхода" +
                    "\n======================================================");
            int input = scanner.nextInt();
            switch (input) {
                case 0 -> isClosed = true;
                case 1 -> viewAllProducts();
                case 2 -> findProduct();
                case 3 -> viewBasketPage();
                case 4 -> {
                    System.out.println("Введите номер чека для возврата товара");
                    int check = scanner.nextInt();
                    productShop.refund(check);
                    System.out.println("Ваш баланс: " + balance);
                }
                case 5 -> updateBalance();
                default -> System.out.println("Введите корректный нормер операции");
            }
        }
    }

    private void viewAllProducts() {
        System.out.println("Ваш баланс: " + balance +
                "\n===================Каталог товаров===================");
        productShop.showAllProducts(SortType.NONE);
        while (true) {
            System.out.println("===================Каталог товаров===================\n" +
                    "Введите 1 для добавления продукта в корзину по названию\n" +
                    "Введите 2 для сортировки товара по цене (по возрастанию)\n" +
                    "Введите 3 для сортировки товара по цене (по убыванию)\n" +
                    "Введите 4 для сортировки товара по рейтингу (по возрастанию)\n" +
                    "Введите 5 для сортировки товара по рейтингу (по убыванию)\n" +
                    "Введите 6 для сортировки товара по количеству (по возрастанию)\n" +
                    "Введите 7 для сортировки товара по количеству (по убыванию)\n" +
                    "Введите 0 для выхода в главное меню\n" +
                    "=============================================");
            int input = scanner.nextInt();
            switch (input) {
                case 0 -> viewMainPage();
                case 1 -> findProduct();
                case 2 -> productShop.showAllProducts(SortType.BY_PRICE_ASC);
                case 3 -> productShop.showAllProducts(SortType.BY_PRICE_DESC);
                case 4 -> productShop.showAllProducts(SortType.BY_RATING_ASC);
                case 5 -> productShop.showAllProducts(SortType.BY_RATING_DESC);
                case 6 -> productShop.showAllProducts(SortType.BY_COUNT_ASC);
                case 7 -> productShop.showAllProducts(SortType.BY_COUNT_DESC);
                default -> System.out.println("Введите корректный номер операции");
            }
        }
    }

    private void findProduct() {
        while (true) {
            System.out.println("Ваш баланс: " + balance +
                    "\n===================Поиск товаров===================\n" +
                    "Введите 1, чтобы найти товар по названию\n" +
                    "Введите 2, чтобы вернуться к каталогу товаров\n" +
                    "Введите 0, чтобы выйти в главное меню");
            scanner.nextLine();
            int input = scanner.nextInt();
            switch (input) {
                case 0 -> viewMainPage();
                case 1 -> {
                    System.out.println("Введите название товара:");
                    scanner.nextLine();
                    String productName = scanner.nextLine();
                    if (validateProductName(productName)) {
                        System.out.println("Введите количество товара:");
                        int amount = scanner.nextInt();
                        if (validateProductAmount(amount)) {
                            List<Product> products = productShop.findProduct(productName, amount);
                            if (products.isEmpty()) {
                                continue;
                            }
                            System.out.println("===================Товар===================\n" +
                                    productName + ", количество: " + products.size() + " шт." +
                                    "\n===================Товар===================");

                            System.out.println("Введите 1, чтобы добавить товар в корзину" +
                                    "\nВведите 2, чтобы найти новый товар");
                            input = scanner.nextInt();
                            if (input == 1) {
                                productBasket.add(products);
                                System.out.println("Введите 1, чтобы перейти в оформлению заказа" +
                                        "\nВведите 2, чтобы продолжить покупки");
                                input = scanner.nextInt();
                                if (input == 1) {
                                    viewBasketPage();
                                }
                            }
                        }
                    }
                }
                case 2 -> viewAllProducts();
                default -> System.out.println("Введите корректный номер операции");
            }
        }
    }

    private void viewBasketPage() {
        while (true) {
            System.out.println("Ваш баланс: " + balance +
                    "\n===================Продуктовая корзина===================\n" +
                    productBasket +
                    "\n===================Продуктовая корзина===================\n" +
                    "Введите 1, чтобы оплатить товары\n" +
                    "Введите 2, чтобы удалить товар из корзины\n" +
                    "Введите 3, чтобы пополнить счет\n" +
                    "Введите 0, чтобы выйти в главное меню");
            int input = scanner.nextInt();
            switch (input) {
                case 0 -> viewMainPage();
                case 1 -> {
                    if (!productBasket.isEmpty()) {
                        viewPaymentPage();
                    } else {
                        System.out.println("Корзина пуста");
                    }
                }
                case 2 -> {
                    if (!productBasket.isEmpty()) {
                        System.out.println("Введите название товара");
                        scanner.nextLine();
                        String productName = scanner.nextLine();
                        if (validateProductName(productName)) {
                            System.out.println("Сколько единиц данного продукта Вы хотите удалить?");
                            int amount = scanner.nextInt();
                            if (validateProductAmount(amount)) {
                                productBasket.remove(productName, amount);
                            }
                        }
                    } else {
                        System.out.println("Корзина пуста");
                    }
                }
                case 3 -> updateBalance();
            }
        }
    }

    private void updateBalance() {
        scanner.nextLine();
        System.out.println("Введите сумму");
        try {
            double sum = Double.parseDouble(scanner.nextLine());
            productShop.updateClientBalance(login, new BigDecimal(sum));
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод");
        }
    }

    private void viewPaymentPage() {
        BigDecimal totalPrice = productBasket.getTotalPrice();
        if (balance.compareTo(totalPrice) >= 0) {
            int check = productShop.pay(login);
            System.out.println("Оплата усеществлена, ваш чек: " + check);
        } else {
            System.out.println("Недостаточно средств для соевршения платежа");
        }
    }

    private boolean validateProductName(String productName) {
        boolean validationStatus = false;
        if (productName == null || productName.isEmpty()) {
            System.out.println("Указано недопустимое название продукта");
        } else {
            validationStatus = true;
        }
        return validationStatus;
    }

    private boolean validateProductAmount(int amount) {
        boolean validationStatus = false;
        if (amount <= 0) {
            System.out.println("Указано недопустимое количество продукта");
        } else {
            validationStatus = true;
        }
        return validationStatus;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public ProductBasket getProductBasket() {
        return productBasket;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
