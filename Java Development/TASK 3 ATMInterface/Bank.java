import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accounts;

    public Bank() {

        accounts = new ArrayList<>();

        accounts.add(
                new Account(
                        "ACC1001",
                        "user01",
                        "1234",
                        "Arun Kumar",
                        10000.00
                )
        );

        accounts.add(
                new Account(
                        "ACC1002",
                        "user02",
                        "5678",
                        "Rahul Raj",
                        8000.00
                )
        );

        accounts.add(
                new Account(
                        "ACC1003",
                        "user03",
                        "2468",
                        "Karthik S",
                        15000.00
                )
        );
    }

    public Account login(String userId, String pin) {

        for (Account account : accounts) {

            if (account.getUserId().equals(userId)
                    && account.getPin().equals(pin)) {

                return account;
            }
        }

        return null;
    }

    public Account findAccount(String accountId) {

        for (Account account : accounts) {

            if (account.getAccountId()
                    .equalsIgnoreCase(accountId)) {

                return account;
            }
        }

        return null;
    }
}