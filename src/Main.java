package src;

import src.Menu;
import src.Caesar;
import src.LFSR;
import src.MD5;
import src.Polybe;
import src.Enigma;
import src.Vigenere;
import src.RC4;
import src.Sha256;
import src.AES;
import src.Steganography;
import src.Hmac;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean running = true;
        Menu mainMenu = new Menu();

        while (running) {
            // Affiche le titre de l'
            //application
            mainMenu.clearConsole();
            System.out.println("=== Cryptator3001.1 ===");

            // Options du menu principal
            String[] mainMenuOptions = {
                    "Encryption/Decryption Tools",
                    "Help",
                    "Exit"
            };

            // Affiche les options et récupère le choix
            int choice = mainMenu.displayOptions(mainMenuOptions);

            // Gère les choix de l'utilisateur
            switch (choice) {
                case 0: // Encryption/Decryption Tools
                    encryptionDecryptionMenu();
                    break;
                case 1: // Help
                    displayHelp();
                    break;
                case 2: // Exit
                    System.out.println("\nExiting Cryptator3001.1. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\nUnexpected error.");
            }
        }
    }

    /**
     * Displays the Encryption/Decryption Tools menu.
     */
    private static void encryptionDecryptionMenu() {
        Scanner scanner = new Scanner(System.in);

        String[] toolsMenuOptions = {
                "Caesar",
                "Vigenere",
                "Polybe",
                "LFSR",
                "RC4",
                "MD5",
                "SHA-256",
                "Steganographie",
                "AES",
                "HMAC",
                "Back to Main Menu"
        };

        boolean toolsRunning = true;
        while (toolsRunning) {
            int choice = Menu.displayOptions(toolsMenuOptions);

            switch (choice) {
                case 0:
                    System.out.println("\nLaunching Caesar...");
                    Caesar.start(scanner);
                    break;
                case 1:
                    System.out.println("\nLaunching Vigenere...");
                    Vigenere.start(scanner);
                    break;
                case 2:
                    System.out.println("\nLaunching Polybe...");
                    Polybe.start();
                    break;
                case 3:
                    System.out.println("\nLaunching LFSR...");
                    LFSR.start(scanner);
                    break;
                case 4:
                    System.out.println("\nLaunching RC4...");
                    RC4.start();
                    break;
                case 5:
                    System.out.println("\nLaunching MD5...");
                    MD5.start(scanner);
                    break;
                case 6:
                    System.out.println("\nLaunching SHA-256...");
                    Sha256.start(scanner);
                    break;
                case 7:
                    System.out.println("\nLaunching Steganography...");
                    Steganography.start();
                    break;
                case 8:
                    System.out.println("\nLaunching AES...");
                    AES.start(scanner);
                    break;
                case 9:
                    System.out.println("\nLaunching HMAC...");
                    Hmac.start(scanner);
                    break;
                case 10:
                    toolsRunning = false;
                    break;
                default:
                    System.out.println("\nUnexpected error.");
            }
        }
    }

    /**
     * Displays the help menu with a brief description of the project and its functionalities,
     * subtly hiding a clue for the Easter egg.
     */
    private static void displayHelp() {
        String helpText = """
                === Help Menu ===
                Welcome to Cryptator3001.1!
                
                About the project:
                Cryptator3001.1 is a tool designed for securely encrypting, decrypting,
                and hashing passwords. The goal is to provide a reliable way to handle
                sensitive information for secure storage and integrity verification.
                
                Features:
                - Caesar: Encrypt or decrypt messages by shifting letters by a fixed number of positions in the alphabet.
                - Vigenere: Use a keyword to encrypt or decrypt messages with a polyalphabetic substitution cipher.
                - Polybe: Encode messages into pairs of coordinates using a grid-based substitution cipher.
                - LFSR (Linear Feedback Shift Register): Generate pseudo-random numbers or encrypt data by shifting bits with feedback.
                - RC4 (Rivest Cipher 4): Stream cipher that generates a pseudo-random bit stream for fast encryption and decryption.
                - MD5 (Message Digest 5): Generate an MD5 hash for verifying the integrity of passwords.
                - SHA-256 (Secure Hash Algorithm 256-bit): Generate a 256-bit cryptographic hash for secure data integrity and verification.
                - Steganography : Hides a message in a png image stored in the repository.
                - HMAC (hash-based message authentication code) Encodes an HMAC with the given data and compares it to a given hexadecimal HMAC to guarantee authenticity.
                
                Usage:
                Select an option from the main menu by entering its number.
                
                For encryption and decryption, follow the specific prompts for each tool.
                """;

        Menu.clearConsole();
        System.out.println(helpText);

        // Hidden clue as fragmented text
        String hiddenClue = """
                Password security best practices:
                - Protect your passwords with strong encryption. 
                - Only use unique passwords for each account. 
                - Limit sharing sensitive information online. 
                - Your passwords should be long and complex. 
                - Be aware of phishing attempts. 
                - Enable two-factor authentication when possible. 
                - Ensure your passwords are stored securely. 
                - Avoid reusing old passwords across platforms. 
                - Secure your accounts with regular updates. 
                - Test the strength of your passwords regularly. 
                - Encrypt sensitive files before storing them. 
                - Rotate passwords periodically for added security. 
                """;

        System.out.println(hiddenClue);
        System.out.println("\nPress Enter to return to the main menu...");
        Menu.getInput("");
    }
}