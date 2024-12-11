package src;
import java.util.Scanner;

public class Menu {
    public int currentOption = 0;
    public String currentMenu = "main";
    private static Scanner scanner = new Scanner(System.in);

    /**
     * @param menuOptions Array of strings representing menu options
     * @return chosen option by the user
     */
    public static int displayOptions(String[] menuOptions) {
        clearConsole();
        System.out.print("\n --- Enter your choice --- \n");

        // display every option
        for(int i = 0; i < menuOptions.length; i++ ) {
                System.out.print("(" + i + ")" + menuOptions[i] + "\n");
        };
        return getOptionNumberFromInput(menuOptions.length - 1);
    };

    /**
     *
     * @param prompt prompt to display
     * @return user input
     */
    public static String getInput(String prompt) {
        clearConsole();
        System.out.print("\n" + prompt + "\n");
        return scanner.nextLine();
    };

    /**
     * Clears console depending on the OS.
     */
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
            System.out.print("\nError while clearing the console\n");
        }
    }

    /**
     *
     * @param optionCount (int) the maximum number of options
     * @return (int) selected option
     */
    public static int getOptionNumberFromInput(int optionCount) {
        int chosenOption = 0;
        boolean validInput = false;
        while (!validInput) {
            String input = scanner.nextLine();
            try {
                chosenOption  = Integer.parseInt(input);
                if(chosenOption <= optionCount) {
                    validInput = true;
                } else {
                    System.out.print("\nWrong input!\n");
                }
            }
            catch (NumberFormatException e) {
                System.out.print("\nWrong input!\n");
            }
        }
        return chosenOption;
    }
}
