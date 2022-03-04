package club.denkyoku.tictactoe.services.output.terminal;

import club.denkyoku.tictactoe.libraries.os.RawConsoleInput;
import club.denkyoku.tictactoe.libraries.os.ShellHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;


public class ConsoleHelper {
    // Used for Linux to output UTF-8
    protected static final PrintWriter printWriterUnix = new PrintWriter(System.out, true);

    // Used for Windows to output UTF-8
    protected static PrintStream outStreamWin = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    protected static String[] lastScreen = new String[]{""};

    private static boolean windowsRunOnce = true;

    private static void winRunOnce() {
        if (windowsRunOnce) {
            try {
                new ProcessBuilder("cmd", "/c", "chcp 65001").inheritIO().start().waitFor();
            } catch (IOException | InterruptedException e) {
            }
            windowsRunOnce = false;
        }
    }

    /**
     * Helper method to clear the console.
     */
    public static void CleanConsole() {
        if (RawConsoleInput.getIsWindows()) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (IOException | InterruptedException e) {
            }
        } else {
            printWriterUnix.print("\033[H\033[2J");
        }
    }

    /**
     * Helper method to print UTF-8 into the console.
     * @param str The string to print.
     */
    public static void println(String str) {
        if (RawConsoleInput.getIsWindows()) {
            winRunOnce();
            outStreamWin.println(str);
        } else {
            printWriterUnix.println(str);
        }
    }

    /**
     * Helper method to print UTF-8 into the console.
     * @param str The string to print.
     */
    public static void print(String str) {
        if (RawConsoleInput.getIsWindows()) {
            winRunOnce();
            outStreamWin.print(str);
        } else {
            printWriterUnix.print(str);
        }
    }

    /**
     * Helper method to print UTF-8 into the console.
     * @param c The character to print.
     */
    public static void print(char c) {
        if (RawConsoleInput.getIsWindows()) {
            winRunOnce();
            outStreamWin.print(c);
        } else {
            printWriterUnix.print(c);
        }
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
     *
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
     * <p>
     * Useful when you want to restore the screen after a screen change.
     *
     * @return The previous screen strings.
     */
    public static String[] GetLastScreen() {
        return ConsoleHelper.lastScreen;
    }

    /**
     * Helper method to get the console width.
     *
     * @return The width of console window.
     */
    public static int GetConsoleWidth() {
        try {
            // TODO: add support for Windows
            ShellHelper.Result result = ShellHelper.shell("tput cols");
            if (result.ret == 0) {
                return Integer.parseInt(result.output);
            }
        } catch (IOException e) {
        }
        return 80; //default value
    }

    /**
     * Helper method to get the console height.
     *
     * @return The height of console window.
     */
    public static int GetConsoleHeight() {
        try {
            // TODO: add support for Windows
            ShellHelper.Result result = ShellHelper.shell("tput lines");
            if (result.ret == 0) {
                return Integer.parseInt(result.output);
            }
        } catch (IOException e) {
        }
        return 24; //default value
    }
}
