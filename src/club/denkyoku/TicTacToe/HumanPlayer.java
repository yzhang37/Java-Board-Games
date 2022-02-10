package club.denkyoku.TicTacToe;

// 人类玩家，按键盘进行游戏。
public class HumanPlayer extends Player {
    public HumanPlayer() {
        super();
        this.isHuman = true;
    }

    public HumanPlayer(String name, char symbol) {
        super();
        this.isHuman = true;
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public Move getMove(Board board, int myId, int[] otherIds) {
        return new Move(0, 0);
    }
}
