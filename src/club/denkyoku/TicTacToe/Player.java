package club.denkyoku.TicTacToe;

public abstract class Player {
    protected String name;
    protected char symbol;
    protected boolean isHuman;

    public Player() {
        this.symbol = '○';
        this.name = "Player";
        this.isHuman = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean isHumanPlayer() {
        return this.isHuman;
    }

    // 给定一个棋盘，让玩家判断走哪一个位置。
    // 如果 isHuman = true，则 Game 可以忽略调用这个函数。
    public abstract Move getMove(Board board);

    public static class Move {
        public int x;
        public int y;
        public Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
