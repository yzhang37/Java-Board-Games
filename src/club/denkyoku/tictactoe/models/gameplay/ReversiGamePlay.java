package club.denkyoku.tictactoe.models.gameplay;

import club.denkyoku.tictactoe.libraries.os.Time;
import club.denkyoku.tictactoe.models.board.Board;
import club.denkyoku.tictactoe.models.board.Slot;
import club.denkyoku.tictactoe.models.gameplay.helpers.BoardRender;
import club.denkyoku.tictactoe.models.gameplay.helpers.TurnBased;
import club.denkyoku.tictactoe.models.player.Move;
import club.denkyoku.tictactoe.models.player.Player;
import club.denkyoku.tictactoe.models.player.ReversiPlayer;
import club.denkyoku.tictactoe.services.input.KeyHandler;
import club.denkyoku.tictactoe.services.output.controls.MessageDialog;

import java.util.ArrayList;


// Classes for the whole Reversi Game Play Experience
public class ReversiGamePlay extends GamePlay {
    protected final String[] pauseGameMessages = new String[]{
            "Game is paused.",
            "What do you want to do?",
    };
    protected final String[] mustPassMessage = new String[]{
            "You cannot make a move.",
            "You must pass.",
    };
    protected final String[] aiMustPassMessage = new String[]{
            "To computer:",
            "You cannot make a move,",
            "You have to pass."
    };
    protected final String[] onlyFirstPassMessage = new String[]{
            "You may not pass.",
            "Move to a place where there's a dot.",
    };
    protected final String[] invalidMoveMessage = new String[]{
            "You may only move to a space where",
            "there is a dot show there.",
    };
    protected final String[] cheatsWarningMessage = new String[]{
            "You're about to enable cheats",
            "for this round. This won't be counted",
            "into the statistics.",
            "Do you want to continue?",
    };
    protected final String[] useHintMessage = new String[] {
            "When you use a hint, the cursor will",
            "be moved to a recommended place."
    };
    protected final MessageDialog.Button[] pauseGameButtons = new MessageDialog.Button[]{
            new MessageDialog.Button("Resume", 'R'),
            new MessageDialog.Button("Quit", 'Q'),
    };
    protected final MessageDialog.Button[] restartGameButtons = new MessageDialog.Button[]{
            new MessageDialog.Button("Have another try", 'T'),
            new MessageDialog.Button("Back to menu", 'B'),
    };
    protected final String[] headerMessage = new String[] {
            "",
            "",
    };
    protected final String[] footerMessage = new String[] {
            "F1: Pass  F2: Hint  Esc: Pause"
    };

    protected final int boardSize = 8;
    protected final ReversiPlayer[] players;
    protected final int[] playerMovesCount;
    protected final Board<Slot> board;
    protected int turn;
    protected int cursor_x;
    protected int cursor_y;
    // if show animation
    protected boolean showAnimation;
    // when use cheats (hint), this won't be counted into statistics
    protected boolean cheats;
    // pass can be used only once
    protected boolean freshBoard;

    public ReversiGamePlay(ReversiPlayer[] players, boolean showAnimation) {
        if (players == null || players.length != 2) {
            throw new IllegalArgumentException("Reversi requires exactly 2 players.");
        }
        this.players = players;
        this.playerMovesCount = new int[2];

        this.showAnimation = showAnimation;
        this.board = new Board<>(boardSize, boardSize);

        this.doPlayerStatistics(players);
    }

    public boolean getShowAnimation() {
        return this.showAnimation;
    }

    public void setShowAnimation(boolean value) {
        this.showAnimation = value;
    }

