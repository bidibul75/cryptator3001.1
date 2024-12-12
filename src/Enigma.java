package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Enigma {

    private static final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * @throws Exception
     */
    public static void Start(){
        System.out.println("\n---ENIGMA---------------------------");
        Scanner scanner = new Scanner(System.in);

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
                System.out.print("ENIGMA - Which position for the first wheel (0 - 26)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[0] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            while (true) {
                System.out.print("ENIGMA - Which position for the first wheel (0 - 26)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[1] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            while (true) {
                System.out.print("ENIGMA - Which position for the first wheel (0 - 26)? : ");
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
                System.out.print("ENIGMA - Which position for the first wheel (0 - 26)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[1] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            while (true) {
                System.out.print("ENIGMA - Which position for the second wheel (0 - 26)? : ");
                choice = scanner.nextLine();
                if (isValidNumber(choice)) {
                    wheel[2] = Integer.parseInt(choice);
                    break;
                }
                else System.out.println("Please answer correctly to the question.");
            }

            while (true) {
                System.out.print("ENIGMA - Which position for the third wheel (0 - 26)? : ");
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
                System.out.println("Encrypted message : " + Enigma.decryptEnigma(messageToEncrypt, wheel));
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
        char[] data = sanitizeMessage(message.toLowerCase().toCharArray());

        for (int i = 0; i < wheelPositions.length; i++) {
            for (int j = 0; j < data.length; j++) {
                data[j] = getLetterWithOffset(data[j], wheelPositions[i], false);
                data[j] = mirrorChar(data[j]);
                data[j] = getLetterWithOffset(data[j], wheelPositions[(wheelPositions.length - 1) - i], false);
            }
        }
        return new String(data);
    }

    public static String decryptEnigma(String message, int[] wheelPositions) {
        char[] data = sanitizeMessage(message.toLowerCase().toCharArray());

        for (int i = wheelPositions.length - 1; i >= 0; i--) {
            for (int j = 0; j < data.length; j++) {
                data[j] = getLetterWithOffset(data[j], wheelPositions[(wheelPositions.length - 1) - i], true);
                data[j] = mirrorChar(data[j]);
                data[j] = getLetterWithOffset(data[j], wheelPositions[i], true);
            }
        }
        return new String(data);
    }

    /**
     * returns the character at the specified offset of the alphabet. Loops the alphabet if position is longer than the alphabet length.
     * @param letter the letter to search in the alphabet
     * @param offset offset to add or subtract
     * @param inverse whether to reverse the offset (used for decryption)
     * @return letter at the specified offset
     */
    public static char getLetterWithOffset(char letter, int offset, boolean inverse) {;
        int firstIndex = firstIndexOfChar(alphabet, letter);
        if (inverse) {
            // subtract the offset and wrap around if needed
            int index;
            if(firstIndex - offset < 0) {
                index = (alphabet.length - 1) + firstIndex - offset;
            } else {
                index = firstIndex - offset;
            }
            return alphabet[index];
        } else {
            // addition the offset and wrap around if needed
            int index;
            if(firstIndex + offset >= alphabet.length) {
                index = (alphabet.length - 1) + firstIndex - offset;
            } else {
                index = firstIndex + offset;
            }
            return alphabet[index];
        }
    }

    /**
     * mirrors on the alphabet each letter of the character list
     * @param character character we want to mirror
     */
    public static char mirrorChar(char character) {
        int firstIndex = firstIndexOfChar(alphabet, character);
        int mirroredIndex;
            // invert char position according to the size of the alphabet
        if(firstIndex < alphabet.length/2) {
            mirroredIndex = (alphabet.length/2) - (alphabet.length/2 - firstIndex);
        } else if (firstIndex > alphabet.length/2) {
            mirroredIndex = (alphabet.length/2) + (alphabet.length/2 - firstIndex);
        } else {
            mirroredIndex = firstIndex;
        }

        return alphabet[mirroredIndex];
    }

    /**
     * return the first index of the character in the char array
     * @param charList char array to iterate over
     * @param character character to check
     * @return index of the first instance of the character
     */
    public static int firstIndexOfChar(char[] charList, char character) {
        int number = -1; // if the element is not in the list
        for (int i = 0; i < charList.length; i++) {
            if (charList[i] == character) {
                number = i;
                break;
            }
        }
        return number;
    }

    public static char[] sanitizeMessage(char[] message) {
        // dynamic list of characters
        List<Character> cleanMessageData = new ArrayList<>();
        // iterate over each
        for (int i = 0; i < message.length; i++) {
            for (int j = 0; j < alphabet.length; j++) {
                if(message[i] == alphabet[j]) {
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

    public static boolean isValidNumber(String str) {
        if (str.matches("^[0-9]{1,2}$")) {
            int number = Integer.parseInt(str);
            return number >= 0 && number <= 25;
        }
        return false;
    }
}
