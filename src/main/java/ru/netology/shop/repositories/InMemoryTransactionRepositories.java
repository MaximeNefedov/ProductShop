package ru.netology.shop.repositories;

import ru.netology.shop.Transaction;

import java.util.HashMap;
import java.util.Map;

// Singleton (реализация с использованием внутреннего класса)
public class InMemoryTransactionRepositories {
    public static TransactionRepository getDefault() {
        return DefaultInMemoryTransactionRepository.INSTANCE;
    }

    private static class DefaultInMemoryTransactionRepository implements TransactionRepository {
        private static final DefaultInMemoryTransactionRepository INSTANCE = new DefaultInMemoryTransactionRepository();
        private final Map<Integer, Transaction> transactions = new HashMap<>();

        @Override
        public Transaction findByCheck(int check) {
            return transactions.get(check);
        }

        @Override
        public void save(int check, Transaction transaction) {
            transactions.put(check, transaction);
        }
    }
}
