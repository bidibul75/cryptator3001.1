package src;
import java.util.Scanner;

public class Menu {
    public int currentOption = 0;
    public String currentMenu = "main";
    private static Scanner scanner = new Scanner(System.in);

    /**
     * @param menuOptions Array of strings representing menu options
     */
    public static String displayMainMenu(String[] menuOptions) {
        clearConsole();
        System.out.print("\n --- Enter your choice --- \n");

        // display every option + current selection
        for(int i = 0; i < menuOptions.length; i++ ) {
                System.out.print("(" + i + ")" + menuOptions[i] + "\n");
        };

        String input = scanner.nextLine();
        return input;
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
