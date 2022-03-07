package club.denkyoku.tictactoe.models.player;

// Represents a move in a board.
public class Move {
    public final int x;
    public final int y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("Move (%d, %d)", x, y);
    }
}
