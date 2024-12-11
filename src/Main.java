package src;


import src.Menu;

public class Main {
    // entry point
    public static void main(String[] args) {
        Menu menu = new src.Menu();
                // Polybe
        String sbPolybe = "Walter";
        System.out.println(sbPolybe);
        System.out.println(Polybe.encryption(sbPolybe));
        System.out.println(Polybe.decryption("t66"));
        // End of Polybe
    }
}
