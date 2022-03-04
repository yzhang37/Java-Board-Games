package club.denkyoku.TicTacToe.Models.Player;

import club.denkyoku.TicTacToe.Models.Board.Board;
import club.denkyoku.TicTacToe.Models.Board.Slot;


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

    public static class Move {
        public int x;
        public int y;
        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Move1 extends Move {
        public Move1(int x, int y) {
            super(x - 1, y - 1);
        }
    }
}
