package club.denkyoku.TicTacToe;

public class Main {

    public static void main(String[] args) {
        SttyHelper.disableEcho();
        SttyHelper.bufferByCharacter();

        TicTacToe game = new TicTacToe();
        game.start();

        // enable echo before exit
        SttyHelper.enableEcho();
    }
}
