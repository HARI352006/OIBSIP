import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Bank bank = new Bank();

            ATM atm = new ATM(bank);

            atm.setVisible(true);
        });
    }
}