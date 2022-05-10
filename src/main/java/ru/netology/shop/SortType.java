package shop.shop;

public enum SortType {
    NONE(0), BY_PRICE(1), BY_RATING(2), BY_COUNT(3);
    private final int value;

    SortType(int value) {
        this.value = value;
    }
}
