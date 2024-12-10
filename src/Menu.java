package src;

public class Menu {
    public int currentOption = 0;
    public String currentMenu = "main";
    public static String[] menuOptions = {"Save password", "Help"};

    // display main menu
    public static void displayMainMenu(int selectedOption) {
        clearConsole();
        System.out.print("\n --- main_menu\n");

        // display every option + current selection
        for(int i = 0; i < menuOptions.length; i++ ) {
            if(i == selectedOption) {
                System.out.print("> " + menuOptions[i] + "\n");
            } else {
                System.out.print("  " + menuOptions[i] + "\n");
            }
        };
    };

    // clear console depending on os
    public static void clearConsole() {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}
