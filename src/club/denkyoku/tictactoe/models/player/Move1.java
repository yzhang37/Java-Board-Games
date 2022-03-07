package club.denkyoku.tictactoe.models.player;

// This move1 class is identically the same as the move class.
// However, it provides backward compatibility with the move
// whose indices start from 1.
public class Move1 extends Move {
    public Move1(int x, int y) {
        super(x - 1, y - 1);
    }
}
