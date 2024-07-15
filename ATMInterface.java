
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ATMInterface {
    private static ArrayList<userDetails> accounts = new ArrayList<>();
    private static userDetails currentUser = null;
    private static double balance = 1000000;

    public static void main(String args[]) {
        accounts.add(new userDetails("Prakyath19", 739712));
        accounts.add(new userDetails("karthik123", 21441));

        System.out.println("\n********* WELCOME TO ATM OF INDIAN OVERSEAS BANK *********\n");

        Scanner sc = new Scanner(System.in);
        System.out.println("Insert the ATM Card and give the valid credentials\n");
        System.out.println("Enter the userId:");
        String userId = sc.nextLine();
        System.out.println("Enter the pin:");
        int pin = sc.nextInt();

        // Authenticate user
        currentUser = authenticateUser(userId, pin);
        if (currentUser != null) {
            System.out.println("Authentication successful. Welcome, " + currentUser.getUserId() + "!");
        } else {
            System.out.println("Authentication failed. Please try again.");
            return; // Exit the program if authentication fails
        }

        ATMOperations atmOperations = new ATMOperations();

        while (true) {
            System.out.println("\n*----ATM MENU----*\n");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("\nEnter the option to perform the operation:");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    atmOperations.TransactionHistory();
                    break;

                case 2:
                    System.out.println("Enter the amount for Withdrawal\n");
                    double withdrawAmt = sc.nextDouble();
                    atmOperations.withdrawal(withdrawAmt, balance);
                    break;

                case 3:
                    System.out.println("Enter the amount for deposit");
                    double depositAmt = sc.nextDouble();
                    atmOperations.Deposit(depositAmt, balance);
                    break;

                case 4:
                    atmOperations.Transfer();
                    break;

                case 5:
                    System.out.println("\nTransaction is Finished .....Thank You For Choosing Our ATM.....\n");
                    return;

                default:
                    System.out.println("Invalid option\n");
                    break;
            }
        }
    }

    private static userDetails authenticateUser(String userId, int pin) {
        for (userDetails user : accounts) {
            if (user.getUserId().equals(userId) && user.getPin() == pin) {
                return user;
            }
        }
        return null; // Return null if authentication fails
    }
}

class ATMOperations {
    private double balance = 1000000;
    private int transactions = 0;
    private String transactionHistory = "";

    public void withdrawal(double withdrawAmt, double balance) {
        try {
            if (withdrawAmt <= balance) {
                this.balance -= withdrawAmt;
                System.out.printf("%f Rs Successfully withdrawn", withdrawAmt);
                System.out.printf("\nthe current amount after withdrawal is :%f\n", this.balance);
                String history = withdrawAmt + " Rs withdrawn\n";
                transactionHistory = transactionHistory.concat(history);
            } else {
                System.out.println("Insufficient Bank Balance");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public void Deposit(double depositAmt, double balance) {
        try {
            if (depositAmt > 0) {
                this.balance += depositAmt;
                System.out.printf("%f Rs Successfully Deposited\n", depositAmt);
                System.out.printf("\nthe Current Amount after Deposited is :%f \n", this.balance);
                String history = depositAmt + " Rs deposited\n";
                transactionHistory = transactionHistory.concat(history);
            } else {
                System.out.printf("\n Sorry. The Limit exceeded");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public void Transfer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter Recipient's UserId: ");
        String recipientId = sc.nextLine();
        System.out.println("Enter Amount to transfer: ");
        double amount = sc.nextDouble();
        try {
            if (this.balance >= amount) {
                transactions++;
                this.balance -= amount;
                System.out.println("\nSuccessfully Transferred to " + recipientId);
                String history = amount + " Rs transferred to " + recipientId + "\n";
                transactionHistory = transactionHistory.concat(history);
            } else {
                System.out.println("\nInsufficient Balance.");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public void TransactionHistory() {
        if (transactions == 0) {
            System.out.println("No transactions happened yet");
        }
         else {
            System.out.println(transactionHistory);
        }
    }
}

class userDetails {
    private String userId;
    private int pin;
    private double balance;

    userDetails(String userId, int pin) {
        this.userId = userId;
        this.pin = pin;
    }

    public String getUserId() {
        return userId;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }
}