    @Override
    public void start() {
        while (true) {
            // first reset the game states.
            this.reset();
            // print the first UI
            this.printUI(false, null, null);

            int exitCode;
            int consecutiveNoMoves = 0;
            while (true) {
                // compute the available moves for the player
                Move[] availableMoves = getAvailableMoves(this.board,
                        this.players[this.turn], this.players[this.turn ^ 1]);
                if (availableMoves.length == 0) {
                    consecutiveNoMoves++;
                    // both two players cannot make a move, game ends
                    if (consecutiveNoMoves == 2) {
                        break;
                    }
                } else {
                    consecutiveNoMoves = 0;
                }

                // do one turn
                exitCode = this.oneTurn(availableMoves);

                // means user want to quit
                if (exitCode == -2) {
                    GamePlay.showGameStatistics(this.players);
                    return;
                }

                // when the board is full or
                // when someone has no moves, he immediately loses.
                if (this.playerMovesCount[0] == 0 || this.playerMovesCount[1] == 0 ||
                    this.board.isFull()) {
                    break;
                }

                this.nextTurn();
            }

            Player winner = this.checkWinner();
            if (!this.cheats) {
                GamePlay.doGameStatistics(this.players, winner);
            }

            // Reprint the board once before ending
            this.printUI(false, null, null);

            // print the game over message.
            String[] messages;
            int moveDiff = this.playerMovesCount[0] - this.playerMovesCount[1];
            moveDiff = Math.abs(moveDiff);

            if (winner == null) {
                messages = new String[]{
                        "You draw the game",
                        "Please restart the game.",
                };
            } else {
                if (this.hasAI && this.onlyOneHuman && !winner.isHumanPlayer()) {
                    messages = new String[] {
                            "You lose!",
                            String.format("[Computer] beats you by %d.", moveDiff),
                            "Would you like to have another try?",
                    };
                } else if (this.hasAI && !this.hasHuman) {
                    messages = new String[] {
                            "Game Ends!",
                            String.format("[Computer] wins by %d.", moveDiff),
                            "Would you like to have another try?",
                    };
                } else if (this.onlyOneHuman && this.hasAI) {
                    messages = new String[] {
                            "You win!",
                            String.format("Congratulations! You beat the Computer by %d!", moveDiff),
                            "Now do you want to play again?",
                    };
                } else if (!this.onlyOneHuman && this.hasAI) {
                    messages = new String[] {
                            String.format("%s win!", winner.getName()),
                            String.format("Congratulations! You beat the Computer by %d!", moveDiff),
                            "Now do you want to play again?",
                    };
                } else {
                    messages = new String[] {
                            String.format("%s win!", winner.getName()),
                            String.format("Congratulations! You beat your friend by %d.", moveDiff),
                            "Now do you want to play again?",
                    };
                }
            }
            int msgRet = MessageDialog.show(messages, restartGameButtons, 0, 1);
            if (msgRet == 1) {
                GamePlay.showGameStatistics(this.players);
                break;
            }
        }
    }

