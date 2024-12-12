package src;
import src.Enigma;


import src.Menu;

public class Main {
    // entry point

    public static void main(String[] args) {
       Menu menu = new Menu();
            // Polybe
            Polybe.Start();
            // End of Polybe

            // RC4
            RC4.Start();
            // End of RC4
        Menu menu = new src.Menu();
        Enigma enigma = new Enigma();
//                // Polybe
//        String sbPolybe = "Walter";
//        System.out.println(sbPolybe);
//        System.out.println(Polybe.encryption(sbPolybe));
//        System.out.println(Polybe.decryption("t66"));
        // End of Polybe

        // Enigma test code
        int[] wheels = {1, 3};
        String encoded = enigma.encryptEnigma("rerewre", wheels);
        System.out.println(encoded);
        String decoded = enigma.decryptEnigma(encoded, wheels);
        System.out.println(decoded);

    public static void main(String[] args) throws Exception {
       


    }
}
