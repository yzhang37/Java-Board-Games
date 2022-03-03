package club.denkyoku.TicTacToe.Services.Output.Terminal;

import club.denkyoku.TicTacToe.Libraries.OS.ShellHelper;

import java.io.IOException;
import java.io.PrintWriter;


public class ConsoleHelper {
    // TODO: add support for Windows, to display Unicodes.

    protected static final PrintWriter printWriter = new PrintWriter(System.out, true);
    protected static String[] lastScreen = new String[]{""};

    /**
     * Helper method to clear the console.
     */
    public static void CleanConsole() {
        // TODO: add support for Windows
        printWriter.print("\033[H\033[2J");
    }

    public static void println(String str) {
        printWriter.println(str);
    }

    public static void print(String str) {
        printWriter.print(str);
    }

    public static void print(char c) {
        printWriter.print(c);
    }

    /**
     * Helper method to make the terminal beep.
     */
    public static void bell() {
        print("\007");
    }

    /**
     * Helper method to print the new screen, and
     * update the last screen strings storage.
     * <br/>
     * If you want to do screen restore, please use <code>GetLastScreen()</code> method
     * before this call.
     * @param screen The new screen strings.
     */
    public static void printScreen(String[] screen) {
        ConsoleHelper.CleanConsole();
        ConsoleHelper.lastScreen = screen;
        for (String line : screen) {
            ConsoleHelper.println(line);
        }
    }

    /**
     * Get the previous screen strings.
     *
     * Useful when you want to restore the screen after a screen change.
     * @return The previous screen strings.
     */
    public static String[] GetLastScreen() {
        return ConsoleHelper.lastScreen;
    }

    /**
     * Helper method to get the console width.
     * @return The width of console window.
     */
    public static int GetConsoleWidth() {
        try {
            // TODO: add support for Windows
            ShellHelper.Result result = ShellHelper.shell("tput cols");
            if (result.ret == 0) {
                return Integer.parseInt(result.output);
            }
        } catch (IOException e) {}
        return 80; //default value
    }

    /**
     * Helper method to get the console height.
     * @return The height of console window.
     */
    public static int GetConsoleHeight() {
        try {
            // TODO: add support for Windows
            ShellHelper.Result result = ShellHelper.shell("tput lines");
            if (result.ret == 0) {
                return Integer.parseInt(result.output);
            }
        } catch (IOException e) {}
        return 24; //default value
    }
}
