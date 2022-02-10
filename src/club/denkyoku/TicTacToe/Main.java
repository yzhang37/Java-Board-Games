package club.denkyoku.TicTacToe;

public class Main {

    public static void main(String[] args) {
        SttyHelper.disableEcho();
        SttyHelper.bufferByCharacter();

        MessageDialog.show(new String[]{"Do you want to play TicTacToe?"},
                MessageDialog.getYesNo(), 0, -1);

//        TicTacToe game = new TicTacToe();
//        game.start();

        // enable echo before exit
        SttyHelper.enableEcho();
    }
}
