import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessGameAWT extends JFrame {
    private int randomNumber;
    private int attemptsLeft;
    private int currentRound;
    private JTextField text;
    private JTextArea outputArea;
    private JLabel roundLabel;
    private JLabel attemptsLabel; // Label to display attempts left
    private JPanel mainPanel; // Declare mainPanel at class level

    private final int MAX_ROUNDS = 3; // Total number of rounds
    private final int MAX_ATTEMPTS = 5; // Maximum attempts per round

    private int generateRandomNumber() {
        return (int) (Math.random() * 100) + 1;
    }

    public GuessGameAWT() {
        super("Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the window is closed

        // Initialize variables
        currentRound = 1;

        // Set background color for the JFrame content pane
        getContentPane().setBackground(Color.PINK);

        mainPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Increased rows for attempts label
        mainPanel.setBackground(Color.PINK); // Set background color for the main panel
        add(mainPanel);

        // Heading label at the top
        JLabel headingLabel = new JLabel("Welcome to Creative World of Gaming!!");
        headingLabel.setFont(new Font("Serif", Font.BOLD, 30));
        headingLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the heading
        mainPanel.add(headingLabel);

        // Round label above the input area
        roundLabel = new JLabel("Round " + currentRound);
        roundLabel.setFont(new Font("Serif", Font.BOLD, 20));
        roundLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the round label
        mainPanel.add(roundLabel);

        // Attempts label to display attempts left
        attemptsLabel = new JLabel("Attempts left: " + MAX_ATTEMPTS);
        attemptsLabel.setFont(new Font("Serif", Font.BOLD, 18));
        attemptsLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the attempts label
        mainPanel.add(attemptsLabel);

        // Center panel for input components
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBackground(Color.PINK); // Set background color for the center panel
        mainPanel.add(centerPanel);

        // Label for the input field
        JLabel firstLabel = new JLabel("Enter Your Guess:");
        firstLabel.setFont(new Font("Serif", Font.BOLD, 24));
        firstLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the label
        centerPanel.add(firstLabel);

        // Text field for user input
        text = new JTextField(20);
        text.setHorizontalAlignment(JTextField.CENTER);
        centerPanel.add(text);

        // Button for submitting guess
        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessButtonListener());
        centerPanel.add(guessButton);

        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(Color.BLUE);
        outputArea.setFont(new Font("Serif", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane);

        // Set frame properties
        pack(); // Size the frame to fit its components
        setLocationRelativeTo(null); // Center the frame on screen
        setVisible(true);

        // Start the first round
        startNewRound();
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(text.getText().trim());
                processGuess(guess);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(GuessGameAWT.this,
                        "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
            text.setText("");
            text.requestFocus();
        }
    }

    private void processGuess(int guess) {
        attemptsLeft--;

        if (guess == randomNumber) {
            displayOutput("Congratulations! You guessed the number.");
            endRound();
        } else if (attemptsLeft == 0) {
            displayOutput("Game over! The number was " + randomNumber);
            endRound();
        } else if (guess < randomNumber) {
            displayOutput("Number Is Too Low!   Try again...");
            displayOutput("Attempts left: " + attemptsLeft);
        } else {
            displayOutput("Number Is Too High!    Try again...");
            displayOutput("Attempts left: " + attemptsLeft);
        }

        updateAttemptsLabel(); // Update attempts label after each guess
    }

    private void displayOutput(String message) {
        outputArea.append(message + "\n");
    }

    private void updateAttemptsLabel() {
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
    }

    private void endRound() {
        if (currentRound < MAX_ROUNDS) {
            currentRound++;
            roundLabel.setText("Round " + currentRound);
            displayOutput("End of Round " + (currentRound - 1) + ". Starting Round " + currentRound + ".");
            // Reset attempts for the new round
            attemptsLeft = MAX_ATTEMPTS;
            updateAttemptsLabel();
            // Ask permission to start the next round
            askPermissionForNextRound();
        } else {
            displayOutput("End of Round " + currentRound + ". Game Over!");
            JLabel gameOverLabel = new JLabel("Game Over");
            gameOverLabel.setFont(new Font("Serif", Font.BOLD, 20));
            gameOverLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the game over label
            mainPanel.add(gameOverLabel); // Adding the "Game Over" label to the main panel
            text.setEnabled(false); // Disable text field after game ends
            mainPanel.revalidate(); // Refresh the layout to show the added label
            mainPanel.repaint();
        }
    }

    private void startNewRound() {
        randomNumber = generateRandomNumber();
        attemptsLeft = MAX_ATTEMPTS; // Reset attempts left for each new round
        updateAttemptsLabel(); // Update attempts label at the start of each round
        displayOutput("Round " + currentRound + ". Guess a number between 1 and 100. Attempts left: " + attemptsLeft);
        displayOutput("Please enter your guess below:");
        // Ask permission to start the first round
        askPermissionForNextRound();
    }

    private void askPermissionForNextRound() {
        int option = JOptionPane.showConfirmDialog(this,
                "Do you want to proceed to Round " + currentRound + "?", "Next Round Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            displayOutput("Starting Round " + currentRound + "...");
        } else {
            displayOutput("Round " + currentRound + " skipped.");
            endRound(); // End the game if user declines to proceed
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuessGameAWT());
    }
}
