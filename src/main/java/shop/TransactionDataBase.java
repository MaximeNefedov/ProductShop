package shop;

public interface TransactionDataBase {
    Transaction getTransaction(int check);
    void addTransaction(int check, Transaction transaction);
}
