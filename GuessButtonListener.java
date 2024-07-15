import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuessTheNumberGame extends JFrame {
    private int randomNumber;
    private int attemptsLeft;
    private JTextField guessField;
    private JTextArea outputArea;

    public GuessTheNumberGame() {
        super("Guess the Number Game");
        randomNumber = generateRandomNumber();
        attemptsLeft = 10; // Number of attempts allowed

        JLabel promptLabel = new JLabel("Enter your guess (1-100):");
        guessField = new JTextField(10);
        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessButtonListener());

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel inputPanel = new JPanel();
        inputPanel.add(promptLabel);
        inputPanel.add(guessField);
        inputPanel.add(guessButton);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    private int generateRandomNumber() {
        return (int) (Math.random() * 100) + 1;
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                int guess = Integer.parseInt(guessField.getText().trim());
                attemptsLeft--;

                if (guess == randomNumber) {
                    outputArea.append("Congratulations! You guessed the number " + randomNumber + "!\n");
                    outputArea.append("Score: " + attemptsLeft + " attempts left.\n");
                    guessField.setEnabled(false);
                } else if (guess < randomNumber) {
                    outputArea.append("Too low! Try again.\n");
                } else {
                    outputArea.append("Too high! Try again.\n");
                }

                if (attemptsLeft == 0 && guess != randomNumber) {
                    outputArea.append("Game over! The number was " + randomNumber + ".\n");
                    guessField.setEnabled(false);
                }
            } catch (NumberFormatException e) {
                outputArea.append("Invalid input. Please enter a number.\n");
            }

            guessField.setText("");
            guessField.requestFocus();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuessTheNumberGame();
            }
        });
    }
}

