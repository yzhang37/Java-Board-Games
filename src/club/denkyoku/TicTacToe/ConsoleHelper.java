package club.denkyoku.TicTacToe;

import java.io.IOException;
import java.io.PrintWriter;

public class ConsoleHelper {
    // TODO: add support for Windows, to display Unicodes.

    protected static final PrintWriter printWriter = new PrintWriter(System.out, true);
    protected static String[] lastScreen = new String[]{""};

    public static void CleanConsole() {
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

    public static void bell() {
        print("\007");
    }

    public static void printScreen(String[] screen) {
        ConsoleHelper.CleanConsole();
        ConsoleHelper.lastScreen = screen;
        for (String line : screen) {
            ConsoleHelper.println(line);
        }
    }

    public static String[] GetLastScreen() {
        return ConsoleHelper.lastScreen;
    }

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
