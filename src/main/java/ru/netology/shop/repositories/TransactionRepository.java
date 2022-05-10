package ru.netology.shop.repositories;

import ru.netology.shop.Transaction;

public interface TransactionRepository {
    Transaction findByCheck(int check);
    void save(int check, Transaction transaction);
}
