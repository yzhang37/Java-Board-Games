package club.denkyoku.tictactoe.models.gameplay.helpers;

import club.denkyoku.tictactoe.models.player.Player;
import club.denkyoku.tictactoe.services.input.DataSync;
import club.denkyoku.tictactoe.services.input.KeyHandler;
import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;

/**
 * Helper class for turn-based game.
 */
public class TurnBased {

    /**
     * Helper function for printing UI in turn-based game.
     * @param boardString <code>String[] </code>The pre-rendered board string.
     * @param players     <code>Player[] </code>The players.
     * @param turn        <code>int </code>The current turn.
     * @param header      Display header. If <code>null</code> then skip this.
     * @param footer      Display footer. If <code>null</code> then skip this.
     */
    public static void drawUI(String[] boardString,
                              Player[] players,
                              int turn,
                              String[] header,
                              String[] footer) {
        int finalBoardStringLength = boardString.length;
        if (header != null) {
            finalBoardStringLength += header.length;
        }
        if (footer != null) {
            finalBoardStringLength += footer.length;
        }
        int maxBoardWidth = 0;
        String[] finalBoardString = new String[finalBoardStringLength];

        int j = 0;
        if (header != null)
            for (int i = 0; i < header.length; i++, j++) {
                finalBoardString[j] = header[i];
                if (header[i].length() > maxBoardWidth) {
                    maxBoardWidth = header[i].length();
                }
            }
        for (int i = 0; i < boardString.length; i++, j++) {
            finalBoardString[j] = boardString[i];
            if (boardString[i].length() > maxBoardWidth) {
                maxBoardWidth = boardString[i].length();
            }
        }
        if (footer != null)
            for (int i = 0; i < footer.length; i++, j++) {
                finalBoardString[j] = footer[i];
                if (footer[i].length() > maxBoardWidth) {
                    maxBoardWidth = footer[i].length();
                }
            }

        // Because the chessboard here must be of equal width, the first one is the largest size.
        // The current user must be printed out.
        int maxPrintPlayers = ConsoleHelper.GetConsoleHeight() / 3;
        int firstPrintPlayer = 0;
        if (turn >= maxPrintPlayers) {
            firstPrintPlayer = turn - maxPrintPlayers + 1;
        }
        int maxPrintPlayersLines = Math.min(maxPrintPlayers * 3, players.length * 3);

        int finalMaxLines = Math.max(maxPrintPlayersLines, finalBoardString.length);

        String[] gameScreen = new String[finalMaxLines];
        for (int lineId = 0, curPrintPlayer = firstPrintPlayer;
             lineId < finalMaxLines; ++lineId) {

            StringBuilder sb = new StringBuilder();

            if (lineId < finalBoardString.length) {
                // If it is part of the chessboard, output the content of the chessboard
                sb.append(finalBoardString[lineId]);
                if (finalBoardString[lineId].length() < maxBoardWidth) {
                    sb.append(" ".repeat(maxBoardWidth - finalBoardString[lineId].length()));
                }
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
                    curPrintPlayer++;
                }
            }
            gameScreen[lineId] = sb.toString();
        }
        ConsoleHelper.printScreen(gameScreen);
    }

    public static class TurnBasedDataSync extends DataSync {
        public boolean doExit;
        public boolean doMoveUp;
        public boolean doMoveDown;
        public boolean doMoveLeft;
        public boolean doMoveRight;
        public boolean doEnter;
        public boolean keepRun;
        public boolean doFunction1;
        public boolean doFunction2;
        public boolean doFunction3;
        public boolean doFunction4;
        public boolean doFunction5;
        public boolean doFunction6;
        public boolean doFunction7;
        public boolean doFunction8;
        public boolean doFunction9;
        public boolean doFunction10;
        public boolean doFunction11;
        public boolean doFunction12;

        @Override
        public void reset() {
            this.keepRun = true;
            this.doExit = false;
            this.doEnter = false;

            this.doMoveUp = this.doMoveDown = this.doMoveLeft = this.doMoveRight = false;
            this.doFunction1 = this.doFunction2 = this.doFunction3 = this.doFunction4 =
                               this.doFunction5 = this.doFunction6 = this.doFunction7 =
                               this.doFunction8 = this.doFunction9 = this.doFunction10 =
                               this.doFunction11 = this.doFunction12 = false;
        }
    }

    public static class TurnBasedKeyHandler extends KeyHandler {
        protected final TurnBasedDataSync dataSync;

        public TurnBasedKeyHandler(TurnBasedDataSync dataSync) {
            this.dataSync = dataSync;
        }

        @Override
        protected void onKeyEsc() {
            this.dataSync.doExit = true;
        }

        @Override
        protected void onKeyUp() {
            this.dataSync.doMoveUp = true;
        }

        @Override
        protected void onKeyDown() {
            this.dataSync.doMoveDown = true;
        }

        @Override
        protected void onKeyLeft() {
            this.dataSync.doMoveLeft = true;
        }

        @Override
        protected void onKeyRight() {
            this.dataSync.doMoveRight = true;
        }

        @Override
        protected void onKeyEnter() {
            this.dataSync.doEnter = true;
        }

        @Override
        protected void onKeyF1() {
            this.dataSync.doFunction1 = true;
        }

        @Override
        protected void onKeyF2() {
            this.dataSync.doFunction2 = true;
        }

        @Override
        protected void onKeyF3() {
            this.dataSync.doFunction3 = true;
        }

        @Override
        protected void onKeyF4() {
            this.dataSync.doFunction4 = true;
        }

        @Override
        protected void onKeyF5() {
            this.dataSync.doFunction5 = true;
        }

        @Override
        protected void onKeyF6() {
            this.dataSync.doFunction6 = true;
        }

        @Override
        protected void onKeyF7() {
            this.dataSync.doFunction7 = true;
        }

        @Override
        protected void onKeyF8() {
            this.dataSync.doFunction8 = true;
        }

        @Override
        protected void onKeyF9() {
            this.dataSync.doFunction9 = true;
        }

        @Override
        protected void onKeyF10() {
            this.dataSync.doFunction10 = true;
        }

        @Override
        protected void onKeyF11() {
            this.dataSync.doFunction11 = true;
        }

        @Override
        protected void onKeyF12() {
            this.dataSync.doFunction12 = true;
        }
    }
}
