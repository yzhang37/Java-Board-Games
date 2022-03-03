package club.denkyoku.TicTacToe;

public class Main {

    public static void main(String[] args) {

        TicTacToe game = new TicTacToe();
        game.start();

        // TODO: don't leave it here
        // enable echo before exit
        SttyHelper.enableEcho();
    }
}
