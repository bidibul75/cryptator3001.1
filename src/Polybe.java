package src;

import java.util.regex.Pattern;

enum Polybe {
    A(11), B(12), C(13), D(14), E(15),
    F(21), G(22), H(23), I(24), J(25),
    K(31), L(32), M(33), N(34), O(35),
    P(41), Q(42), R(43), S(44), T(45),
    U(51), V(52), W(53), X(54), Y(55), Z(56);

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

        if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
           throw new IllegalArgumentException("Error : the message to encrypt is not only composed of letters");
        }
        try {
            return valueOf(String.valueOf(Character.toUpperCase(c))).getCode();
        } catch (IllegalArgumentException e) {
            return 0; // Returns 0 for unfound characters
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
    public static String encryption(String messageToCode) {
        String encodedMessage = "";
        for (int i = 0; i < messageToCode.length(); i++) {
            encodedMessage += getCodeFromChar(messageToCode.charAt(i));
        }
        return encodedMessage;
    }
    // Decryption method
    public static String decryption(String codedMessage) {
        String sliceString = "", decodedMessage = "", errorMessage="";
        int sliceInt = 0;
        // Error detection
        if (codedMessage.length() % 2 != 0) errorMessage += "Error : coded massage length isn't even.\n";
        boolean isNumeric = Pattern.matches("^\\d+$", codedMessage);
        if (!isNumeric) errorMessage += "Error : coded message doesn't contains only numbers.";
        if (errorMessage !="") return errorMessage;

        try {
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

