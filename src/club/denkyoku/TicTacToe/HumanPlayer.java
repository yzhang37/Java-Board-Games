package club.denkyoku.TicTacToe;

// 人类玩家，按键盘进行游戏。
public class HumanPlayer extends Player {
    public HumanPlayer() {
        super();
        this.isHuman = true;
    }

    @Override
    public int[] getMove(Board board) {
        return new int[]{0, 0};
    }
}
