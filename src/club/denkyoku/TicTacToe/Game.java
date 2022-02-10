package club.denkyoku.TicTacToe;

import java.util.StringJoiner;

public class Game {
    protected Board board;
    protected Player[] players;
    protected int turn;

    // 创建一局游戏。需要提供 Player 的对象列表，以及棋盘对象。
    public Game(Board board, Player[] players) {
        this.board = board;
        this.players = players;
        this.turn = 0;
    }

    public void nextTurn() {
        this.turn ++;
        if (this.turn >= this.players.length) {
            this.turn = 0;
        }
    }

    public Player getPlayerAt(int index) {
        return this.players[index];
    }

    public String[] renderBoard() {
        String[] lines = new String[2 * this.board.getHeight() + 1];
        StringJoiner joiner1, joiner2;
        for (int i = 0; i < this.board.getHeight(); ++i) {
            if (i == 0)
                joiner1 = new StringJoiner("┬", "┌", "┐");
            else
                joiner1 = new StringJoiner("┼", "├", "┤");

            joiner2 = new StringJoiner("│", "│", "│");

            for (int j = 0; j < this.board.getWidth(); ++j) {
                joiner1.add("─");
                joiner2.add(String.valueOf(this.getPlayerAt(this.board.at(i, j)).getSymbol()));
            }
            lines[2 * i] = joiner1.toString();
            lines[2 * i + 1] = joiner2.toString();
        }
        joiner1 = new StringJoiner("┴", "└", "┘");
        for (int j = 0; j < this.board.getWidth(); ++j) {
            joiner1.add("─");
        }
        lines[2 * this.board.getHeight()] = joiner1.toString();
        return lines;
    }

    
}
