package shop.repositories;

import shop.Transaction;

public interface TransactionRepository {
    Transaction findByCheck(int check);
    void save(int check, Transaction transaction);
}
