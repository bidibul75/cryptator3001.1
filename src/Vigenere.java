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
      public static void start(Scanner scanner){
        //Scanner scanner = new Scanner(System.in);
        Vigenere cipher = new Vigenere();

        System.out.println("=== Vigenere ===");

        // User enters a password
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        String key = "";  // Initialize the key variable before the loop
        int compt = 0; // Counter for invalid attempts

        // Keep asking for a valid key until the user provides one or 5 attempts are reached
        while (compt < 5) {
            System.out.print("Enter the key for encryption (only letters): ");
            key = scanner.nextLine();

            if (cipher.isValidKey(key)) {
                break; // If key is valid, exit the loop
            } else {
                compt++; // Increment the attempts count
                System.out.println("Invalid key! Please enter only letters.");
            }
        }

        // Check if the user failed to enter a valid key after 5 attempts
        if (compt >= 5) {
            System.out.println("Error: noob YOU LOOSE back to menu!!!!");
            return; // Exit the program if maximum attempts are reached
        }

        // Encrypt the password using the Vigenere
        String encryptedPassword = cipher.encrypt(password, key);

        // Display the encrypted password
        System.out.println("Your encrypted password is: " + encryptedPassword);

        // Decrypt the password using the same key
        String decryptedPassword = cipher.decrypt(encryptedPassword, key);

        // Display the decrypted password
        System.out.println("Your decrypted password is: " + decryptedPassword);

        //scanner.close();
    }
}
