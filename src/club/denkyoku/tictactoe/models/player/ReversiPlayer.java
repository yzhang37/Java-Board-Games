package club.denkyoku.tictactoe.models.player;

import club.denkyoku.tictactoe.models.board.Board;
import club.denkyoku.tictactoe.models.board.Slot;


public class ReversiPlayer extends Player {
    /**
     * There is no very strict distinction between AIPlayer and HumanPlayer.
     * Because even HumanPlayer can call the hint function, which triggers
     * the hidden AIPlayer to give a chess hint.
     * @param isHuman whether the player is human or not
     * @param assistAILevel the level of the assist AIPlayer
     * @param name the name of the player
     * @param symbol the symbol of the player
     */
    public ReversiPlayer(
            boolean isHuman,
            int assistAILevel,
            String name,
            char symbol) {
        super();
        this.isHuman = isHuman;
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public <T extends Slot> Move getMove(Board<T> board, Player[] playerLists) {
        // TODO: under construction
        return null;
    }
}
