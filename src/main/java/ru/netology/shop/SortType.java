package ru.netology.shop;

public enum SortType {
    NONE(0),
    BY_PRICE_ASC(1),
    BY_PRICE_DESC(2),
    BY_RATING_ASC(3),
    BY_RATING_DESC(4),
    BY_COUNT_ASC(5),
    BY_COUNT_DESC(6);
    private final int value;

    SortType(int value) {
        this.value = value;
    }
}
