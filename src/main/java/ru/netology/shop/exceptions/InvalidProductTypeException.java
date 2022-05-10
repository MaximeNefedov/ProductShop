package ru.netology.shop.exceptions;

public class InvalidProductTypeException extends Exception {
    public InvalidProductTypeException(String message) {
        super(message);
    }
}
