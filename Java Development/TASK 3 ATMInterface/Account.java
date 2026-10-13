import java.util.ArrayList;

public class Account {

    private String accountId;
    private String userId;
    private String pin;
    private String holderName;
    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(String accountId, String userId, String pin,
                   String holderName, double balance) {

        this.accountId = accountId;
        this.userId = userId;
        this.pin = pin;
        this.holderName = holderName;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {

        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}