package club.denkyoku.TicTacToe;

public class Utility {
    public static String Repeat(String s, int n) {
        return new String(new char[n]).replace("\0", s);
    }
}
