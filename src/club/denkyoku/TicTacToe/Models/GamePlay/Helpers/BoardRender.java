package club.denkyoku.TicTacToe.Models.GamePlay.Helpers;

import club.denkyoku.TicTacToe.Models.Board.Board;
import club.denkyoku.TicTacToe.Models.Board.Slot;

import java.util.StringJoiner;

public class BoardRender {
    /**
     * Helper function for printing a common rectangular board
     * @param board The <code>Board&lt;T extends Slot&gt;</code> object.
     * @param showCursor Whether to show the cursor. If <code>false</code>,
     *                   then <code>x</code> and <code>y</code> will be ignored.
     * @param x The x-coordinate of the cursor.
     * @param y The y-coordinate of the cursor.
     * @param <T> The type of the slot.
     * @return A string representation of the board.
     */
    public static <T extends Slot> String[] drawRectBoard(
            Board<T> board, boolean showCursor, int x, int y) {
        String[] lines = new String[2 * board.getHeight() + 1];

        StringJoiner joiner1, joiner2;

        for (int i = 0; i < board.getHeight(); ++i) {
            if (i == 0)
                joiner1 = new StringJoiner("┬", "┌", "┐");
            else
                joiner1 = new StringJoiner("┼", "├", "┤");

            joiner2 = new StringJoiner("│", "│", "│");

            for (int j = 0; j < board.getWidth(); ++j) {
                joiner1.add("───");
                char symbolForJoiner2 = ' ';
                if (showCursor && x == i && y == j) {
                    symbolForJoiner2 = '█';
                } else {
                    Slot slot = board.at(i, j);
                    if (slot != null) {
                        symbolForJoiner2 = slot.getPlayer().getSymbol();
                    }
                }
                joiner2.add(" " + String.valueOf(symbolForJoiner2) + " ");
            }

            lines[2 * i] = joiner1.toString();
            lines[2 * i + 1] = joiner2.toString();
        }

        joiner1 = new StringJoiner("┴", "└", "┘");
        for (int j = 0; j < board.getWidth(); ++j) {
            joiner1.add("───");
        }
        lines[2 * board.getHeight()] = joiner1.toString();
        return lines;
    }
}
