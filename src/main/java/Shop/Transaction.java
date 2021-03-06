package Shop;

import Exceptions.ProductNotFoundException;
import Exceptions.ShopOutOfProductException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Transaction {

    public static final int ERROR_CODE = 999;
    private User client;
    private int sum;

    public Transaction() {

    }

    public int pay(ProductBasket productBasket, User user) throws ShopOutOfProductException, ProductNotFoundException {
        System.out.println(user.getSum());
        if (productBasket.getTotalPrice() <= 0) {
            System.out.println("Оплата покупки невозможна");
            return ERROR_CODE;
        } else {
            int finalSum = user.getSum() - productBasket.getTotalPrice();
            if (finalSum < 0) {
                System.out.println("Недостаточно средств для совершения покупки");
                return ERROR_CODE;
            } else {
                this.sum = productBasket.getTotalPrice();
                this.client = user;
                user.setSum(finalSum);
                Random random = new Random();
                int check = random.nextInt(777);
                TransactionDataBase dataBase = TransactionsRepository.getTransactionsRepository();
                dataBase.addTransaction(check, this);

                WarehouseAdministrator administrator = new WarehouseAdministrator();
                administrator.deleteFromWareHouse(productBasket);

                Map<String, List<Product>> productMap = productBasket.getProductMap();
                user.setProductMap(productMap);
                productBasket.getProductMap().clear();
                productBasket.setTotalPrice(0);
                user.setCheck(check);
                return user.getCheck();
            }
        }
    }

    public void backMoney(int check) {
        TransactionsRepository repository = TransactionsRepository.getTransactionsRepository();
        Transaction transaction = repository.getTransaction(check);
        if (transaction == null) {
            System.out.println("Никаких операций совершено не было");
        } else {
            WarehouseAdministrator administrator = new WarehouseAdministrator();
            administrator.addToWarehouse(transaction.getClient().getProducts());

            int back = transaction.getSum();
            transaction.getClient().setSum(transaction.getClient().getSum() + back);
            System.out.println("Уважаемый, " + transaction.getClient().getName() + "," +
                    " Вам возвращены Ваши денежные средства в размере: " +
                    transaction.getSum() + "\nТекущий баланс: " + transaction.getClient().getSum());
        }
    }

    public User getClient() {
        return client;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "TransactionImpl{" +
                "client=" + client +
                ", sum=" + sum +
                '}';
    }
}
