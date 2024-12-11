package src;


import src.Menu;

public class Main {
    // entry point
    public static void main(String[] args) {
        // instanciate our classes
        Menu menu = new src.Menu();

        // menu start
        String[] menuOptions = {"Save password", "Help"};
        String input = menu.displayMainMenu(menuOptions);
    }
}