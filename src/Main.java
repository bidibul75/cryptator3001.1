package src;


import src.Menu;

public class Main {
    // entry point
    public static void main(String[] args) throws Exception {
        Menu menu = new Menu();
        // Polybe
        Polybe.Start();
        // End of Polybe

        // RC4
        System.out.println("\n---RC4---------------------------");
        String plaintext = "Hello, RC4!";
        String key = "mysecretkey";

        // Initialisation de RC4 avec la clé
        RC4 rc4 = new RC4(key.getBytes());

        // Chiffrement
        byte[] encrypted = rc4.encryptDecrypt(plaintext.getBytes());
        System.out.println("Texte chiffré : " + new String(encrypted));

        // Déchiffrement (utilise le même flux RC4)
        RC4 rc4Decrypt = new RC4(key.getBytes());
        byte[] decrypted = rc4Decrypt.encryptDecrypt(encrypted);
        System.out.println("Texte déchiffré : " + new String(decrypted));


        // End of RC4

    }
}
