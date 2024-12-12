package src;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Sha256 {

    //public static void main(String[]arg){
    public static void start(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter a password
        System.out.print("Enter a password to hash: ");
        String password = scanner.nextLine();

        try {
            // Hash the password using SHA-256
            String hashedPassword = hashPassword(password);

            // Display the hashed password
            System.out.println("Hashed password (SHA-256): " + hashedPassword);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error during hashing: " + e.getMessage());
        }

        //scanner.close();
    }

    // Method to hash a password using SHA-256
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        // Create an instance of MessageDigest with the SHA-256 algorithm
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Convert the password to UTF-8 bytes, then apply the hash
        byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        // Convert the hash result (bytes) to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b); // Convert each byte to hexadecimal
            if (hex.length() == 1) {
                hexString.append('0'); // Add a zero to maintain a length of 2
            }
            hexString.append(hex);
        }

        return hexString.toString(); // Return the hash in hexadecimal
    }
}
