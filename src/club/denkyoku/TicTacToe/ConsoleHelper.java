package club.denkyoku.TicTacToe;

public class ConsoleHelper {
    static void CleanConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