    /**
     * Compute the available moves for the current turn player.
     * @return the available moves for the current turn player.
     */
    public static <T extends Slot> Move[] getAvailableMoves(
            Board<T> board, Player self, Player opponent) {
        ArrayList<Move> movesList = new ArrayList<>();

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.at(i, j) == null) {
                    if (ReversiGamePlay.computeFlip(board,
                            i, j, self, opponent).length > 0) {
                        movesList.add(new Move(i, j));
                    }
                }
            }
        }
        Move[] moves = new Move[movesList.size()];
        movesList.toArray(moves);
        return moves;
    }

    /**
     * Helper function, to return number of pieces
     * flipped when a piece is played at coords
     *
     * @param board the board to play on
     * @param x x coordinate
     * @param y y coordinate
     * @param self the player who plays
     * @param opponent the opponent of the player who plays
     * @return A list of pieces flipped when a piece is played at coords
     */
    public static <T extends Slot> Move[] computeFlip(
            Board<T> board,
            int x, int y,
            Player self, Player opponent) {
        ArrayList<Move> movesList = new ArrayList<>();

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                // skip the current position
                if (dRow == 0 && dCol == 0)
                    continue;

                // look at the first square in the given direction
                int row = x + dRow;
                int col = y + dCol;

                // keep track of how many squares have the opponent's piece
                int counter = 0;

                while (row >= 0 && row < 8 &&
                       col >= 0 && col < 8) {
                    Slot slot = board.at(row, col);
                    if (slot == null || slot.getPlayer() != opponent) {
                        break;
                    }
                    // continue moving in this direction
                    row += dRow;
                    col += dCol;
                    // increment the count of number of stones flipped
                    counter++;
                }

                // the next slot must be of the current player,
                // and still on the board
                if (row >= 0 && row < 8 &&
                    col >= 0 && col < 8) {
                    Slot slot = board.at(row, col);
                    if (slot != null && slot.getPlayer() == self) {
                        for (int i = 1; i <= counter; i++) {
                            movesList.add(new Move(x + dRow * i, y + dCol * i));
                        }
                    }
                }
            }
        }
        Move[] moves = new Move[movesList.size()];
        movesList.toArray(moves);
        return moves;
    }

    protected int oneTurn(Move[] availableMoves) {
        // first print the game without cursor.
        this.printUI(false, availableMoves, null);

        Player curTurnPlayer = this.players[this.turn];

        TurnBased.TurnBasedDataSync dataSync = new TurnBased.TurnBasedDataSync();
        KeyHandler keyHandler = new TurnBased.TurnBasedKeyHandler(dataSync);

        Move the_move = null;
        if (curTurnPlayer.isHumanPlayer()) {
            if (availableMoves.length == 0) {
                MessageDialog.showOK(mustPassMessage);
            }

            boolean redraw = false;
            boolean firstTouch = true;

            while (dataSync.keepRun) {
                if (redraw) {
                    this.printUI(true, availableMoves, null);
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
                        keyHandler.exitInput();
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
                    // check if the move is in available moves
                    boolean canMove = false;
                    for (Move move : availableMoves) {
                        if (move.x == this.cursor_x && move.y == this.cursor_y) {
                            canMove = true;
                            break;
                        }
                    }
                    if (canMove) {
                        the_move = new Move(this.cursor_x, this.cursor_y);
                        break;
                    } else {
                        // if there's place to move, show invalid move message
                        if (availableMoves.length > 0)
                            MessageDialog.showOK(invalidMoveMessage);
                        else
                        // if there's no place to move, show must pass message
                            MessageDialog.showOK(mustPassMessage);
                    }
                } else if (dataSync.doFunction1) {
                    if (this.freshBoard || availableMoves.length == 0) {
                        return 0;
                    } else {
                        MessageDialog.showOK(onlyFirstPassMessage);
                    }
                } else if (dataSync.doFunction2) {
                    // make a hint
                    if (!this.cheats) {
                        int ret = MessageDialog.show(cheatsWarningMessage,
                                MessageDialog.getYesNo(), 1, 1);
                        if (ret == 1)
                            continue;
                        this.headerMessage[0] = "Reversi Practice Game";
                        this.cheats = true;
                        MessageDialog.showOK(useHintMessage);
                    }
                    Move hint = curTurnPlayer.getMove(this.board, this.players, availableMoves);
                    this.cursor_x = hint.x;
                    this.cursor_y = hint.y;
                    redraw = true;
                }
            }
            keyHandler.exitInput();
        } else {
            // AI player
            if (availableMoves.length == 0) {
                MessageDialog.show(aiMustPassMessage);
                return 0;
            }
            // if show animation, wait for 1 second
            if (!this.players[this.turn].isHumanPlayer() && this.showAnimation) {
                Time.waitMilliseconds(1000);
            }
            the_move = curTurnPlayer.getMove(this.board, this.players, availableMoves);
        }

        if (the_move == null) {
            throw new RuntimeException("Unexpected null move!");
        }

        // First play the flipping animation
        Move[] flippedMoves = ReversiGamePlay.computeFlip(
                this.board, the_move.x, the_move.y,
                this.players[this.turn], this.players[this.turn ^ 1]);
        this.playerMovesCount[this.turn]++;

        this.board.put(the_move.x, the_move.y, new Slot(curTurnPlayer));
        this.printUI(false, null, flippedMoves);
        this.playerMovesCount[this.turn] += flippedMoves.length;
        this.playerMovesCount[this.turn ^ 1] -= flippedMoves.length;

        // do the flipping
        for (Move move : flippedMoves) {
            this.board.put(move.x, move.y, new Slot(curTurnPlayer));
        }
//        this.headerMessage[1] = "A: " + this.playerMovesCount[0] + " B: " + this.playerMovesCount[1];
        return 0;
    }

    /**
     * A helper function for doing the turn
     * It's called by Players
     * @param board The board
     * @param x    The x coordinate
     * @param y    The y coordinate
     * @param self The player
     * @param opponent The opponent
     */
    public static void tryTurn(
            Board<Slot> board, int x, int y, Player self, Player opponent) {
        Move[] flippedMoves = ReversiGamePlay.computeFlip(
                board, x, y, self, opponent);
        board.put(x, y, new Slot(self));
        for (Move move : flippedMoves) {
            board.put(move.x, move.y, new Slot(self));
        }
    }

    /**
     * Find the game winner.
     * @return the winner, or null if there is no winner.
     */
    protected Player checkWinner() {
        if (this.playerMovesCount[0] == 0 && this.playerMovesCount[1] != 0) {
            return this.players[1];
        } else if (this.playerMovesCount[1] == 0 && this.playerMovesCount[0] != 0) {
            return this.players[0];
        } else {
            if (this.playerMovesCount[0] > this.playerMovesCount[1]) {
                return this.players[0];
            } else if (this.playerMovesCount[0] < this.playerMovesCount[1]) {
                return this.players[1];
            } else {
                return null;
            }
        }
    }

    /**
     * Print the UI of the Reversi Game.
     *
     * @param bShowCursor    Whether to show the cursor.
     *                       If <code>true</code>, the cursor will be shown at the
     *                       <code>cursor_x</code> and <code>cursor_y</code> position.
     * @param availableMoves Whether to show the available moves.
     *                       If <code>null</code>, then this will be skipped.
     *                       Else, it will show dots at the slots.
     * @param flippingMoves  Whether to show the flipping moves.
     *                       If <code>null</code>, then this will be skipped.
     *                       Else, it will play the animation of flipping the moves.
     */
    protected void printUI(boolean bShowCursor,
                           Move[] availableMoves,
                           Move[] flippingMoves) {
        int init_capacity = 0;
        if (availableMoves != null) {
            init_capacity += availableMoves.length;
        }
        if (flippingMoves != null) {
            init_capacity += flippingMoves.length;
        }

        // making the preset backgrounds
        ArrayList<BoardRender.SlotChar> dynamicPreset = new ArrayList<>(init_capacity);
        BoardRender.SlotChar[] presetArray;

        if (availableMoves != null) {
            for (var move : availableMoves) {
                dynamicPreset.add(new BoardRender.SlotChar(move, '·', false));
            }
        }

        if (flippingMoves == null) {
            presetArray = new BoardRender.SlotChar[dynamicPreset.size()];
            presetArray = dynamicPreset.toArray(presetArray);
            String[] boardString = BoardRender.drawRectBoard(this.board,
                    bShowCursor, this.cursor_x, this.cursor_y,
                    presetArray);

            TurnBased.drawUI(boardString, this.players, this.turn, this.headerMessage, this.footerMessage);
            return;
        }

        char curPlayerSymbol = this.players[this.turn].getSymbol();

        // if show animation, we will show flipping one by one.
        for (var move : flippingMoves) {
            dynamicPreset.add(new BoardRender.SlotChar(move, curPlayerSymbol,true));
            if (this.showAnimation) {
                presetArray = new BoardRender.SlotChar[dynamicPreset.size()];
                presetArray = dynamicPreset.toArray(presetArray);

                char tempPlayerSymbol = presetArray[dynamicPreset.size() - 1].getSymbol();
                if (tempPlayerSymbol == '●') {
                    tempPlayerSymbol = '⬬';
                } else if (tempPlayerSymbol == '○') {
                    tempPlayerSymbol = '⬭';
                }
                presetArray[dynamicPreset.size() - 1] = new BoardRender.SlotChar(move, tempPlayerSymbol, true);

                // draw 1st times
                String[] boardString = BoardRender.drawRectBoard(this.board,
                        bShowCursor, this.cursor_x, this.cursor_y,
                        presetArray);
                TurnBased.drawUI(boardString, this.players, this.turn, this.headerMessage, this.footerMessage);
                Time.waitMilliseconds(500);
            }
        }

        presetArray = new BoardRender.SlotChar[dynamicPreset.size()];
        presetArray = dynamicPreset.toArray(presetArray);

        String[] boardString = BoardRender.drawRectBoard(this.board,
                bShowCursor, this.cursor_x, this.cursor_y,
                presetArray);

        TurnBased.drawUI(boardString, this.players, this.turn, this.headerMessage, this.footerMessage);
    }

    /**
     * Function used to switch to next player.
     */
    protected void nextTurn() {
        this.freshBoard = false;
        this.turn ++;
        if (this.turn >= this.players.length) {
            this.turn = 0;
        }
    }

    public void reset() {
        this.cursor_x = this.cursor_y = 3;
        this.turn = 0;
        this.board.clear();
        this.cheats = false;
        this.freshBoard = true;
        this.headerMessage[0] = "Reversi";

        // default moves in the center of the board.
        this.board.put(3, 3, new Slot(this.players[0]));
        this.board.put(4, 4, new Slot(this.players[0]));
        this.board.put(3, 4, new Slot(this.players[1]));
        this.board.put(4, 3, new Slot(this.players[1]));
        this.playerMovesCount[0] = 2;
        this.playerMovesCount[1] = 2;
    }
}
