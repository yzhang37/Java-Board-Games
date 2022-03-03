package club.denkyoku.TicTacToe.Models;

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

    // Given a board, let the player decide which position to take.
    // If isHuman = true, Game can ignore calling this function.
    public abstract Move getMove(Board board, int myId, int[] otherIds);

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
