package shop;

import java.util.HashMap;
import java.util.Map;

public class TransactionsRepository implements TransactionDataBase {

    private static TransactionsRepository instance;

    private Map<Integer, Transaction> transactions = new HashMap<>();

    public static TransactionsRepository getTransactionsRepository() {
        if (instance == null) {
            instance = new TransactionsRepository();
        }
        return instance;
    }

    @Override
    public Transaction getTransaction(int check) {
        return transactions.get(check);
    }

    @Override
    public void addTransaction(int check, Transaction transaction) {
        transactions.put(check, transaction);
    }
}
