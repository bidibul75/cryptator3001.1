package src;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class InitializationVector {
    //public static void main(String[] args) throws Exception {
    public static void start(Scanner scanner){
        //Scanner scanner = new Scanner(System.in);

        // Prompt the user for a password
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        // Generate two different salts
        byte[] salt1 = generateSalt();
        byte[] salt2 = generateSalt();

        // Derive two different keys from the same password using the salts
        SecretKeySpec key1 = deriveKey(password, salt1);
        SecretKeySpec key2 = deriveKey(password, salt2);

        // Encrypt the password using the first key
        byte[] encrypted1 = encrypt(password, key1);

        // Encrypt the password using the second key
        byte[] encrypted2 = encrypt(password, key2);

        // Display the results
        System.out.println("\n=== Results ===");
        System.out.println("Password: " + password);
        System.out.println("First encryption result: " + Base64.getEncoder().encodeToString(encrypted1));
        System.out.println("Second encryption result: " + Base64.getEncoder().encodeToString(encrypted2));
        //System.out.println("First salt: " + Base64.getEncoder().encodeToString(salt1));
        //System.out.println("Second salt: " + Base64.getEncoder().encodeToString(salt2));

        // Verify that the two results are different
        System.out.println("\nThe two encrypted results are different: " +
                !Base64.getEncoder().encodeToString(encrypted1).equals(Base64.getEncoder().encodeToString(encrypted2)));
    }

    // Method to generate a random salt
    private static byte[] generateSalt() {
        byte[] salt = new byte[16]; // 16 bytes = 128 bits
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    // Method to derive a key from the password and salt
    private static SecretKeySpec deriveKey(String password, byte[] salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt); // Mix the salt with the password
        byte[] keyBytes = digest.digest(password.getBytes("UTF-8"));
        return new SecretKeySpec(keyBytes, "AES");
    }

    // Method to encrypt data using AES
    private static byte[] encrypt(String data, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // ECB mode for simplicity (no IV)
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes("UTF-8"));
    }
}
