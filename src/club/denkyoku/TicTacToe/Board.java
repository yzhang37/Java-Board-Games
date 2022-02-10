package club.denkyoku.TicTacToe;

public abstract class Board {
    // 棋盘的大小。一旦设置就不能再改变。
    private int height;
    private int width;
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

    // 检查是否可以放棋子。
    abstract public boolean canPut(int x, int y);

    public boolean isFull() {
        return this.usedSlots == this.height * this.width;
    }

    // 检查游戏棋盘的状态，判断是否有一方赢。
    abstract public int check_win();
}
