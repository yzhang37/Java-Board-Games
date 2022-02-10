package club.denkyoku.TicTacToe;

public abstract class Board {
    // 棋盘的大小。一旦设置就不能再改变。
    private int height;
    private int width;
    protected int[][] board;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new int[height][width];
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

    // 检查游戏棋盘的状态，判断是否有一方赢。
    abstract public int check_win();
}
