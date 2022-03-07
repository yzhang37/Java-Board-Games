package club.denkyoku.tictactoe.models.player;

import club.denkyoku.tictactoe.models.board.Board;
import club.denkyoku.tictactoe.models.board.Slot;


public abstract class Player {
    protected String name;
    protected char symbol;
    protected boolean isHuman;

    public Player() {
        this.symbol = '○';
        this.name = "Player";
        this.isHuman = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isHumanPlayer() {
        return this.isHuman;
    }

    /**
     * Function, given a board, let the player decide which position to take.
     * If <code>isHuman = true</code>, Game can ignore calling this function.
     * @param board The <code>Board</code> object the player will check.
     * @param playerLists The <code>Player</code> objects in the game.
     *                    The player can use it to check score of other players.
     * @param <T> The type of <code>Slot</code> object.
     * @return The place the player wants to take.
     */
    public abstract <T extends Slot> Move getMove(Board<T> board, Player[] playerLists);

}
