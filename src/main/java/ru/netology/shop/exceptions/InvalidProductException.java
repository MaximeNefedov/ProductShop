package ru.netology.shop.exceptions;

public class InvalidProductException extends Exception {
    public InvalidProductException(String message) {
        super(message);
    }
}
