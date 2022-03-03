package club.denkyoku.TicTacToe.Models;

import club.denkyoku.TicTacToe.Services.Debug.ExamineKeyHandler;
import club.denkyoku.TicTacToe.Services.Input.KeyHandler;
import club.denkyoku.TicTacToe.Services.Output.Terminal.ConsoleHelper;
import club.denkyoku.TicTacToe.Services.Output.Controls.MessageDialog;

import java.util.StringJoiner;


public class Game {
    protected static final String[] pauseGameMessages = new String[]{
            "Game is paused.",
            "What do you want to do?",
    };
    protected static final MessageDialog.Button[] pauseGameButtons = new MessageDialog.Button[]{
            new MessageDialog.Button("Resume", 'R'),
            new MessageDialog.Button("Quit", 'Q'),
    };
    protected static final MessageDialog.Button[] restartGameButtons = new MessageDialog.Button[]{
            new MessageDialog.Button("Have another try", 'T'),
            new MessageDialog.Button("Back to menu", 'B'),
    };

    protected Board board;
    protected Player[] players;
    protected int turn;
    // Controls the cursor position used by human players.
    protected int cursor_x;
    protected int cursor_y;

    protected boolean onlyOneHuman;
    protected boolean hasAI;

    // Create a game. Requires a list of Player objects, and a chessboard object.
    public Game(Board board, Player[] players) {
        this.board = board;
        this.players = players;
        this.turn = 0;
        this.cursor_x = 0;
        this.cursor_y = 0;

        // These fields are only used for
        // printing final messages when the game is over.
        int humanCount = 0;
        for (Player player : players) {
            if (player.isHumanPlayer()) {
                humanCount ++;
            } else {
                this.hasAI = true;
            }
        }
        if (humanCount == 1) {
            this.onlyOneHuman = true;
        }
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

    public String[] renderBoard(boolean showCursor) {
        String[] lines = new String[2 * this.board.getHeight() + 1];
        StringJoiner joiner1, joiner2;
        for (int i = 0; i < this.board.getHeight(); ++i) {
            if (i == 0)
                joiner1 = new StringJoiner("┬", "┌", "┐");
            else
                joiner1 = new StringJoiner("┼", "├", "┤");

            joiner2 = new StringJoiner("│", "│", "│");

            for (int j = 0; j < this.board.getWidth(); ++j) {
                joiner1.add("───");
                char symbolForJoiner2 = ' ';
                if (showCursor && this.cursor_x == i && this.cursor_y == j) {
                    symbolForJoiner2 = '█';
                } else {
                    int cellValue = this.board.at(i, j);
                    if (cellValue > 0) {
                        symbolForJoiner2 = this.getPlayerAt(cellValue - 1).getSymbol();
                    }
                }
                joiner2.add(" " + String.valueOf(symbolForJoiner2) + " ");
            }
            lines[2 * i] = joiner1.toString();
            lines[2 * i + 1] = joiner2.toString();
        }
        joiner1 = new StringJoiner("┴", "└", "┘");
        for (int j = 0; j < this.board.getWidth(); ++j) {
            joiner1.add("───");
        }
        lines[2 * this.board.getHeight()] = joiner1.toString();
        return lines;
    }

    public void printUI(boolean showCursor) {
        String[] boardString = this.renderBoard(showCursor);

        // Because the chessboard here must be of equal width, the first one is the largest size.
        int maxBoardWidth = boardString[0].length();

        // Because the chessboard here must be of equal width, the first one is the largest size.
        // The current user must be printed out.
        int maxPrintPlayers = ConsoleHelper.GetConsoleHeight() / 3;
        int firstPrintPlayer = 0;
        if (turn >= maxPrintPlayers) {
            firstPrintPlayer = turn - maxPrintPlayers + 1;
        }
        int maxPrintPlayersLines = Math.min(maxPrintPlayers * 3, this.players.length * 3);

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

            if (curPrintPlayer < Math.min(firstPrintPlayer + maxPrintPlayers, this.players.length)) {
                // Line 0 of every 3 lines is blank.
                if (lineId % 3 == 1) {
                    sb.append("  ");
                    if (curPrintPlayer == this.turn) {
                        sb.append("▶");
                    } else {
                        sb.append(" ");
                    }
                    sb.append(this.getPlayerAt(curPrintPlayer).getSymbol());
                    sb.append(" ");
                    sb.append(this.getPlayerAt(curPrintPlayer).getName());
                } else if (lineId % 3 == 2) {
                    if (curPrintPlayer == this.turn) {
                        // If it is the current player, output the prompt information of the current player.
                        sb.append("     ");

                        if (this.getPlayerAt(curPrintPlayer).isHumanPlayer()) {
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

    public void start() {
        while (true) {
            this.reset();
            this.printUI(false);

            int turnResult = 0;
            do {
                turnResult = this.oneTurn();
            } while (turnResult == 0);

            // force exit
            if (turnResult == -2)
                return;

            // Reprint the board once before ending.
            this.printUI(false);

            String[] messages;

            if (turnResult == -1) {
                messages = new String[]{
                        "You draw the game",
                        "Please restart the game.",
                };
            } else {
                if (this.hasAI && !this.getPlayerAt(turnResult-1).isHumanPlayer()) {
                    messages = new String[] {
                            "You lose!",
                            "[Computer] beat you.",
                            "Would you like to have another try?",
                    };
                } else if (this.onlyOneHuman && this.hasAI) {
                    messages = new String[] {
                            "You win!",
                            "Congratulations! You beat the Computer!",
                            "Now do you want to play again?",
                    };
                } else if (!this.onlyOneHuman && this.hasAI) {
                    messages = new String[] {
                            String.format("%s win!", this.getPlayerAt(turnResult - 1).getName()),
                            "Congratulations! You beat the Computer!",
                            "Now do you want to play again?",
                    };
                } else {
                    messages = new String[] {
                            String.format("%s win!", this.getPlayerAt(turnResult - 1).getName()),
                            "Congratulations! You beat your friends.",
                            "Now do you want to play again?",
                    };
                }
            }
            int msgRet = MessageDialog.show(messages, restartGameButtons, 0, 1);
            if (msgRet == 1) {
                break;
            }
        }
    }

    /**
     * perform a Turn
     * @return Return value：0: means go to next turn, -1: means draw, -2: means exit
     *                       >= 1: the win player's id + 1
     */
    public int oneTurn() {
        this.printUI(false);
        Player curTurnPlayer = this.getPlayerAt(this.turn);

        KeyHandler keyHandler = new ExamineKeyHandler();
        if (curTurnPlayer.isHumanPlayer()) {
            boolean redraw = false;
            boolean firstTouch = true;
            while (true) {
                if (redraw) {
                    this.printUI(true);
                    redraw = false;
                }

                keyHandler.run();

                if (firstTouch) {
                    redraw = true;
                    firstTouch = false;
                }

//                if (KeyHandler.isEsc(buffer)) {
//                    if (MessageDialog.show(pauseGameMessages, pauseGameButtons, 0, 0) == 1) {
//                        return -2;
//                    }
//                } else if (KeyHandler.isKeyUp(buffer)) {
//                    this.cursor_x--;
//                    if (this.cursor_x < 0) {
//                        this.cursor_x = this.board.getWidth() - 1;
//                    }
//                    redraw = true;
//                } else if (KeyHandler.isKeyDown(buffer)) {
//                    this.cursor_x++;
//                    if (this.cursor_x >= this.board.getWidth()) {
//                        this.cursor_x = 0;
//                    }
//                    redraw = true;
//                } else if (KeyHandler.isKeyLeft(buffer)) {
//                    this.cursor_y--;
//                    if (this.cursor_y < 0) {
//                        this.cursor_y = this.board.getHeight() - 1;
//                    }
//                    redraw = true;
//                } else if (KeyHandler.isKeyRight(buffer)) {
//                    this.cursor_y++;
//                    if (this.cursor_y >= this.board.getHeight()) {
//                        this.cursor_y = 0;
//                    }
//                    redraw = true;
//                } else if (KeyHandler.isEnter(buffer)) {
//                    if (this.board.canPut(this.cursor_x, this.cursor_y)) {
//                        this.board.put(this.cursor_x, this.cursor_y, this.turn + 1);
//                        break;
//                    }
//                }
            }

        } else {
            // AI player
            int[] otherIds = new int[this.players.length - 1];
            for (int all_i = 0, o_i = 0; all_i < this.players.length; all_i++) {
                if (all_i != this.turn) {
                    otherIds[o_i++] = all_i + 1;
                }
            }
            Player.Move move = curTurnPlayer.getMove(this.board, this.turn + 1, otherIds);
            this.board.put(move.x, move.y, this.turn + 1);
        }

        int winner = this.board.check_win();
        if (winner > 0) {
            return winner;
        } else {
            // check if draw
            if (this.board.isFull()) {
                return -1;
            }
            this.nextTurn();
            return 0;
        }
    }

    void reset() {
        this.cursor_x = this.cursor_y = 0;
        this.board.clear();
    }
}
