import java.util.Scanner;

public class Caesar {

    // Main entry point for the Caesar tool
    public static void start(Scanner scanner) {
        boolean running = true;

        System.out.println("=== Caesar Tool ===");

        while (running) {
            int choice = getValidOption(scanner); // Get a valid choice from the user

            switch (choice) {
                case 1:
                    encryptProcess(scanner);
                    break;
                case 2:
                    decryptProcess(scanner);
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    running = false;
                    break;
                default:
                    System.out.println("This should not happen. Please try again.");
                    break;
            }
        }
    }

    // Get a valid option (1, 2, or 3) from the user
    private static int getValidOption(Scanner scanner) {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1 - Encrypt a message");
            System.out.println("2 - Decrypt a message");
            System.out.println("3 - Return to main menu");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 3) {
                    return choice; // Valid choice
                } else {
                    System.out.println("Error: Please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number (1, 2, or 3).");
            }
        }
    }

    // Encryption process
    private static void encryptProcess(Scanner scanner) {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter a string to encrypt (letters must be only lowercase or only uppercase):");
            String input = scanner.nextLine();

            if (isValidInput(input)) {
                validInput = true;

                int shift = getValidShift(scanner); // Get a valid shift value

                String encrypted = encryptROT(input, shift);

                System.out.println("Encrypted message: " + encrypted);
            } else {
                System.out.println("Error: The input must contain only uppercase or lowercase letters.");
                System.out.println("Example of valid input: 'abc' or 'ABC'. Please try again.");
            }
        }

        System.out.println(); // Add spacing for better readability
    }

    // Decryption process
    private static void decryptProcess(Scanner scanner) {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter a string to decrypt (letters must be only lowercase or only uppercase):");
            String input = scanner.nextLine();

            if (isValidInput(input)) {
                validInput = true;

                int shift = getValidShift(scanner); // Get a valid shift value

                String decrypted = decryptROT(input, shift);
                System.out.println("Decrypted message: " + decrypted);
            } else {
                System.out.println("Error: The input must contain only uppercase or lowercase letters.");
                System.out.println("Example of valid input: 'abc' or 'ABC'. Please try again.");
            }
        }

        System.out.println(); // Add spacing for better readability
    }

    // Method to get a valid integer shift value
    private static int getValidShift(Scanner scanner) {
        while (true) {
            System.out.println("Enter a shift value (X):");
            try {
                int shift = Integer.parseInt(scanner.nextLine());
                return shift; // Return the valid integer
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number for the shift.");
            }
        }
    }

    // Method to validate user input
    private static boolean isValidInput(String input) {
        return input.matches("[a-z]+") || input.matches("[A-Z]+");
    }

    // Method to encrypt the string using ROT(X)
    private static String encryptROT(String input, int shift) {
        StringBuilder result = new StringBuilder();
        shift = shift % 26; // Normalize the shift

        for (char c : input.toCharArray()) {
            if (Character.isLowerCase(c)) {
                result.append((char) ((c - 'a' + shift) % 26 + 'a'));
            } else if (Character.isUpperCase(c)) {
                result.append((char) ((c - 'A' + shift) % 26 + 'A'));
            }
        }
        return result.toString();
    }

    // Method to decrypt the string using ROT(X)
    private static String decryptROT(String input, int shift) {
        StringBuilder result = new StringBuilder();
        shift = shift % 26; // Normalize the shift

        for (char c : input.toCharArray()) {
            if (Character.isLowerCase(c)) {
                result.append((char) ((c - 'a' - shift + 26) % 26 + 'a'));
            } else if (Character.isUpperCase(c)) {
                result.append((char) ((c - 'A' - shift + 26) % 26 + 'A'));
            }
        }
        return result.toString();
    }
}