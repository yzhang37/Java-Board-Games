package club.denkyoku.TicTacToe.Library.Terminal;

public class DebugHelper {
    // print the char buffer within int code.
    static public void viewKeyBuffer(char[] buffer) {
        System.out.print("\"");
        for (int c : buffer) {
            if (c != '\0') {
                System.out.print(c);
                System.out.print(" ");
            } else
                break;
        }
        System.out.print("\"");
    }
}
