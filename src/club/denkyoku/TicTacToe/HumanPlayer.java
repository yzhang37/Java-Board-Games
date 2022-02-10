package club.denkyoku.TicTacToe;

// Human player, press the keyboard to play the game.
public class HumanPlayer extends Player {
    public HumanPlayer() {
        super();
        this.isHuman = true;
    }

    public HumanPlayer(String name, char symbol) {
        super();
        this.isHuman = true;
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public Move getMove(Board board, int myId, int[] otherIds) {
        return new Move(0, 0);
    }
}
