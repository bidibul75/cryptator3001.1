package src;

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
    public static int getCodeFromChar(char c) throws Exception {
        // Checks if the message to encrypt contains only letters
        if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            throw new IllegalArgumentException("Error : the message to encrypt is not only composed of letters");
        }
        // Gets the respective code from a character in the enum
        try {
            return valueOf(String.valueOf(Character.toUpperCase(c))).getCode();
        } catch (IllegalArgumentException e) {
            // If the character is not found in the Polybe enum, as we know at that point that the character is
            // a letter, it means that le Polybe enum lacks a letter
            throw new Exception("Some letter lacks in the Polybe enum.");
        }
    }

    public static char getCharFromCode(int intToAnalyse) {
        for (Polybe item : values()) {
            if (item.getCode() == intToAnalyse) {
                return item.name().charAt(0);
            }
        }
        return '-';
    }

    // Encryption method
    public static String encryption(String messageToCode) throws Exception {
        String encodedMessage = "";
        for (int i = 0; i < messageToCode.length(); i++) {
            encodedMessage += String.valueOf(getCodeFromChar(messageToCode.charAt(i)));
        }
        return encodedMessage;
    }

    // Decryption method
    public static String decryption(String codedMessage) {
        String sliceString = "", decodedMessage = "", errorMessage = "";
        int sliceInt = 0;
        // Errors detection on the coded message sent in parameter : message length is odd and/or non-numeric item in the message
        if (codedMessage.length() % 2 != 0) errorMessage += "Error : coded message length isn't even.\n";
        boolean isNumeric = Pattern.matches("^\\d+$", codedMessage);
        if (!isNumeric) errorMessage += "Error : coded message doesn't contains only numbers.";
        if (errorMessage != "") return errorMessage;

        try {
            // Makes slices of 2 numbers from the coded message then sends each slide to the method that returns the character
            for (int i = 0; i < codedMessage.length(); i += 2) {
                sliceString = codedMessage.substring(i, i + 2);
                sliceInt = Integer.parseInt(sliceString);
                decodedMessage += getCharFromCode(sliceInt);
            }
            return decodedMessage.toLowerCase();
        } catch (IndexOutOfBoundsException e) {
            return "Error on coded message length";
        }
    }
}

