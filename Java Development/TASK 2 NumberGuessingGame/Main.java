import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Main extends JFrame {

    private enum Difficulty {
        EASY("Easy", 1, 50),
        MEDIUM("Medium", 1, 100),
        HARD("Hard", 1, 200);

        String name;
        int min;
        int max;

        Difficulty(String name, int min, int max) {
            this.name = name;
            this.min = min;
            this.max = max;
        }

        public String toString() {
            return name;
        }
    }

    private final Random random = new Random();

    private Difficulty difficulty = Difficulty.MEDIUM;

    private int secretNumber;
    private int attempts;
    private int round = 1;
    private int wins = 0;
    private int losses = 0;
    private int score = 0;
    private int bestScore = 0;

    private JComboBox<Difficulty> difficultyBox;
    private JTextField guessField;

    private JLabel rangeLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JLabel bestScoreLabel;
    private JLabel accuracyLabel;
    private JLabel resultLabel;
    private JLabel roundLabel;

    private JTextArea historyArea;

    private JButton guessButton;
    private JButton playAgainButton;
    private JButton resetButton;

    public Main() {
        setTitle("Number Guessing Game");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createUI();
        startRound();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createGameArea(), BorderLayout.CENTER);
        mainPanel.add(createButtons(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Number Guessing Game");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));

        roundLabel = new JLabel("Round 1");
        roundLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        panel.add(title, BorderLayout.WEST);
        panel.add(roundLabel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createGameArea() {

        JPanel panel = new JPanel(
                new GridLayout(1, 2, 15, 0)
        );

        panel.add(createGamePanel());
        panel.add(createHistoryPanel());

        return panel;
    }

    private JPanel createGamePanel() {

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Game"
                )
        );

        JLabel difficultyLabel =
                new JLabel("Difficulty");

        difficultyBox =
                new JComboBox<>(
                        Difficulty.values()
                );

        difficultyBox.setSelectedItem(
                Difficulty.MEDIUM
        );

        difficultyBox.addActionListener(
                e -> changeDifficulty()
        );

        rangeLabel = new JLabel();

        JLabel guessLabel =
                new JLabel("Enter your guess");

        guessField = new JTextField();

        guessField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        guessField.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        guessField.setHorizontalAlignment(
                JTextField.CENTER
        );

        guessField.addActionListener(
                this::handleGuess
        );

        guessButton =
                new JButton("Check Number");

        guessButton.addActionListener(
                this::handleGuess
        );

        resultLabel =
                new JLabel(
                        "Enter a number to start"
                );

        resultLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        attemptsLabel = new JLabel();
        scoreLabel = new JLabel();
        bestScoreLabel = new JLabel();
        accuracyLabel = new JLabel();

        panel.add(difficultyLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(difficultyBox);

        panel.add(Box.createVerticalStrut(15));

        panel.add(rangeLabel);

        panel.add(Box.createVerticalStrut(20));

        panel.add(guessLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(guessField);

        panel.add(Box.createVerticalStrut(10));
        panel.add(guessButton);

        panel.add(Box.createVerticalStrut(20));
        panel.add(resultLabel);

        panel.add(Box.createVerticalStrut(15));

        panel.add(attemptsLabel);
        panel.add(scoreLabel);
        panel.add(bestScoreLabel);
        panel.add(accuracyLabel);

        return panel;
    }

    private JPanel createHistoryPanel() {

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Game History"
                )
        );

        historyArea = new JTextArea();

        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);

        JScrollPane scrollPane =
                new JScrollPane(historyArea);

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel createButtons() {

        JPanel panel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT
                )
        );

        playAgainButton =
                new JButton("Play Again");

        resetButton =
                new JButton("Reset");

        playAgainButton.addActionListener(
                e -> startRound()
        );

        resetButton.addActionListener(
                e -> resetGame()
        );

        panel.add(playAgainButton);
        panel.add(resetButton);

        return panel;
    }

    private void startRound() {

        attempts = 0;
        score = 0;

        secretNumber = generateNumber();

        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);

        resultLabel.setText(
                "Enter a number to start"
        );

        resultLabel.setForeground(
                Color.BLACK
        );

        updateInfo();

        guessField.requestFocus();
    }

    private int generateNumber() {

        return random.nextInt(
                difficulty.max - difficulty.min + 1
        ) + difficulty.min;
    }

    private void handleGuess(ActionEvent event) {

        String input =
                guessField.getText().trim();

        if (input.isEmpty()) {

            resultLabel.setText(
                    "Please enter a number"
            );

            return;
        }

        int guess;

        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException e) {

            resultLabel.setText(
                    "Enter a valid number"
            );

            return;
        }

        if (guess < difficulty.min ||
                guess > difficulty.max) {

            resultLabel.setText(
                    "Enter a number between "
                            + difficulty.min
                            + " and "
                            + difficulty.max
            );

            return;
        }

        attempts++;

        if (guess == secretNumber) {

            wins++;

            score =
                    (11 - attempts) * 10;

            if (score > bestScore) {
                bestScore = score;
            }

            resultLabel.setText(
                    "Correct! You Won!"
            );

            resultLabel.setForeground(
                    new Color(0, 130, 60)
            );

            addHistory(
                    "Round "
                            + round
                            + " - WIN - "
                            + attempts
                            + " attempts - Score: "
                            + score
            );

            endRound();

            return;
        }

        if (guess > secretNumber) {

            resultLabel.setText(
                    "Too High!"
            );

            resultLabel.setForeground(
                    new Color(180, 80, 0)
            );

        } else {

            resultLabel.setText(
                    "Too Low!"
            );

            resultLabel.setForeground(
                    new Color(0, 100, 180)
            );
        }

        updateInfo();

        if (attempts >= 10) {

            losses++;
            score = 0;

            resultLabel.setText(
                    "ROUND OVER"
            );

            resultLabel.setForeground(
                    Color.RED
            );

            addHistory(
                    "Round "
                            + round
                            + " - LOSS - Number was "
                            + secretNumber
            );

            endRound();

            return;
        }

        guessField.setText("");
        guessField.requestFocus();
    }

    private void endRound() {

        guessField.setEnabled(false);
        guessButton.setEnabled(false);

        updateInfo();

        round++;

        roundLabel.setText(
                "Round " + round
        );
    }

    private void changeDifficulty() {

        Difficulty selected =
                (Difficulty)
                        difficultyBox.getSelectedItem();

        if (selected == null) {
            return;
        }

        difficulty = selected;

        startRound();

        resultLabel.setText(
                difficulty.name
                        + " mode started"
        );
    }

    private void resetGame() {

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Reset all scores and history?",
                        "Reset Game",
                        JOptionPane.YES_NO_OPTION
                );

        if (option ==
                JOptionPane.YES_OPTION) {

            wins = 0;
            losses = 0;
            score = 0;
            bestScore = 0;
            round = 1;

            historyArea.setText("");

            roundLabel.setText(
                    "Round 1"
            );

            startRound();
        }
    }

    private void updateInfo() {

        rangeLabel.setText(
                "Range: "
                        + difficulty.min
                        + " - "
                        + difficulty.max
        );

        attemptsLabel.setText(
                "Attempts: "
                        + attempts
                        + " / 10"
        );

        scoreLabel.setText(
                "Score: "
                        + score
        );

        bestScoreLabel.setText(
                "Best Score: "
                        + bestScore
        );

        int totalRounds =
                wins + losses;

        double accuracy =
                totalRounds == 0
                        ? 0
                        : (double) wins
                        / totalRounds * 100;

        accuracyLabel.setText(
                String.format(
                        "Accuracy: %.0f%%",
                        accuracy
                )
        );
    }

    private void addHistory(String text) {

        if (historyArea.getText().isEmpty()) {

            historyArea.append(text);

        } else {

            historyArea.append(
                    "\n" + text
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Main game = new Main();

            game.setVisible(true);
        });
    }
}