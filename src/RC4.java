package src;

import java.util.Scanner;

public class RC4 {
    private final byte[] S = new byte[256]; // Tableau de permutation
    private int x = 0;
    private int y = 0;

    // RC4 algorythm initialization with a key
    public RC4(byte[] key) {
        initializeSBox(key);
    }

    // Method that initializes the S (S-box) array with a key
    private void initializeSBox(byte[] key) {
        int keyLength = key.length;
        // Fills a byte array with 0 to 255 numbers
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
        }

        int j = 0;
        for (int i = 0; i < 256; i++) {
            // RC4 algorythm
            j = (j + S[i] + key[i % keyLength]) & 0xFF;
            swap(S, i, j);
        }
    }

    // Method that encrypts and decrypts the data
    public byte[] encryptDecrypt(byte[] data) {
        byte[] result = new byte[data.length];

        for (int i = 0; i < data.length; i++) {
            x = (x + 1) & 0xFF;
            y = (y + S[x]) & 0xFF;
            swap(S, x, y);

            int k = S[(S[x] + S[y]) & 0xFF]; // Generates a pseudo-random key between 0 and 255 (binary mask)
            result[i] = (byte) (data[i] ^ k); // XOR on the flux to encrypt/decrypt
        }
        return result;
    }

    // Method that swaps 2 values in the S array
    private void swap(byte[] array, int i, int j) {
        byte temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void Start() {
        System.out.println("\n---RC4---------------------------");
        String choice;
        // Is an encryption already made or not (used to propose to re-use the encrypted message)
        boolean encryptionMade = false;
        // Initialization of the encryptedMessage variable
        byte[] encryptedMessage = new byte[0];
        // the key is asked once before asking for encryption or decryption
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the key : ");
        String key = scanner.nextLine();

        do {
            System.out.print("Do you want to encrypt (E), decrypt (D) or exit (Q) ? : ");
            choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "Q":
                    break;
                case "E":
                    // the return of the encryption function is affected to a vaiable to allow to use if for decryption
                    encryptedMessage = encryptRC4(key, scanner);
                    encryptionMade = true;
                    break;
                case "D":
                    boolean useLastEncryptedMessage=false;
                    // if an encrypted message has already been made, the user is asked for using it or not
                    if (encryptionMade) {
                        System.out.print("An encryption has been made, do you want to use the encrypted message to be used for decryption (Y/N) ? : ");
                        String choice2 = scanner.nextLine().toUpperCase();
                        useLastEncryptedMessage = choice2.equalsIgnoreCase("Y");
                    }
                    decryptRC4(key, useLastEncryptedMessage, encryptedMessage, scanner);
                    break;
                default:
                    System.out.println("Please answer correctly to the question.");
            }
        } while (!choice.equals("Q"));
    }

    public static byte[] encryptRC4(String key, Scanner scanner) {
        String messageToEncrypt;
        System.out.print("Please enter the message to encrypt : ");
        messageToEncrypt = scanner.nextLine();
        // spaces at the begining and at the end of the string are deleted
        messageToEncrypt = messageToEncrypt.trim();
        // Crypt - RC4 initialization with the key
        RC4 rc4 = new RC4(key.getBytes());
        byte[] encryptedMessage = rc4.encryptDecrypt(messageToEncrypt.getBytes());
        System.out.println("Encrypted message : " + new String(encryptedMessage));
        return encryptedMessage;
    }

    public static void decryptRC4(String key, boolean useLastEncryptedMessage, byte[] encryptedMessage, Scanner scanner) {
        byte[] messageToDecrypt;
        if (useLastEncryptedMessage) {
            messageToDecrypt = encryptedMessage;
        } else {
            System.out.print("Please enter the message to decrypt : ");
            messageToDecrypt = scanner.nextLine().getBytes();
        }
        // Decrypt
        RC4 rc4Decrypt = new RC4(key.getBytes());
        byte[] decrypted = rc4Decrypt.encryptDecrypt(messageToDecrypt);
        System.out.println("Decrypted message : " + new String(decrypted));
    }
}

