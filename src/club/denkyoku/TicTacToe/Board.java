package club.denkyoku.TicTacToe;

public class Board {
    private int size;
    private int[][] board;
    private int turn;

    public Board(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.turn = 1;
    }

    public int getSize() {
        return this.size;
    }

    public int getTurn() {
        return this.turn;
    }

}
