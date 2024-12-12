package src;


import src.Menu;

public class Main {
    // entry point
    public static void main(String[] args) throws Exception {
        Menu menu = new Menu();
        
        //Steganographie
        Steganography.start();
        // End of Steganographie

        // Polybe
        Polybe.start();
        // End of Polybe

        // RC4
        RC4.start();
        // End of RC4


    }
}
