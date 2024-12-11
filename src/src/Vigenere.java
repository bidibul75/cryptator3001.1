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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Vigenere cipher = new Vigenere();

        //  user enter a password
        System.out.print("enter your password: ");
        String password = scanner.nextLine();

        //  user enter a key for encryption
        System.out.print("enter the key for encryption: ");
        String key = scanner.nextLine();

        // Encrypt the password using the Vigenere cipher
        String encryptedPassword = cipher.encrypt(password, key);

        // Display the encrypted password
        System.out.println("Your encrypted password is: " + encryptedPassword);

        scanner.close();
    }
}
