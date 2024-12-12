package src;


import src.Menu;

public class Main {
    // entry point
    public static void main(String[] args) throws Exception {
        Menu menu = new Menu();
        // Enigma
        Enigma.Start();
        // End of Enigma

        // Polybe
        Polybe.Start();
        // End of Polybe

        // RC4
        RC4.Start();
        // End of RC4

    }
}