import java.util.Arrays;
import java.util.Scanner;

public class LFSR {

    /**
     * Start the LFSR tool, prompting the user for a seed, validating it, and generating
     * pseudo-random numbers.
     *
     * @param scanner: The Scanner object used for user input.
     */
    public static void start(Scanner scanner) {
        System.out.println("=== LFSR Random Number Generator ===");

        // Prompt the user for a seed
        String seed = null;
        int attempts = 0; // Counter for invalid attempts

        while (attempts < 5) {
            System.out.print("Enter a seed (lowercase letters only): ");
            seed = scanner.nextLine();

            // Validate the seed
            if (isValidSeed(seed)) {
                break; // Valid seed, exit the loop
            }

            attempts++;
            System.out.println("Invalid seed! Please enter lowercase letters only.");
            if (attempts == 5) {
                System.out.println("Too many invalid attempts. Returning to the main menu.");
                return; // Exit the method to return to the main menu
            }
        }

        // Convert the seed into bits
        int[] lfsr = convertSeedToBits(seed);
        System.out.println("Initial seed (in bits): " + Arrays.toString(lfsr));

        // Run the LFSR for 10 iterations
        System.out.println("\nLFSR results:");
        for (int i = 0; i < 10; i++) {
            int output = runLFSR(lfsr);
            System.out.printf("Iteration %d: Binary = %s, Decimal = %d\n",
                    i + 1, Arrays.toString(lfsr).replaceAll("[\\[\\], ]", ""), output);
        }
    }

    /**
     * Validate that the provided seed contains only lowercase letters.
     *
     * @param seed: The seed to validate.
     * @return True if the seed contains only lowercase letters, false otherwise.
     */
    private static boolean isValidSeed(String seed) {
        return seed.matches("[a-z]+"); // Only allow lowercase letters
    }

    /**
     * Convert a seed string into an array of bits.
     *
     * Each character of the seed is converted into its 8-bit binary representation,
     * and all characters are concatenated to form the bit array.
     *
     * @param seed: The seed string to convert.
     * @return An array of integers representing the bits of the seed.
     */
    private static int[] convertSeedToBits(String seed) {
        StringBuilder binaryString = new StringBuilder();
        for (char c : seed.toCharArray()) {
            // Convert each character to an 8-bit binary representation
            String binaryChar = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binaryString.append(binaryChar);
        }

        // Convert the binary string into an array of integers
        int[] lfsr = new int[binaryString.length()];
        for (int i = 0; i < binaryString.length(); i++) {
            lfsr[i] = binaryString.charAt(i) - '0';
        }
        return lfsr;
    }

    /**
     * Execute one iteration of the LFSR algorithm.
     *
     * The feedback bit is calculated using the first, second, third, and second-to-last bits,
     * with a specific formula: (bit1 + bit2 - bit3 + bitLast + 26) % 2.
     * The bits are then shifted to the left, and the feedback bit is added to the end.
     *
     * @param lfsr: The current state of the LFSR as an array of bits.
     * @return The decimal value of the LFSR state after this iteration.
     */
    private static int runLFSR(int[] lfsr) {
        // Length of the sequence
        int n = lfsr.length;

        // Feedback using taps based on a primitive polynomial
        int feedback = lfsr[0] ^ lfsr[2] ^ lfsr[3] ^ lfsr[4];

        // Shift the bits
        for (int i = 0; i < n - 1; i++) {
            lfsr[i] = lfsr[i + 1];
        }
        lfsr[n - 1] = feedback; // Add the feedback bit at the last position

        // Convert the current LFSR state into a decimal value
        int decimalValue = 0;
        for (int i = 0; i < n; i++) {
            decimalValue = (decimalValue << 1) | lfsr[i];
        }

        return decimalValue;
    }
}