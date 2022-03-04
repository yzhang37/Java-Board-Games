package club.denkyoku.TicTacToe.Models.GamePlay.Helpers;

import club.denkyoku.TicTacToe.Models.Player;
import club.denkyoku.TicTacToe.Services.Output.Terminal.ConsoleHelper;

/**
 * Helper class for turn-based game.
 */
public class TurnBased {

    /**
     * Helper function for printing UI in turn-based game.
     * @param boardString <code>String[] </code>The pre-rendered board string.
     * @param players <code>Player[] </code>The players.
     * @param turn <code>int </code>The current turn.
     */
    public static void drawUI(String[] boardString,
                              Player[] players,
                              int turn) {
        // Because the chessboard here must be of equal width, the first one is the largest size.
        int maxBoardWidth = boardString[0].length();

        // Because the chessboard here must be of equal width, the first one is the largest size.
        // The current user must be printed out.
        int maxPrintPlayers = ConsoleHelper.GetConsoleHeight() / 3;
        int firstPrintPlayer = 0;
        if (turn >= maxPrintPlayers) {
            firstPrintPlayer = turn - maxPrintPlayers + 1;
        }
        int maxPrintPlayersLines = Math.min(maxPrintPlayers * 3, players.length * 3);

        int finalMaxLines = Math.max(maxPrintPlayersLines, boardString.length);
        String[] gameScreen = new String[finalMaxLines];
        for (int lineId = 0, curPrintPlayer = firstPrintPlayer;
             lineId < finalMaxLines; ++lineId) {

            StringBuilder sb = new StringBuilder();

            if (lineId < boardString.length) {
                // If it is part of the chessboard, output the content of the chessboard
                sb.append(boardString[lineId]);
            } else {
                // Otherwise, a blank line of the same width as the chessboard is generated
                sb.append(" ".repeat(maxBoardWidth));
            }

            if (curPrintPlayer < Math.min(firstPrintPlayer + maxPrintPlayers, players.length)) {
                // Line 0 of every 3 lines is blank.
                if (lineId % 3 == 1) {
                    sb.append("  ");
                    if (curPrintPlayer == turn) {
                        sb.append("▶");
                    } else {
                        sb.append(" ");
                    }
                    sb.append(players[curPrintPlayer].getSymbol());
                    sb.append(" ");
                    sb.append(players[curPrintPlayer].getName());
                } else if (lineId % 3 == 2) {
                    if (curPrintPlayer == turn) {
                        // If it is the current player, output the prompt information of the current player.
                        sb.append("     ");

                        if (players[curPrintPlayer].isHumanPlayer()) {
                            sb.append("Your turn");
                        } else {
                            sb.append("AI thinking...");
                        }
                    }
                    curPrintPlayer ++;
                }
            }
            gameScreen[lineId] = sb.toString();
        }
        ConsoleHelper.printScreen(gameScreen);
    }
}
