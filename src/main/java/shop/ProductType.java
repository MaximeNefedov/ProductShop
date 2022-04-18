package shop;

public enum  ProductType {
    VEGETABLES("Овощи"),
    FRUIT("Фрукты"),
    MEAT("Мясные продукты"),
    BEVERAGES("Напитки"),
    MILK_PRODUCTS("Молочные продукты"),
    CANDY("Конфеты");

    String description;

    ProductType(String description) {
        this.description = description;
    }
}
