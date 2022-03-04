package club.denkyoku.TicTacToe.Models.Board;

public class Board<T extends Slot> {
    private T[][] board;
    private final int height;
    private final int width;
    private int usedSlots;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.usedSlots = 0;
        this.board = (T[][]) new Slot[height][width];
    }

    /**
     * Return the height of the board
     * @return the height of the board
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Return the width of the board
     * @return the width of the board
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Return the slot data, given the row and column
     * @param x the row
     * @param y the column
     * @return the slot data
     */
    public T at(int x, int y) {
        return this.board[x][y];
    }

    /**
     * Return the slot data, given the row and column
     * <br/>
     * The row and column indices are starting from 1.
     * @param x the row (starting from 1)
     * @param y the column (starting from 1)
     * @return the slot data
     */
    public T atByOne(int x, int y) {
        return this.at(x - 1, y - 1);
    }

    /**
     * Make a move at the given row and column.
     * @param x the row
     * @param y the column
     * @param value the value to be placed. If the slot is
     *              to be cleared, pass null.
     */
    public void put(int x, int y, T value) {
        if (0 <= x && x < this.height && 0 <= y && y < this.width) {
            if (this.board[x][y] == null && value != null) {
                this.usedSlots++;
            } else if (this.board[x][y] != null && value == null) {
                this.usedSlots--;
            }
            this.board[x][y] = value;
        }
    }

    /**
     * Return if the board is full
     * @return <code>true</code> if the board is full
     */
    public boolean isFull() {
        return this.usedSlots == this.height * this.width;
    }

    /**
     * Return if the board is empty
     * @return <code>true</code> if the board is empty
     */
    public boolean isEmpty() {
        return this.usedSlots == 0;
    }

    /**
     * Clear the entire board.
     */
    public void clear() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.board[i][j] = null;
            }
        }
        this.usedSlots = 0;
    }

}
