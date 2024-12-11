package src;

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
}
