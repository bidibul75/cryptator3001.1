package src;

import java.util.Scanner;

public class Vigenere {

    // Method to encrypt using Vigenere cipher
    public String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Encrypt only alphabetic characters
            if (c >= 'A' && c <= 'Z') {
                result.append((char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A'));
                j = (j + 1) % key.length(); // Cycle through the key
            } else {
                result.append(c); // Leave non-alphabetic characters unchanged
            }
        }
        return result.toString();
    }

    // Method to validate the key
    public boolean isValidKey(String key) {
        if (key == null || key.isEmpty()) {
            return false; // Key must not be null or empty
        }
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            // Check if the character is not a letter (A-Z or a-z)
            if (!Character.isLetter(c)) {
                return false; // If any non-letter character is found, return false
            }
        }
        return true; // If all characters are letters, return true
    }

    // Method to decrypt using Vigenere cipher
    public String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // Decrypt only alphabetic characters
            if (c >= 'A' && c <= 'Z') {
                result.append((char) ((c - key.charAt(j) + 26) % 26 + 'A'));
                j = (j + 1) % key.length(); // Cycle through the key
            } else {
                result.append(c); // Leave non-alphabetic characters unchanged
            }
        }
        return result.toString();
    }

    //public static void main(String[] args) {
    public static void start(Scanner scanner) {
        Vigenere cipher = new Vigenere();

        System.out.println("=== Vigenere Cipher ===");

        // User enters a password
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        String key;
        int attempts = 0; // Counter for invalid attempts

        // Ask for a valid key with up to 5 attempts
        while (attempts < 5) {
            System.out.print("Enter the key for encryption (only letters): ");
            key = scanner.nextLine();

            // Validate the key
            if (cipher.isValidKey(key)) {
                // Key is valid, proceed to encryption
                String encryptedPassword = cipher.encrypt(password, key);

                // Display the encrypted password
                System.out.println("Your encrypted password is: " + encryptedPassword);

                // Decrypt the password using the same key
                String decryptedPassword = cipher.decrypt(encryptedPassword, key);

                // Display the decrypted password
                System.out.println("Your decrypted password is: " + decryptedPassword);
                return; // Exit the method
            } else {
                attempts++; // Increment the attempts count
                System.out.println("Invalid key! Please enter only letters and make sure it is not empty.");
            }
        }

        // If the user fails to provide a valid key after 5 attempts
        System.out.println("Error: Too many invalid attempts. Returning to the main menu.");
    }
}
