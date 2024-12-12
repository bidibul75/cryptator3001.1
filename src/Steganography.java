package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Steganography {
    private static final String BASE_IMAGE_PATH = "src/resources/images/";

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Steganography Program");
        System.out.println("1. Encode a message");
        System.out.println("2. Decode a message");
        int choice = scanner.nextInt();
       scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.print("Enter image name (e.g., myimage.png): ");
            String imagePath = scanner.nextLine();
            System.out.print("Enter message to encode: ");
            String message = scanner.nextLine();
            System.out.print("Enter output image path: ");
            String outputPath = scanner.nextLine();
            encodeMessage(imagePath, message, outputPath);
        } else if (choice == 2) {
            System.out.print("Enter image name (e.g., myimage.png): ");
            String imagePath = scanner.nextLine();
            String decodedMessage = decodeMessage(imagePath);
            System.out.println("Decoded message: " + decodedMessage);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void encodeMessage(String imagePath, String message, String outputPath) {
        try {
            String fullImagePath = BASE_IMAGE_PATH + imagePath;
            File imageFile = new File(fullImagePath);

            // Checks if the file exists
            if (!imageFile.exists()) {
                System.err.println("Error: Image file not found at " + fullImagePath);
                return;
            }

            // Load image
            BufferedImage image = ImageIO.read(imageFile);

            // Checks if the image is large enough
            if (!isImageLargeEnough(image, message.length())) {
                System.err.println("Error: The image is too small to contain the message.");
                return;
            }

            // Encrypts image and saves it
            BufferedImage encodedImage = encode(image, message);
            ImageIO.write(encodedImage, "png", new File(outputPath));
            System.out.println("Message encoded successfully!");
        } catch (IOException e) {
            System.err.println("Error reading or writing image: " + e.getMessage());
        }
    }

    private static String decodeMessage(String imagePath) {
        try {
            String fullImagePath = BASE_IMAGE_PATH + imagePath;
            File imageFile = new File(fullImagePath);

            // Checks if the file exists
            if (!imageFile.exists()) {
                System.err.println("Error: Image file not found at " + fullImagePath);
                return null;
            }

            // Loads image
            BufferedImage image = ImageIO.read(imageFile);

            // Decrypt image
            return decode(image);
        } catch (IOException e) {
            System.err.println("Error reading image: " + e.getMessage());
            return null;
        }
    }

    private static boolean isImageLargeEnough(BufferedImage image, int messageLength) {
        int imageCapacity = image.getWidth() * image.getHeight(); // Pixels total
        int requiredBits = (messageLength + 1) * 8; // Message + null terminator
        return imageCapacity >= requiredBits;
    }

    private static BufferedImage encode(BufferedImage image, String message) {
        String messageWithTerminator = message + "\0"; // Adds the termination code
        int[] messageBits = messageToBits(messageWithTerminator);

        BufferedImage encodedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        int bitIndex = 0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                // Encrypts a bit if the message is not finished
                if (bitIndex < messageBits.length) {
                    pixel = setLeastSignificantBit(pixel, messageBits[bitIndex++]);
                }
                encodedImage.setRGB(x, y, pixel);
            }
        }
        return encodedImage;
    }

    private static String decode(BufferedImage image) {
        StringBuilder message = new StringBuilder();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int bit = getLeastSignificantBit(pixel);
                message.append(bit);

                if (message.length() % 8 == 0) {
                    String character = binaryToString(message.substring(message.length() - 8));
                    if (character.equals("\0")) {
                        return binaryToString(message.substring(0, message.length() - 8));
                    }
                }
            }
        }
        return binaryToString(message.toString());
    }

    private static int[] messageToBits(String message) {
        byte[] bytes = message.getBytes();
        int[] bits = new int[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bits[i * 8 + j] = (bytes[i] >> (7 - j)) & 1;
            }
        }
        return bits;
    }

    private static String binaryToString(String binary) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            int character = Integer.parseInt(binary.substring(i, Math.min(i + 8, binary.length())), 2);
            result.append((char) character);
        }
        return result.toString();
    }

    private static int setLeastSignificantBit(int pixel, int bit) {
        int alpha = (pixel >> 24) & 0xFF; // Alpha component (transparency)
        int red = (pixel >> 16) & 0xFF;  // Red component
        int green = (pixel >> 8) & 0xFF; // Green component
        int blue = pixel & 0xFF;         // Blue component

        // Modify only the blue component to set the LSB
        blue = (blue & 0xFE) | bit;

        // Reconstruct the pixel with the new values
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    private static int getLeastSignificantBit(int pixel) {
        return pixel & 1; // Extract the LSB from the blue component
    }
}
