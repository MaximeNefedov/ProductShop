package ru.netology.shop.entites;

public enum ProductType {
    VEGETABLES("Овощи"),
    FRUIT("Фрукты"),
    MEAT("Мясные продукты"),
    BEVERAGES("Напитки"),
    MILK_PRODUCTS("Молочные продукты"),
    SWEETS("Конфеты");

    private String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
