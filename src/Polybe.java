package src;

import java.util.Scanner;
import java.util.regex.Pattern;

// Snail Polybe code with 26 characters (i/j or v/w substitutions not used).
enum Polybe {
    A(33), B(32), C(42), D(43), E(44),
    F(34), G(24), H(23), I(22), J(21),
    K(31), L(41), M(51), N(52), O(53),
    P(54), Q(55), R(45), S(35), T(25),
    U(15), V(14), W(13), X(12), Y(11), Z(16);
    private final int code;

    // Constructor
    Polybe(int code) {
        this.code = code;
    }

    // Method to get the code
    public int getCode() {
        return code;
    }

    // Method to get the code from a char
    public static int getCodeFromChar(char c) {
        // Checks if the message to encrypt contains only letters
        if (c == ' ') return 57; // 57 stands for spaces in the message to encrypt
        // Gets the respective code from a character in the enum
        try {
            return valueOf(String.valueOf(Character.toUpperCase(c))).getCode();
        } catch (IllegalArgumentException e) {
            // If the character is not found in the Polybe enum, as we know at that point that the character is
            // a letter, it means that le Polybe enum lacks a letter. We return the code 999 to spot that.
            return 999;
        }
    }

    public static char getCharFromCode(int intToAnalyse) {
        if (intToAnalyse == 57) return ' '; // 57 stands for a space in the encrypted message
        for (Polybe item : values()) {
            if (item.getCode() == intToAnalyse) {
                return item.name().charAt(0);
            }
        }
        return '-';
    }

    // Encryption method
    public static String encryption(String messageToCode) {
        if (messageToCode.equalsIgnoreCase("easter")) {
            System.out.println("""
                            ,~.
                          ,-'__ `-,
                         {,-'  `. }              ,')
                        ,( a )   `-.__         ,',')~
                       <=.) (         `-.__,==' ' ' }
                         (   )                      /
                          `-'\\\\   ,                    )
                              |  \\\\        `~.        /        ..---..
                              \\\\   `._        \\\\      /      /         \\\s
                               \\\\     `._____,'    ,'       /           \\\s
                                `-.             ,'          :            ;
                                   `-._     _,-'            \\           /
                                       `""\"`                 `---___---'
                    """);
            return "366";
        }
        String encodedMessage = "", codeTemp;
        for (int i = 0; i < messageToCode.length(); i++) {
            codeTemp = String.valueOf(getCodeFromChar(messageToCode.charAt(i)));
            if (codeTemp.equals("999")) {
                System.out.println("Error : the character has not been found in the Polybe array.");
                System.exit(1);
            }
            encodedMessage += codeTemp;
        }
        return encodedMessage;
    }

    // Decryption method
    public static String decryption(String codedMessage) {
        if (codedMessage.equalsIgnoreCase("366")) return "easter";

        String sliceString="", decodedMessage="";
        int sliceInt;
        try {
            // Makes slices of 2 numbers from the coded message then sends each slide to the method that returns the character
            for (int i = 0; i < codedMessage.length(); i += 2) {
                sliceString = codedMessage.substring(i, i + 2);
                sliceInt = Integer.parseInt(sliceString);
                if (getCharFromCode(sliceInt) == '-') {
                    return "Error : One or more numbers do not match encoded characters.";
                }
                decodedMessage += getCharFromCode(sliceInt);
            }
            return decodedMessage.toLowerCase();
        } catch (IndexOutOfBoundsException e) {
            return "Error on coded message length";
        }
    }

    public static void start() {
        System.out.println("\n---POLYBE---------------------------");

        Scanner scanner = new Scanner(System.in);
        String choice, messageToEncrypt, messageToDecrypt;
        StringBuilder errorMessage= new StringBuilder();
        do {
            System.out.print("POLYBE - do you want to encrypt (E), decrypt (D) or exit (Q) ? : ");
            choice = scanner.nextLine();
            choice = choice.toUpperCase();
            switch (choice) {
                case "E":
                    do {
                        System.out.print("Please enter the message to encrypt (letters and spaces only) : ");
                        messageToEncrypt = scanner.nextLine();
                    } while (messageToEncrypt.isEmpty());
                    messageToEncrypt = messageToEncrypt.trim();
                    if (Pattern.matches("^[a-zA-Z ]*$", messageToEncrypt)) {
                        System.out.println("Encrypted message : " + Polybe.encryption(messageToEncrypt));
                        choice="";
                        break;
                    }
                    System.out.println("Error : the message to encrypt is not only composed of letters and spaces");
                    choice = "";
                    break;
                case "D":
                    do {
                        System.out.print("Please enter the message to decrypt (numbers only) : ");
                        messageToDecrypt = scanner.nextLine();
                    } while (messageToDecrypt.isEmpty());
                    messageToDecrypt = messageToDecrypt.trim();

                    // Errors detection on the coded message sent in parameter : message length is odd and/or non-numeric item in the message
                    if (messageToDecrypt.length() % 2 != 0)
                        errorMessage.append("Error : encrypted message length isn't even.\n");
                    boolean isNumeric = Pattern.matches("^\\d+$", messageToDecrypt);
                    if (!isNumeric) errorMessage.append("Error : encrypted message doesn't contains only numbers.");
                    if (!errorMessage.isEmpty()) {
                        System.out.println(errorMessage);
                        errorMessage.setLength(0);
                    } else {
                        String decryptionResult = Polybe.decryption(messageToDecrypt);
                        if (decryptionResult.length()>6 &&decryptionResult.substring(0, 5).equalsIgnoreCase("Error")) {
                            System.out.println(decryptionResult);
                        } else {
                            System.out.println("Decrypted message : " + decryptionResult);
                        }
                    }
                    choice = "";
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Please enter a correct answer.");
                    choice = "";
                    break;
            }
        } while (choice.isEmpty());
    }
}