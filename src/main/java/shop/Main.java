//        Single Responsibility Principle
//        Класс ProductShop предоставляет покупателю доступ к каталогу своих товаров,
//        возможность осуществлять покупки и производить поиск продуктов, но сама логика
//        этих операций делегируется другим классам.
//        Например, класс Searcher работает только с продуктовым складом, класс ProductAccountant
//        производит бухгалтерский учет в момент поставки товаров, однако тут класс поиска взаимодействует
//        с таблицей учета для более быстрого поиска по товарам на складе
//        Класс TransactionImpl отвечает за осуществление оплаты или за откат транзакции,
//        а класс TransactionsRepository за хранение транзакций в некой базе данных
package shop;

import exceptions.ProductNotFoundException;
import exceptions.ShopOutOfProductException;
import shop.supplierFactory.ProductSupplierFactory;
import shop.supplierFactory.VegetableSupplierFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        supplyProducts();


        System.out.println("Добро пожаловать в продуктовый магазин");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==================== Главное меню ====================" +
                    "\nВведите 1 для отображения каталога товаров" +
                    "\nВведите 2 для поиска продукта по названию" +
                    "\nВведите 0 для выхода" +
                    "\n======================================================");
            int input = scanner.nextInt();
            if (input == 1) {
                boolean b = showCatalog();
                if (b) {
                    continue;
                } else {
                    break;
                }
            } else if (input == 2) {
                boolean b = searchProduct();
                if (!b) {
                    break;
                } else {
                    continue;
                }
            } else {
                break;
            }
        }
    }

    private static boolean showCatalog() {
//        Dependency Inversion Principle
//        Клиент взаимодействует с магазином через специально отведенный интерфейс,
//        не беря в голову то, какая реализация стоит за запросом, то есть вся бизнес-логика магазина
//        скрыта от потребителя
        Shop shop = ProductShop.getProductShop();
        Scanner scanner = new Scanner(System.in);
        shop.showAllProducts();
        System.out.println("\n=============================================" +
                "\nВведите 1 для добавления продукта в корзину по названию" +
                "\nВведите 2 для сортировки товара" +
                "\nВведите 0 для выхода" +
                "\n=============================================");
        int input = scanner.nextInt();
        if (input == 1) {
            boolean b = searchProduct();
            if (!b) {
                return false;
            } else {
                return true;
            }
        } else if (input == 2) {
            boolean b = sortCatalog();
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private static boolean sortCatalog() {
        Shop shop = ProductShop.getProductShop();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=============================================" +
                    "\nВведите 1, чтобы отсортировать список по цене" +
                    "\nВведите 2, чтобы отсортировать список по рейтингу" +
                    "\n=============================================");

            int input = scanner.nextInt();
            shop.sortProducts(input);
            System.out.println("\n=============================================" +
                    "\nВведите 1, если хотите выбрать товар по названию" +
                    "\nВведите 2, если хотите вернуться назад" +
                    "\n=============================================");
            input = scanner.nextInt();
            if (input == 1) {
//                Правило DRY
//                Можно было бы не выносить в отдельный метод логику
//                поиска продукта, а писать ее заново каждый раз, когда потребуется
                boolean b = searchProduct();
                if (!b) {
                    return false;
                } else {
                    return true;
                }
            } else if (input == 2) {
                continue;
            }
        }
    }

    private static boolean searchProduct() {
        Scanner scanner = new Scanner(System.in);
        Shop shop = ProductShop.getProductShop();
        while (true) {
            try {
                System.out.println("Введите название продукта");
                String inputName = scanner.nextLine();
                Product product = shop.search(inputName);
                System.out.println("Введите 1, чтобы добавить товар в корзину" +
                        "\nВведите 2, чтобы найти новый товар");
                int input = scanner.nextInt();
                if (input == 1) {
                    System.out.println("Введите количество товара");
                    int amount = scanner.nextInt();
                    boolean status = shop.addToBasket(product, amount);
                    if (!status) {
                        scanner.nextLine();
                        continue;
                    }
                    System.out.println("Введите 1, чтобы перейти в оформлению заказа" +
                            "\nВведите 2, чтобы продолжить покупки");
                    input = scanner.nextInt();
                    if (input == 1) {
                        boolean b = doPayment();
                        if (!b) {
                            return false;
                        } else {
                            return true;
                        }
                    } else if (input == 2) {
                        scanner.nextLine();
                        continue;
                    } else {
                        System.out.println("Введены некорректные данные");
                    }
                } else if (input == 2) {
                    scanner.nextLine();
                    continue;
                }
            } catch (ProductNotFoundException e) {
                System.out.println("Товар не найден");
            } catch (ShopOutOfProductException e) {
                System.out.println("Товар закончился");
            }
        }
    }

    private static boolean doPayment() {
        Shop shop = ProductShop.getProductShop();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Ваша корзина:");
            boolean status = shop.showBasket();
            if (!status) {
                System.out.println("Введите 0, чтобы вернуться в главное меню магазина" +
                        "\nВведите 1, чтобы завершить программу");
                int input = scanner.nextInt();
                if (input == 0) {
                    return true;
                } else if (input == 1) {
                    return false;
                }
            }
            System.out.println("Введите 1, чтобы удалить элемент из корзины" +
                    "\nВведите 2, чтобы произвести оплату");
            int input = scanner.nextInt();
            if (input == 1) {
                System.out.println("Введите название продукта, который хотите удалить");
                scanner.nextLine();
                String name = scanner.nextLine();
                System.out.println("Введите количество продукта");
                int amount = scanner.nextInt();
                boolean isNotEmpty = shop.deleteFromBasket(name, amount);
                if (isNotEmpty) {
                    System.out.println("Продукт удален");
                }
            } else if (input == 2) {
                try {
                    int check = shop.buy();
                    if (check == Transaction.ERROR_CODE) {
                        System.out.println("Ошибка Оплаты");
                        continue;
                    }
                    System.out.println("Ваш чек: " + check);
                    System.out.println("Введите 0, чтобы завершить программу" +
                            "\nВведите 1, чтобы вернуть деньги");
                    input = scanner.nextInt();
                    if (input == 1) {
                        System.out.println("Предъявите, пожалуйста, чек");
                        input = scanner.nextInt();
                        shop.backProduct(input);
                    } else if (input == 0) {
                        return false;
                    }
                } catch (ShopOutOfProductException | ProductNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void supplyProducts() {
//        Product product1 = new Product("Lindor", ProductType.CANDY, 234, 10);
//        Product product2 = new Product("7 UP", ProductType.BEVERAGES, 404, 20);
//        Product product3 = new Product("Мираторг", ProductType.MEAT, 1440, 50);
//        Product product4 = new Product("Овощи вкусные", ProductType.VEGETABLES, 100, 20);
//        Product product5 = new Product("Северное мясо", ProductType.MEAT, 2440, 60);
//        Product product6 = new Product("Вкусное мясо", ProductType.MEAT, 3500, 90);
//        Product product7 = new Product("Pepsi", ProductType.BEVERAGES, 47, 40);
//        Product product8 = new Product("Alpen Gold", ProductType.CANDY, 100, 20);
//
//        AbleToBeSupplied ableToBeSupplied = ProductShop.getProductShop();
//        Supplier supplier = new ProductSupplier(ableToBeSupplied);
//        supplier.addProduct(product1, 10);
//        supplier.addProduct(product2, 5);
//        supplier.addProduct(product3, 10);
//        supplier.addProduct(product4, 100);
//        supplier.addProduct(product5, 1);
//        supplier.addProduct(product6, 10);
//        supplier.addProduct(product7, 20);
//        supplier.addProduct(product8, 20);


//        На данном примере я продемонстрирую сразу два принципа SOLID:
//        1) Open-closed principle
//        Класс ProductSupplier расширяется классом VegetableSupplier без изменения кода родителя
//        2) Liskov substitution principle
//        Я использую метод supplyBySpecificSupplier для демонстрации принципа подмены родителя потомком
//        в данном примере магазин овощей является частным случаем магазина всех продуктов
        Product product1 = new Product("Lindor", ProductType.CANDY, 234, 10);
        Product product2 = new Product("7 UP", ProductType.BEVERAGES, 404, 20);
        Product product3 = new Product("Мираторг", ProductType.MEAT, 1440, 50);
        Product product4 = new Product("Овощи вкусные", ProductType.VEGETABLES, 100, 25);
        Product product5 = new Product("Овощи", ProductType.VEGETABLES, 200, 40);
        Product product6 = new Product("Овощи очень вкусные", ProductType.VEGETABLES, 60, 30);

        //        Dependency Inversion Principle
//        Аналогично покупателю, поставщик работает с магазином через специально отведенный интерфейс
//        за счет чего, от него скрыты такие ненужные методы, как: покупка, добавление в корзину и тд
        AbleToBeSupplied shopForSupplier = ProductShop.getProductShop();
        Supplier supplier = new ProductSupplierFactory(shopForSupplier)
                                .getSupplier();
        Supplier vegetableSupplier = new VegetableSupplierFactory(shopForSupplier)
                                        .getSupplier();

        supplyBySpecificSupplier(supplier, product1, 5);
        supplyBySpecificSupplier(vegetableSupplier, product1, 5);
        supplyBySpecificSupplier(vegetableSupplier, product6, 15);
        supplyBySpecificSupplier(vegetableSupplier, product4, 5);

//        Shop shop = (Shop) shopForSupplier;
//        shop.showAllProducts();
    }

    private static void supplyBySpecificSupplier(Supplier supplier, Product product, int amount) {
        supplier.addProduct(product, amount);
    }
}
