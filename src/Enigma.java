package src;

public class Enigma {

    private final char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * encrypts the message given with the enigma algorithm
     * @param message message to encrypt
     * @param wheelPositions array with the positions of each wheel in order, technically would work with any size
     * @return encrypted message
     */
    String encryptEnigma(String message, int[] wheelPositions) {
        char[] data = message.toLowerCase().toCharArray();

        // run the wheel offsets as many times as we want
        for (int position : wheelPositions) {
            for (int j = 0; j < message.length(); j++) {
                data[j] = getLetterWithOffset(data[j], position, false);
            }
        }

        // mirror on the alphabet
        mirrorCharList(data);

        // re-run the wheels again
        for (int position : wheelPositions) {
            for (int j = 0; j < message.length(); j++) {
                data[j] = getLetterWithOffset(data[j], position, false);
            }
        }

        return new String(data);
    }

    String decryptEnigma(String message, int[] wheelPositions) {
        char[] data = message.toLowerCase().toCharArray();

        // re-run the wheel offsets in reverse order (should be the same input as encoding function call)
        for (int i = wheelPositions.length - 1; i >= 0; i--) {
            for (int j = 0; j < message.length(); j++) {
                // reverse the offset
                data[j] = getLetterWithOffset(data[j], wheelPositions[i], true);
            }
        }

        // mirror again
        mirrorCharList(data);

        // same thing as above to completely reverse the algorithm
        for (int i = wheelPositions.length - 1; i >= 0; i--) {
            for (int j = 0; j < message.length(); j++) {
                data[j] = getLetterWithOffset(data[j], wheelPositions[i], true);  // Inverse offset for decryption
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
    char getLetterWithOffset(char letter, int offset, boolean inverse) {
        int firstIndex = firstIndexOfChar(alphabet, letter);
        if (inverse) {
            // subtract the offset and wrap around if needed
            return alphabet[(firstIndex - offset + alphabet.length) % alphabet.length];
        } else {
            // addition the offset and wrap around if needed
            return alphabet[(firstIndex + offset) % alphabet.length];
        }
    }

    /**
     * mirrors on the alphabet each letter of the character list
     * @param charList list of characters we want to mirror
     */
    void mirrorCharList(char[] charList) {
        for (int i = 0; i < charList.length; i++) {
            int firstIndex = firstIndexOfChar(alphabet, charList[i]);

            // invert char position according to the size of the alphabet
            int mirroredIndex = alphabet.length - 1 - firstIndex;
            charList[i] = alphabet[mirroredIndex];
        }
    }

    /**
     * return the first index of the character in the char array
     * @param charList char array to iterate over
     * @param character character to check
     * @return index of the first instance of the character
     */
    int firstIndexOfChar(char[] charList, char character) {
        int number = -1; // if the element is not in the list
        for (int i = 0; i < charList.length; i++) {
            if (charList[i] == character) {
                number = i;
                break;
            }
        }
        return number;
    }
}
