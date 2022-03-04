package club.denkyoku.TicTacToe.Models.GamePlay;

import club.denkyoku.TicTacToe.Models.Board.Board;
import club.denkyoku.TicTacToe.Models.Board.Slot;
import club.denkyoku.TicTacToe.Models.GamePlay.Helpers.BoardRender;
import club.denkyoku.TicTacToe.Models.GamePlay.Helpers.TurnBased;
import club.denkyoku.TicTacToe.Models.Player.Player;
import club.denkyoku.TicTacToe.Services.Input.DataSync;
import club.denkyoku.TicTacToe.Services.Input.KeyHandler;
import club.denkyoku.TicTacToe.Services.Output.Controls.MessageDialog;


public class TicTacToeGamePlay extends GamePlay {
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

    protected int boardSize;
    protected Board<Slot> board;
    protected Player[] players;
    protected int turn;
    protected int cursor_x;
    protected int cursor_y;

    /**
     * Create a new TicTacToe game
     * @param boardSize The size of the TicTacToe board.
     * @param players A list of <code>Player</code> objects.
     */
    public TicTacToeGamePlay(int boardSize, Player[] players) {
        this.boardSize = boardSize;
        this.players = players;
        this.board = new Board<>(boardSize, boardSize);
        this.turn = 0;
        this.cursor_x = 0;
        this.cursor_y = 0;

        this.doPlayerStatistics(players);
    }

