import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ATM extends JFrame {

    private final Bank bank;
    private Account currentAccount;

    private JLabel balanceLabel;
    private JLabel welcomeLabel;

    public ATM(Bank bank) {

        this.bank = bank;

        setTitle("Smart ATM System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        showLogin();
    }

    private void showLogin() {

        JPanel mainPanel = new JPanel(
                new GridBagLayout()
        );

        mainPanel.setBackground(
                UIManager.getColor("Panel.background")
        );

        JPanel form = new JPanel(
                new GridBagLayout()
        );

        form.setPreferredSize(
                new Dimension(430, 430)
        );

        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIManager.getColor(
                                        "Component.border"
                                ),
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                35,
                                45,
                                35,
                                45
                        )
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(
                6,
                5,
                6,
                5
        );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel(
                "SMART ATM",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;

        form.add(
                title,
                gbc
        );

        JLabel subtitle = new JLabel(
                "Secure Banking System",
                SwingConstants.CENTER
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        gbc.gridy = 1;

        form.add(
                subtitle,
                gbc
        );

        gbc.gridy = 2;

        form.add(
                Box.createVerticalStrut(20),
                gbc
        );

        JLabel userLabel = new JLabel(
                "User ID"
        );

        userLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor =
                GridBagConstraints.WEST;

        form.add(
                userLabel,
                gbc
        );

        JTextField userField =
                new JTextField();

        userField.setPreferredSize(
                new Dimension(
                        300,
                        38
                )
        );

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor =
                GridBagConstraints.CENTER;

        form.add(
                userField,
                gbc
        );

        JLabel pinLabel = new JLabel(
                "PIN"
        );

        pinLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor =
                GridBagConstraints.WEST;

        form.add(
                pinLabel,
                gbc
        );

        JPasswordField pinField =
                new JPasswordField();

        pinField.setPreferredSize(
                new Dimension(
                        300,
                        38
                )
        );

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor =
                GridBagConstraints.CENTER;

        form.add(
                pinField,
                gbc
        );

        JLabel message = new JLabel(
                " ",
                SwingConstants.CENTER
        );

        message.setForeground(
                Color.RED
        );

        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        form.add(
                message,
                gbc
        );

        JButton loginButton =
                new JButton(
                        "LOGIN"
                );

        loginButton.setPreferredSize(
                new Dimension(
                        130,
                        40
                )
        );

        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor =
                GridBagConstraints.CENTER;

        form.add(
                loginButton,
                gbc
        );

        JLabel demo = new JLabel(
                "Demo: user01 / 1234",
                SwingConstants.CENTER
        );

        demo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        form.add(
                demo,
                gbc
        );

        final int[] attempts = {0};

        loginButton.addActionListener(e -> {

            String userId =
                    userField
                            .getText()
                            .trim();

            String pin =
                    new String(
                            pinField
                                    .getPassword()
                    ).trim();

            if (userId.isEmpty()
                    || pin.isEmpty()) {

                message.setText(
                        "Please enter User ID and PIN."
                );

                return;
            }

            Account account =
                    bank.login(
                            userId,
                            pin
                    );

            if (account != null) {

                currentAccount = account;

                showDashboard();

            } else {

                attempts[0]++;

                int remaining =
                        3 - attempts[0];

                if (remaining > 0) {

                    message.setText(
                            "Invalid login. "
                                    + remaining
                                    + " attempt(s) remaining."
                    );

                } else {

                    loginButton.setEnabled(false);

                    message.setText(
                            "Access denied."
                    );
                }
            }
        });

        mainPanel.add(form);

        setContentPane(mainPanel);

        revalidate();
        repaint();
    }

    private void showDashboard() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        25,
                        25
                )
        );

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        JLabel title =
                new JLabel(
                        "SMART ATM"
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        welcomeLabel =
                new JLabel(
                        "Welcome"
                );

        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JButton logout =
                new JButton(
                        "LOGOUT"
                );

        logout.addActionListener(
                e -> {
                    currentAccount = null;
                    showLogin();
                }
        );

        JPanel heading =
                new JPanel();

        heading.setLayout(
                new BoxLayout(
                        heading,
                        BoxLayout.Y_AXIS
                )
        );

        heading.add(title);

        heading.add(
                Box.createVerticalStrut(5)
        );

        heading.add(welcomeLabel);

        header.add(
                heading,
                BorderLayout.WEST
        );

        header.add(
                logout,
                BorderLayout.EAST
        );

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        JPanel center =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        JPanel balancePanel =
                new JPanel();

        balancePanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Account Details"
                )
        );

        balancePanel.setLayout(
                new BoxLayout(
                        balancePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel balanceTitle =
                new JLabel(
                        "Available Balance"
                );

        balanceTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        balanceLabel =
                new JLabel();

        balanceLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        JLabel accountLabel =
                new JLabel(
                        "Account: "
                                + currentAccount
                                .getAccountId()
                );

        balancePanel.add(
                balanceTitle
        );

        balancePanel.add(
                Box.createVerticalStrut(8)
        );

        balancePanel.add(
                balanceLabel
        );

        balancePanel.add(
                Box.createVerticalStrut(5)
        );

        balancePanel.add(
                accountLabel
        );

        updateBalance();

        center.add(
                balancePanel,
                BorderLayout.NORTH
        );

        JPanel buttons =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                15,
                                15
                        )
                );

        JButton withdraw =
                new JButton(
                        "WITHDRAW"
                );

        JButton deposit =
                new JButton(
                        "DEPOSIT"
                );

        JButton transfer =
                new JButton(
                        "TRANSFER"
                );

        JButton history =
                new JButton(
                        "TRANSACTION HISTORY"
                );

        withdraw.addActionListener(
                e -> withdraw()
        );

        deposit.addActionListener(
                e -> deposit()
        );

        transfer.addActionListener(
                e -> transfer()
        );

        history.addActionListener(
                e -> showHistory()
        );

        buttons.add(withdraw);
        buttons.add(deposit);
        buttons.add(transfer);
        buttons.add(history);

        center.add(
                buttons,
                BorderLayout.CENTER
        );

        mainPanel.add(
                center,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);

        revalidate();
        repaint();
    }

    private void withdraw() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter withdrawal amount:",
                        "Withdraw",
                        JOptionPane.PLAIN_MESSAGE
                );

        if (input == null) {
            return;
        }

        try {

            double amount =
                    Double.parseDouble(
                            input
                    );

            if (amount <= 0) {

                showError(
                        "Amount must be greater than zero."
                );

                return;
            }

            if (amount >
                    currentAccount.getBalance()) {

                showError(
                        "Insufficient Funds."
                );

                return;
            }

            currentAccount.withdraw(
                    amount
            );

            currentAccount.addTransaction(
                    new Transaction(
                            "WITHDRAW",
                            amount,
                            "Cash withdrawal"
                    )
            );

            updateBalance();

            showSuccess(
                    "Withdrawal successful.\n\n"
                            + "Amount: ₹"
                            + money(amount)
                            + "\nBalance: ₹"
                            + money(
                            currentAccount
                                    .getBalance()
                    )
            );

        } catch (NumberFormatException e) {

            showError(
                    "Enter a valid amount."
            );
        }
    }

    private void deposit() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter deposit amount:",
                        "Deposit",
                        JOptionPane.PLAIN_MESSAGE
                );

        if (input == null) {
            return;
        }

        try {

            double amount =
                    Double.parseDouble(
                            input
                    );

            if (amount <= 0) {

                showError(
                        "Amount must be greater than zero."
                );

                return;
            }

            currentAccount.deposit(
                    amount
            );

            currentAccount.addTransaction(
                    new Transaction(
                            "DEPOSIT",
                            amount,
                            "Cash deposit"
                    )
            );

            updateBalance();

            showSuccess(
                    "Deposit successful.\n\n"
                            + "Amount: ₹"
                            + money(amount)
                            + "\nBalance: ₹"
                            + money(
                            currentAccount
                                    .getBalance()
                    )
            );

        } catch (NumberFormatException e) {

            showError(
                    "Enter a valid amount."
            );
        }
    }

    private void transfer() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                10,
                                10
                        )
                );

        JTextField accountField =
                new JTextField();

        JTextField amountField =
                new JTextField();

        panel.add(
                new JLabel(
                        "Recipient Account:"
                )
        );

        panel.add(
                accountField
        );

        panel.add(
                new JLabel(
                        "Amount:"
                )
        );

        panel.add(
                amountField
        );

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Transfer Money",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (option !=
                JOptionPane.OK_OPTION) {

            return;
        }

        String recipientId =
                accountField
                        .getText()
                        .trim();

        Account recipient =
                bank.findAccount(
                        recipientId
                );

        if (recipient == null) {

            showError(
                    "Recipient account not found."
            );

            return;
        }

        if (recipient.getAccountId()
                .equalsIgnoreCase(
                        currentAccount
                                .getAccountId()
                )) {

            showError(
                    "You cannot transfer to your own account."
            );

            return;
        }

        try {

            double amount =
                    Double.parseDouble(
                            amountField
                                    .getText()
                                    .trim()
                    );

            if (amount <= 0) {

                showError(
                        "Amount must be greater than zero."
                );

                return;
            }

            if (amount >
                    currentAccount.getBalance()) {

                showError(
                        "Insufficient Funds."
                );

                return;
            }

            currentAccount.withdraw(
                    amount
            );

            recipient.deposit(
                    amount
            );

            currentAccount.addTransaction(
                    new Transaction(
                            "TRANSFER OUT",
                            amount,
                            "Transferred to "
                                    + recipient
                                    .getAccountId()
                    )
            );

            recipient.addTransaction(
                    new Transaction(
                            "TRANSFER IN",
                            amount,
                            "Received from "
                                    + currentAccount
                                    .getAccountId()
                    )
            );

            updateBalance();

            showSuccess(
                    "Transfer successful.\n\n"
                            + "Amount: ₹"
                            + money(amount)
                            + "\nRecipient Account: "
                            + recipient.getAccountId()
                            + "\nBalance: ₹"
                            + money(
                            currentAccount
                                    .getBalance()
                    )
            );

        } catch (NumberFormatException e) {

            showError(
                    "Enter a valid amount."
            );
        }
    }

    private void showHistory() {

        ArrayList<Transaction> list =
                currentAccount
                        .getTransactions();

        if (list.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No transactions available.",
                    "Transaction History",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        StringBuilder history =
                new StringBuilder();

        history.append(
                "TRANSACTION HISTORY\n"
        );

        history.append(
                "====================================\n\n"
        );

        for (Transaction transaction :
                list) {

            String sign =
                    transaction
                            .getType()
                            .equals("DEPOSIT")
                            || transaction
                            .getType()
                            .equals("TRANSFER IN")
                            ? "+"
                            : "-";

            history.append(
                    transaction
                            .getDateTime()
            );

            history.append("\n");

            history.append(
                    transaction
                            .getType()
            );

            history.append(
                    "    "
                            + sign
                            + " ₹"
                            + money(
                            transaction
                                    .getAmount()
                    )
            );

            history.append("\n");

            history.append(
                    transaction
                            .getDescription()
            );

            history.append(
                    "\n\n"
            );
        }

        history.append(
                "====================================\n"
        );

        history.append(
                "Current Balance: ₹"
                        + money(
                        currentAccount
                                .getBalance()
                )
        );

        JTextArea area =
                new JTextArea(
                        history.toString()
                );

        area.setEditable(false);

        area.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );

        JScrollPane scroll =
                new JScrollPane(area);

        scroll.setPreferredSize(
                new Dimension(
                        600,
                        400
                )
        );

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Transaction History",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateBalance() {

        if (balanceLabel != null) {

            balanceLabel.setText(
                    "₹"
                            + money(
                            currentAccount
                                    .getBalance()
                    )
            );
        }
    }

    private void showSuccess(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showError(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "ATM",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private String money(double amount) {

        return String.format(
                "%.2f",
                amount
        );
    }
}