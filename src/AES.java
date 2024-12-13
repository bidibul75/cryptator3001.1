package src;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class AES {

    /**
     * Start the AES encryption tool.
     *
     * @param scanner: The Scanner object for user input.
     */
    public static void start(Scanner scanner) {
        System.out.println("=== AES Encryption/Decryption Tool ===");

        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1 - Encrypt a message");
            System.out.println("2 - Decrypt a message");
            System.out.println("3 - Generate a new key");
            System.out.println("4 - Return to main menu");

            int choice = getValidOption(scanner);

            switch (choice) {
                case 1:
                    System.out.print("Enter the message to encrypt: ");
                    String messageToEncrypt = scanner.nextLine();
                    System.out.print("Enter your key (base64): ");
                    String encryptionKey = scanner.nextLine();
                    String encryptedMessage = encrypt(messageToEncrypt, encryptionKey);
                    System.out.println("Encrypted message: " + encryptedMessage);
                    break;
                case 2:
                    System.out.print("Enter the message to decrypt: ");
                    String messageToDecrypt = scanner.nextLine();
                    System.out.print("Enter your key (base64): ");
                    String decryptionKey = scanner.nextLine();
                    String decryptedMessage = decrypt(messageToDecrypt, decryptionKey);
                    System.out.println("Decrypted message: " + decryptedMessage);
                    break;
                case 3:
                    String newKey = generateKey();
                    System.out.println("Generated key (base64): " + newKey);
                    break;
                case 4:
                    System.out.println("Exiting AES Tool. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Unexpected error.");
            }
        }
    }

    /**
     * Encrypt a message using AES.
     *
     * @param message: The message to encrypt.
     * @param base64Key: The encryption key in base64 format.
     * @return The encrypted message in base64 format, or an error message if encryption fails.
     */
    public static String encrypt(String message, String base64Key) {
        try {
            SecretKey key = decodeKey(base64Key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return "Encryption failed: " + e.getMessage();
        }
    }

    /**
     * Decrypt a message using AES.
     *
     * @param encryptedMessage: The encrypted message in base64 format.
     * @param base64Key: The decryption key in base64 format.
     * @return The decrypted message, or an error message if decryption fails.
     */
    public static String decrypt(String encryptedMessage, String base64Key) {
        try {
            SecretKey key = decodeKey(base64Key);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(decryptedBytes);
        } catch (Exception e) {
            return "Decryption failed: " + e.getMessage();
        }
    }

    /**
     * Generate a new AES key and return it in base64 format.
     *
     * @return The generated AES key in base64 format, or an error message if key generation fails.
     */
    public static String generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256); // AES supports 128, 192, or 256 bits
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            return "Key generation failed: " + e.getMessage();
        }
    }

    /**
     * Decode a base64 key into a SecretKey.
     *
     * @param base64Key: The base64 encoded key.
     * @return The SecretKey object, or null if decoding fails.
     */
    private static SecretKey decodeKey(String base64Key) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            return new SecretKeySpec(decodedKey, "AES");
        } catch (Exception e) {
            System.out.println("Invalid key format: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get a valid option from the user.
     *
     * @param scanner: The Scanner object for user input.
     * @return The user's choice as an integer.
     */
    private static int getValidOption(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Error: Please enter a valid number (1-4).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number (1-4).");
            }
        }
    }
}