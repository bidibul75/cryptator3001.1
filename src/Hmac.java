package src;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Scanner;

public class Hmac {

    static void start(Scanner scanner) {
        boolean isHashValid = false;
        String hash = "";
        // Prompt the user to enter the data needed for hmac
        while(!isHashValid) {
            System.out.print("Enter your hash in hexadecimal: ");
            hash = scanner.nextLine();
            try {
                HexFormat.of().parseHex(hash);
                isHashValid = true;
            } catch(Exception e) {
                System.out.print("\nHash is not valid hexadecimal code.\n");
            }
        }

        System.out.print("Enter your data to hash: ");
        String data = scanner.nextLine();

        System.out.print("Enter your key: ");
        String key = scanner.nextLine();

        try {
            // get processed hash using HMAC(SHA-256)
            boolean verified = verifyHMAC(hash, data, key);

            // Display the result
            if(verified) {
                System.out.print("The given hash and the processed one matches");
            } else {
                System.out.print("The given hash and the processed one does not match");
            }


        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error during hashing: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error during hashing: " + e.getMessage());
        }

    }

    /**
     * Verifies if a hmac matches with given data
     * @param hash hash to verify
     * @param data data to wrap
     * @param key key for the hmac
     * @return whether the original hash is equal to the one processed
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    static boolean verifyHMAC(String hash, String data, String key) throws Exception {
        boolean isHashValid = false;

        // hmac lib in java takes bytes instead of strings
        byte[] hashBytes = HexFormat.of().parseHex(hash);
        byte[] keyBytes = key.getBytes();
        byte[] dataBytes = data.getBytes();

        // process the data and key into the hmac
        Mac mac = null;
        try {
            // generating the hmac
            mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] processed = mac.doFinal(dataBytes);

            // check if our hashes are the same
            if (new String(hashBytes, StandardCharsets.UTF_8).equals(new String(processed, StandardCharsets.UTF_8))) {
                isHashValid = true;
            }
        } catch (Exception e) {
            throw new Exception("An error occured during hashing process");
        }

        return isHashValid;
    };
}
