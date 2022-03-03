package club.denkyoku.TicTacToe.Models;

public class NInRowBoard extends Board{
    private final int board_size;

    public NInRowBoard(int size) {
        super(size, size);
        this.board_size = size;
    }

    @Override
    public boolean canPut(int x, int y) {
        return 0 <= x && x < this.getHeight() &&
                0 <= y && y < this.getWidth() &&
                this.board[x][y] == 0;
    }

    @Override
    public int check_win() {
        boolean same;
        int first;

        // line check
        for (int i = 0; i < board_size; i++) {
            first = this.at(i, 0);
            same = true;

            for (int j = 1; j < board_size; j++) {
                if (this.at(i, j) != first) {
                    same = false;
                    break;
                }
            }

            if (same) {
                return first;
            }
        }

        // column check
        for (int j = 0; j < board_size; j++) {
            first = this.at(0, j);
            same = true;

            for (int i = 1; i < board_size; i++) {
                if (this.at(i, j) != first) {
                    same = false;
                    break;
                }
            }

            if (same) {
                return first;
            }
        }

        // NW to SE check
        same = true;
        first = this.at(0, 0);
        for (int i = 1; i < board_size; i++) {
            if (this.at(i, i) != first) {
                same = false;
                break;
            }
        }
        if (same) {
            return first;
        }

        // NE to SW check
        same = true;
        first = this.at(0, board_size - 1);
        for (int i = 1; i < board_size; i++) {
            if (this.at(i, board_size - i - 1) != first) {
                same = false;
                break;
            }
        }
        if (same) {
            return first;
        }

        return 0;
    }
}
