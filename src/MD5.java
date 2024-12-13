package src;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MD5 {

    /**
     * Start the MD5 hash tool, allowing the user to generate and optionally compare hashes.
     *
     * @param scanner: The Scanner object used for user input.
     */
    public static void start(Scanner scanner) {
        System.out.println("=== MD5 ===");

        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1 - Generate MD5 hash for a message");
            System.out.println("2 - Compare two hashes");
            System.out.println("3 - Quit");

            int choice = getValidOption(scanner);

            switch (choice) {
                case 1:
                    generateHash(scanner);
                    break;
                case 2:
                    compareHashes(scanner);
                    break;
                case 3:
                    System.out.println("Exiting MD5 Hash Tool. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("This should not happen. Please try again.");
                    break;
            }
        }
    }

    /**
     * Generate an MD5 hash for a given message and display it.
     *
     * @param scanner: The Scanner object used for user input.
     */
    private static void generateHash(Scanner scanner) {
        System.out.print("Enter the message to hash: ");
        String message = scanner.nextLine();
        String hash = calculateMD5Hash(message);
        System.out.println("MD5 Hash: " + hash);
    }

    /**
     * Compare two MD5 hashes entered by the user.
     *
     * @param scanner: The Scanner object used for user input.
     */
    private static void compareHashes(Scanner scanner) {
        System.out.print("Enter the first hash: ");
        String hash1 = scanner.nextLine();
        while (!isValidMD5Hash(hash1)) {
            System.out.println("Invalid hash! Please enter a valid hexa 32-character MD5 hash.");
            System.out.print("Enter the first hash: ");
            hash1 = scanner.nextLine();
        }

        System.out.print("Enter the second hash: ");
        String hash2 = scanner.nextLine();
        while (!isValidMD5Hash(hash2)) {
            System.out.println("Invalid hash! Please enter a valid hexa 32-character MD5 hash.");
            System.out.print("Enter the second hash: ");
            hash2 = scanner.nextLine();
        }

        if (hash1.equalsIgnoreCase(hash2)) {
            System.out.println("The two hashes are identical.");
        } else {
            System.out.println("The two hashes are different.");
        }
    }

    /**
     * Validate that a string is a valid MD5 hash (32-character hexadecimal).
     *
     * @param hash: The string to validate.
     * @return True if the string is a valid MD5 hash, false otherwise.
     */
    private static boolean isValidMD5Hash(String hash) {
        return hash.matches("^[a-fA-F0-9]{32}$");
    }

    /**
     * Calculate the MD5 hash of a given message.
     *
     * @param message: The message to hash.
     * @return The MD5 hash as a hexadecimal string.
     */
    private static String calculateMD5Hash(String message) {
        try {
            // Create MD5 MessageDigest instance
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Compute the hash and convert it to hexadecimal format
            byte[] digest = md.digest(message.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available.", e);
        }
    }

    /**
     * Get a valid option from the user.
     *
     * @param scanner: The Scanner object used for user input.
     * @return The user's choice as an integer.
     */
    private static int getValidOption(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 3) {
                    return choice; // Valid choice
                } else {
                    System.out.println("Error: Please enter a valid number (1, 2, or 3).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number (1, 2, or 3).");
            }
        }
    }
}