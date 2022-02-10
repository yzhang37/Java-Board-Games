package club.denkyoku.TicTacToe;

public class Board {
    private int size;
    private int[][] board;

    public Board(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    public int getSize() {
        return this.size;
    }
}
