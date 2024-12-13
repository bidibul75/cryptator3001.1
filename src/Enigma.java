package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Enigma {

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static final String reversedAlphabet = new StringBuilder(alphabet).reverse().toString();


    /**
     * @throws Exception
     */
    public static void start(Scanner scanner){
        System.out.println("\n---ENIGMA---------------------------");

        // choice for the user
        String choice, messageToEncrypt = "", messageToDecrypt = "", errorMessage = "";
        while (true) {
            System.out.print("ENIGMA - do you want to encrypt (E) or decrypt (D) ? : ");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("E") || choice.equalsIgnoreCase("D")) break;
            else System.out.println("Please answer correctly to the question.");
        }

        while (choice.equalsIgnoreCase("E")) {
            int[] wheel = {0, 0, 0};
            while (true) {
                System.out.print("ENIGMA - Which position for the first wheel (0 - 25)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[0] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            while (true) {
                System.out.print("ENIGMA - Which position for the first wheel (0 - 25)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[1] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            while (true) {
                System.out.print("ENIGMA - Which position for the first wheel (0 - 25)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[2] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            System.out.print("Please enter the message to encrypt (minuscule letters only) : ");
            messageToEncrypt = scanner.nextLine();
            messageToEncrypt = messageToEncrypt.trim();

            if (Pattern.matches("^[a-z]+$", messageToEncrypt)) {
                System.out.println("Message to encrypt : " + messageToEncrypt);
                System.out.println("Encrypted message : " + Enigma.encryptEnigma(messageToEncrypt, wheel));
                break;
            }

            System.out.println("Error : the message to encrypt is not only composed of minuscule letters");
        }

        while (choice.equalsIgnoreCase("D")) {
            int[] wheel = {0, 0, 0};
            while (true) {
                System.out.print("ENIGMA - Which position for the first wheel (0 - 25)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[1] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            while (true) {
                System.out.print("ENIGMA - Which position for the second wheel (0 - 25)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[2] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            while (true) {
                System.out.print("ENIGMA - Which position for the third wheel (0 - 25)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[0] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            System.out.print("Please enter the message to decrypt (minuscule letters only) : ");
            messageToEncrypt = scanner.nextLine();
            messageToEncrypt = messageToEncrypt.trim();

            if (Pattern.matches("^[a-z]+$", messageToEncrypt)) {
                System.out.println("Message to decrypt : " + messageToEncrypt);
                System.out.println("Decrypted message : " + Enigma.decryptEnigma(messageToEncrypt, wheel));
                break;
            }

            System.out.println("Error : the message to encrypt is not only composed of minuscule letters");
        }

    }

    /**
     * encrypts the message given with the enigma algorithm
     * @param message message to encrypt
     * @param wheelPositions array with the positions of each wheel in order, technically would work with any size
     * @return encrypted message
     */
    public static String encryptEnigma(String message, int[] wheelPositions) {
        // sanitize the message just in case
        char[] data = sanitizeMessage(message.toLowerCase().toCharArray());

        // loop for each character of the message
        for (int j = 0; j < data.length; j++) {
            // rotors
            for (int i = 0; i < wheelPositions.length; i++) {
                data[j] = getLetterWithOffset(data[j], wheelPositions[i]);
            }

            // mirror the character
            data[j] = mirrorChar(data[j]);

            // rotors reverse
            for (int i = wheelPositions.length - 1; i >= 0; i--) {
                data[j] = getLetterWithOffset(data[j], -wheelPositions[i]); // Apply the reverse offset
            }

            // rotate the rotors after each character is processed
            for (int i = 0; i < wheelPositions.length; i++) {
                wheelPositions[i] = (wheelPositions[i] + 1) % alphabet.length();  // Rotate the rotor position
            }
        }
        return new String(data);
    }

    /**
     * decrypts the message given with the enigma algorithm
     * @param message message to decrypt
     * @param wheelPositions array with the positions of each wheel in order, technically would work with any size
     * @return decrypted message
     */
    public static String decryptEnigma(String message, int[] wheelPositions) {
        // sanitize the message just in case
        char[] data = sanitizeMessage(message.toLowerCase().toCharArray());

        // loop for each character of the message
        for (int j = 0; j < data.length; j++) {
            // Apply rotor shifts in reverse (decrypting)
            for (int i = wheelPositions.length - 1; i >= 0; i--) {
                data[j] = getLetterWithOffset(data[j], -wheelPositions[i]);  // Reverse rotor shift
            }

            // mirror the character (reflector)
            data[j] = mirrorChar(data[j]);

            // apply rotor shifts forward (decrypting reverse process)
            for (int i = 0; i < wheelPositions.length; i++) {
                data[j] = getLetterWithOffset(data[j], wheelPositions[i]);  // Forward rotor shift
            }

            // rotate the rotors after each character is processed
            for (int i = 0; i < wheelPositions.length; i++) {
                wheelPositions[i] = (wheelPositions[i] + 1) % alphabet.length();
                if (i < wheelPositions.length - 1 && wheelPositions[i] == 0) {
                    wheelPositions[i + 1] = (wheelPositions[i + 1] + 1) % alphabet.length();
                }
            }
        }
        return new String(data);
    }

    /**
     * returns the character at the specified offset of the alphabet. Loops the alphabet if position is longer than the alphabet length.
     * @param letter the letter to search in the alphabet
     * @param offset offset to add or subtract
     * @return letter at the specified offset
     */
    public static char getLetterWithOffset(char letter, int offset) {
        int charIndex = alphabet.indexOf(letter);
        // loop alphabet
        charIndex = (charIndex + offset + alphabet.length()) % alphabet.length();
        return alphabet.charAt(charIndex);
    }

    /**
     * mirrors on the alphabet each letter of the character list
     * @param character character we want to mirror
     * @return the mirrored character
     */
    public static char mirrorChar(char character) {
        int index = alphabet.indexOf(character);
        return reversedAlphabet.charAt(index);
    }

    /**
     * sanitizes a message to make it suitable for enigma
     * @param message message to sanitize
     * @return sanitized message
     */
    public static char[] sanitizeMessage(char[] message) {
        // dynamic list of characters
        List<Character> cleanMessageData = new ArrayList<>();
        // iterate over each
        for (int i = 0; i < message.length; i++) {
            for (int j = 0; j < alphabet.length(); j++) {
                if(message[i] == alphabet.charAt(j)) {
                    cleanMessageData.add(message[i]);
                }
            }
        }

        // convert to char array (because it's size is fixed)
        char[] cleanMessage = new char[cleanMessageData.size()];
        for (int i = 0; i < cleanMessageData.size(); i++) {
            cleanMessage[i] = cleanMessageData.get(i);
        }
        return cleanMessage;
    }

    /**
     * checks if a string contains only a number between 0 and 25
     * @param str string containing the number
     * @return whether the string is a valid number between 0 and 25
     */
    public static boolean isValidNumber(String str) {
        if (str.matches("^[0-9]{1,2}$")) {
            int number = Integer.parseInt(str);
            return number >= 0 && number <= 25;
        }
        return false;
    }
}