    @Override
    public void start() {
        while (true) {
            this.reset();
            this.printUI(false);

            boolean gameOver = false;
            int exitCode;
            Player winner;
            do {
                // do one turn
                exitCode = this.oneTurn();
                // check if the game is over
                winner = this.checkWinner();

                if (winner != null) {
                    gameOver = true;
                } else {
                    // check if the game is draw
                    if (this.board.isFull()) {
                        gameOver = true;
                    }
                    this.nextTurn();
                }

            } while (!gameOver);

            // force exit
            if (exitCode == -2)
                return;

            // Reprint the board once before ending.
            this.printUI(false);

            String[] messages;

            if (winner == null) {
                messages = new String[]{
                        "You draw the game",
                        "Please restart the game.",
                };
            } else {
                if (this.hasAI && !winner.isHumanPlayer()) {
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
                            String.format("%s win!", winner.getName()),
                            "Congratulations! You beat the Computer!",
                            "Now do you want to play again?",
                    };
                } else {
                    messages = new String[] {
                            String.format("%s win!", winner.getName()),
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

    public int oneTurn() {
        // first print the game without cursor.
        this.printUI(false);

        Player curTurnPlayer = this.players[this.turn];

        TurnBasedDataSync dataSync = new TurnBasedDataSync();
        KeyHandler keyHandler = new TurnBasedKeyHandler(dataSync);
        if (curTurnPlayer.isHumanPlayer()) {
            boolean redraw = false;
            boolean firstTouch = true;

            while (dataSync.keepRun) {
                if (redraw) {
                    this.printUI(true);
                    redraw = false;
                }

                dataSync.reset();
                keyHandler.run();

                if (firstTouch) {
                    redraw = true;
                    firstTouch = false;
                }

                if (dataSync.doExit) {
                    if (MessageDialog.show(pauseGameMessages, pauseGameButtons, 0, 0) == 1) {
                        return -2;
                    }
                } else if (dataSync.doMoveUp) {
                    this.cursor_x--;
                    if (this.cursor_x < 0) {
                        this.cursor_x = this.board.getWidth() - 1;
                    }
                    redraw = true;
                } else if (dataSync.doMoveDown) {
                    this.cursor_x++;
                    if (this.cursor_x >= this.board.getWidth()) {
                        this.cursor_x = 0;
                    }
                    redraw = true;
                } else if (dataSync.doMoveLeft) {
                    this.cursor_y--;
                    if (this.cursor_y < 0) {
                        this.cursor_y = this.board.getHeight() - 1;
                    }
                    redraw = true;
                } else if (dataSync.doMoveRight) {
                    this.cursor_y++;
                    if (this.cursor_y >= this.board.getHeight()) {
                        this.cursor_y = 0;
                    }
                    redraw = true;
                } else if (dataSync.doEnter) {
                    if (this.checkCanPut(this.cursor_x, this.cursor_y)) {
                        this.board.put(this.cursor_x, this.cursor_y, new Slot(curTurnPlayer));
                        break;
                    }
                }
            }
        } else {
            // AI player
            Player.Move move = curTurnPlayer.getMove(this.board, this.players);
            this.board.put(move.x, move.y, new Slot(curTurnPlayer));
        }
        return 0;
    }

    private static class TurnBasedDataSync extends DataSync {
        public boolean doExit;
        public boolean doMoveUp;
        public boolean doMoveDown;
        public boolean doMoveLeft;
        public boolean doMoveRight;
        public boolean doEnter;
        public boolean keepRun;

        @Override
        public void reset() {
            this.keepRun = true;
            this.doExit = false;
            this.doMoveUp = false;
            this.doMoveDown = false;
            this.doMoveLeft = false;
            this.doMoveRight = false;
            this.doEnter = false;
        }
    }

    private static class TurnBasedKeyHandler extends KeyHandler {
        protected TurnBasedDataSync dataSync;

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
    }

    protected void nextTurn() {
        this.turn ++;
        if (this.turn >= this.players.length) {
            this.turn = 0;
        }
    }

    protected boolean checkCanPut(int x, int y) {
        return this.board.at(x, y) == null;
    }

    protected Player checkWinner() {
        boolean same;
        Slot first;

        // line check
        for (int i = 0; i < this.boardSize; i++) {
            first = this.board.at(i, 0);

            // if the first slot is null, skip this line
            if (first == null)
                continue;

            same = true;
            for (int j = 1; j < this.boardSize; j++) {
                Slot cur = this.board.at(i, j);
                if (cur == null || cur.getPlayer() != first.getPlayer()) {
                    same = false;
                    break;
                }
            }

            if (same) {
                return first.getPlayer();
            }
        }

        // column check
        for (int j = 0; j < this.boardSize; j++) {
            first = this.board.at(0, j);

            // if the first slot is null, skip this column
            if (first == null)
                continue;

            same = true;
            for (int i = 1; i < this.boardSize; i++) {
                Slot cur = this.board.at(i, j);
                if (cur == null || cur.getPlayer() != first.getPlayer()) {
                    same = false;
                    break;
                }
            }

            if (same) {
                return first.getPlayer();
            }
        }

        // NW to SE check
        same = true;
        first = this.board.at(0, 0);
        if (first != null) {
            for (int i = 1; i < this.boardSize; i++) {
                Slot cur = this.board.at(i, i);
                if (cur == null || cur.getPlayer() != first.getPlayer()) {
                    same = false;
                    break;
                }
            }
            if (same) {
                return first.getPlayer();
            }
        }


        // NE to SW check
        same = true;
        first = this.board.at(0, this.boardSize - 1);
        if (first != null) {
            for (int i = 1; i < this.boardSize; i++) {
                Slot cur = this.board.at(i, this.boardSize - i - 1);
                if (cur == null || cur.getPlayer() != first.getPlayer()) {
                    same = false;
                    break;
                }
            }
            if (same) {
                return first.getPlayer();
            }
        }

        return null;
    }

    /**
     * Function used to print the game UI.
     * @param showCursor Whether to show the cursor.
     */
    protected void printUI(boolean showCursor) {
        String[] boardString = BoardRender.drawRectBoard(this.board,
                showCursor, this.cursor_x, this.cursor_y);
        TurnBased.drawUI(boardString, this.players, this.turn);
    }

    public void reset() {
        this.cursor_x = this.cursor_y = 0;
        this.board.clear();
    }
}
