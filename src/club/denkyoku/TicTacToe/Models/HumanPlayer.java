package club.denkyoku.TicTacToe.Models;


import club.denkyoku.TicTacToe.Models.Board.Board;
import club.denkyoku.TicTacToe.Models.Board.Slot;

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
    public <T extends Slot> Move getMove(
            Board<T> board, Player[] playerLists) {
        return new Move(0, 0);
    }
}
