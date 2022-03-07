package club.denkyoku.tictactoe.models.player;

import club.denkyoku.tictactoe.libraries.math.StdRandom;
import club.denkyoku.tictactoe.models.board.Board;
import club.denkyoku.tictactoe.models.board.Slot;
import club.denkyoku.tictactoe.models.gameplay.ReversiGamePlay;

import java.util.ArrayList;


public class ReversiPlayer extends Player {
    protected double smart_prob;

    /**
     * There is no very strict distinction between AIPlayer and HumanPlayer.
     * Because even HumanPlayer can call the hint function, which triggers
     * the hidden AIPlayer to give a chess hint.
     * @param isHuman whether the player is human or not
     * @param smart_prob the probability of the assist AIPlayer won't make mistakes
     * @param name the name of the player
     * @param symbol the symbol of the player
     */
    public ReversiPlayer(
            boolean isHuman,
            double smart_prob,
            String name,
            char symbol) {
        super();
        this.isHuman = isHuman;
        this.name = name;
        this.symbol = symbol;
        this.smart_prob = smart_prob;
    }

    public double getSmart_prob() {
        return smart_prob;
    }

    public void setSmart_prob(double smart_prob) {
        this.smart_prob = smart_prob;
    }

    @Override
    public <T extends Slot> Move getMove(Board<T> board, Player[] playerLists, Move[] availableMoves) {
        ReversiPlayer opponent = null;
        for (Player player : playerLists) {
            if (player != this && player instanceof ReversiPlayer) {
                opponent = (ReversiPlayer) player;
            }
        }
        if (opponent == null) {
            throw new IllegalArgumentException("No opponent found.");
        }

        if (StdRandom.bernoulli(smart_prob)) {
            var myMove = minimax(4, (Board<Slot>) board, availableMoves, this, opponent);
            return myMove.getMove();
        } else {
            return availableMoves[StdRandom.uniform(availableMoves.length)];
        }
    }

    protected static record ScoredMove(int moveValue, Move move) {
        public int getMoveValue() {
            return this.moveValue;
        }

        public Move getMove() {
            return this.move;
        }
    }

    protected static int[][] coordValues = new int[][]{
            {99, -8, 8, 6, 6, 8, -8,99},
            {-8,-24,-4,-3,-3,-4,-24,-8},
            { 8, -4, 7, 4, 4, 7, -4, 8},
            { 6, -3, 4, 0, 0, 4, -3, 6},
            { 6, -3, 4, 0, 0, 4, -3, 6},
            { 8, -4, 7, 4, 4, 7, -4, 8},
            {-8,-24,-4,-3,-3,-4,-24,-8},
            {99, -8, 8, 6, 6, 8, -8,99}
    };

    /**
     * A precomputed value for each slot.
     * @param move the move to be evaluated
     * @return the value of the slot
     */
    protected static int evaluate_coordinate(Move move) {
        return coordValues[move.x][move.y];
    }

    /**
     * performs minimax algorithm with depth
     * @param depth the depth of the minimax algorithm
     * @param lastBoard the last board
     * @param lastAvailableMoves the last available moves
     * @param self the self player
     * @param opponent the opponent player
     * @return the value of the move and best move
     */
    protected static ScoredMove minimax(int depth,
            Board<Slot> lastBoard,
            Move[] lastAvailableMoves,
            ReversiPlayer self,
            ReversiPlayer opponent) {

        // initialize best move tracking variable
        int bestMoveValue = -999;
        ArrayList<Move> bestMoves = new ArrayList<>();

        for (Move move : lastAvailableMoves) {
            int moveValue = evaluate_coordinate(move);
            if (depth > 1) {
                Board<Slot> newBoard = lastBoard.clone();
                ReversiGamePlay.tryTurn(newBoard, move.x, move.y, self, opponent);
                // opponent takes their most valuable move
                Move [] newAvailableMoves = ReversiGamePlay.getAvailableMoves(newBoard, opponent, self);
                if (newAvailableMoves.length > 0) {
                    var oppValue = minimax(depth - 1, newBoard, newAvailableMoves, opponent, self);
                    moveValue -= oppValue.getMoveValue();
                }
            }

            if (moveValue > bestMoveValue) {
                bestMoves.clear();
            }
            if (moveValue >= bestMoveValue) {
                bestMoves.add(move);
            }
        }
        // pick a "best" move at random
        return new ScoredMove(bestMoveValue, bestMoves.get(StdRandom.uniform(bestMoves.size())));
    }
}
