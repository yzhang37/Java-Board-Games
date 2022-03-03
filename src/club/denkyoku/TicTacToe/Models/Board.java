package club.denkyoku.TicTacToe.Models;

public abstract class Board {
    // The size of the chessboard. Once set, it cannot be changed.
    private final int height;
    private final int width;
    private int usedSlots;
    protected int[][] board;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new int[height][width];
        this.usedSlots = 0;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int at(int x, int y) {
        return this.board[x][y];
    }

    public final int atByOne(int x, int y) {
        return this.board[x - 1][y - 1];
    }

    public void put(int x, int y, int value) {
        if (0 <= x && x < this.height && 0 <= y && y < this.width) {
            if (this.board[x][y] == 0 && value != 0) {
                this.usedSlots++;
            } else if (this.board[x][y] != 0 && value == 0) {
                this.usedSlots--;
            }
            this.board[x][y] = value;
        }
    }

    // Check if a move can be placed.
    abstract public boolean canPut(int x, int y);

    public boolean isFull() {
        return this.usedSlots == this.height * this.width;
    }

    public boolean isEmpty() {
        return this.usedSlots == 0;
    }

    // Check the state of the game board to see if one side wins.
    abstract public int check_win();

    public void clear() {
        this.board = new int[this.height][this.width];
        this.usedSlots = 0;
    }
}
