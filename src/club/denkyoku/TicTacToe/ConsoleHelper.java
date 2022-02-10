package club.denkyoku.TicTacToe;

import java.io.PrintWriter;

public class ConsoleHelper {
    private static final PrintWriter printWriter = new PrintWriter(System.out, true);

    static void CleanConsole() {
        printWriter.print("\033[H\033[2J");
    }

    static void println(String str) {
        printWriter.println(str);
    }

    static void print(String str) {
        printWriter.print(str);
    }
}
