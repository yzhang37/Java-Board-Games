package club.denkyoku.TicTacToe.Models.Player;

import club.denkyoku.TicTacToe.Libraries.Math.StdRandom;
import club.denkyoku.TicTacToe.Models.Board.Board;
import club.denkyoku.TicTacToe.Models.Board.Slot;
import club.denkyoku.TicTacToe.Models.Player.Player;

import java.util.concurrent.ThreadLocalRandom;


public class TicTacToeAIPlayer extends Player {
    protected double smart_prob;

    public TicTacToeAIPlayer(char symbol, double smart_prob) {
        super();
        this.name = "Computer";
        this.symbol = symbol;
        this.smart_prob = smart_prob;
    }

    @Override
    public <T extends Slot> Move getMove(Board<T> board, Player[] playerLists) {
        if (board.getHeight() != 3 || board.getWidth() != 3) {
            throw new IllegalArgumentException("Board must be 3x3");
        }

        return smartAI(board, playerLists);
    }

    private <T extends Slot> Move smartAI(Board<T> board, Player[] playerLists) {
        if (StdRandom.bernoulli(smart_prob)) {
            Move newMove;

            // This step is to first find out if there
            // is any connection with yourself or others.
            //
            // If there is, priority connection/blocking
            if ((newMove = findConsecutive(board, this)) != null) {
                return newMove;
            }
            for (var player : playerLists) {
                if ((newMove = findConsecutive(board, player)) != null) {
                    return newMove;
                }
            }

            // Advanced AI: If you choose the first step first, you must choose if the center of the map is empty.
            if (board.atByOne(2, 2) == null) {
                return new Move1(2, 2);
            }

            // If not, we randomly pick one that has a high probability of winning (that is, the position that is not blocked by others)
            if ((newMove = findPossible(board, this)) != null) {
                return newMove;
            }
        }

        // No more, we just have to pick one at random
        return FindRandom(board);
    }

    /**
     * Helper function to extract player from slot
     * @param slot A slot, can be null
     * @return The <code>Player</code> object, or <code>null</code>
     */
    private static Player extractSlot(Slot slot) {
        return slot == null ? null : slot.getPlayer();
    }

    /**
     * Find a current Consecutive moves.
     * @param board The board
     * @param z The player to be compared
     * @param <T> The type of slot
     * @return The move, or <code>null</code> if no such move exists
     */
    private <T extends Slot> Move findConsecutive(Board<T> board,
                                                  Player z) {
        Player a = extractSlot(board.atByOne(1, 1)),
               b = extractSlot(board.atByOne(2, 1)),
               c = extractSlot(board.atByOne(3, 1)),
               d = extractSlot(board.atByOne(1, 2)),
               e = extractSlot(board.atByOne(2, 2)),
               f = extractSlot(board.atByOne(3, 2)),
               g = extractSlot(board.atByOne(1, 3)),
               h = extractSlot(board.atByOne(2, 3)),
               i = extractSlot(board.atByOne(3, 3));

        if (a == b && a == z && c == null) {
            return new Move1(3, 1);
        } else if (a == c && a == z && b == null) {
            return new Move1(2, 1);
        } else if (b == c && b == z && a == null) {
            return new Move1(1, 1);
        } else if (d == e && d == z && f == null) {
            return new Move1(3, 2);
        } else if (d == f && d == z && e == null) {
            return new Move1(2, 2);
        } else if (e == f && e == z && d == null) {
            return new Move1(1, 2);
        } else if (g == h && g == z && i == null) {
            return new Move1(3, 3);
        } else if (g == i && g == z && h == null) {
            return new Move1(2, 3);
        } else if (h == i && h == z && g == null) {
            return new Move1(1, 3);
        } else if (a == d && a == z && g == null) {
            return new Move1(1, 3);
        } else if (a == g && a == z && d == null) {
            return new Move1(1, 2);
        } else if (d == g && d == z && a == null) {
            return new Move1(1, 1);
        } else if (b == e && b == z && h == null) {
            return new Move1(2, 3);
        } else if (b == h && b == z && e == null) {
            return new Move1(2, 2);
        } else if (e == h && e == z && b == null) {
            return new Move1(2, 1);
        } else if (c == f && c == z && i == null) {
            return new Move1(3, 3);
        } else if (c == i && c == z && f == null) {
            return new Move1(3, 2);
        } else if (f == i && f == z && c == null) {
            return new Move1(3, 1);
        } else if (a == e && a == z && i == null) {
            return new Move1(3, 3);
        } else if (a == i && a == z && e == null) {
            return new Move1(2, 2);
        } else if (e == i && e == z && a == null) {
            return new Move1(1, 1);
        } else if (c == e && c == z && g == null) {
            return new Move1(1, 3);
        } else if (c == g && c == z && e == null) {
            return new Move1(2, 2);
        } else if (e == g && e == z && c == null) {
            return new Move1(3, 1);
        } else {
            return null;
        }
    }

    private Move findPossible(Board board, Player z) {
        Player a = extractSlot(board.atByOne(1, 1)),
               b = extractSlot(board.atByOne(2, 1)),
               c = extractSlot(board.atByOne(3, 1)),
               d = extractSlot(board.atByOne(1, 2)),
               e = extractSlot(board.atByOne(2, 2)),
               f = extractSlot(board.atByOne(3, 2)),
               g = extractSlot(board.atByOne(1, 3)),
               h = extractSlot(board.atByOne(2, 3)),
               i = extractSlot(board.atByOne(3, 3));

        int []randoms = {1, 2, 3, 4, 5, 6, 7, 8};
        StdRandom.shuffle(randoms);

        for (int k: randoms) {
            if ((a == z || a == null) && b == null && c == null && k == 1) {
                return new Move1(3, 1);
            } else if (a == null && b == null && (c == z || c == null) && k == 2) {
                return new Move1(1, 1);
            } else if ((g == z || g == null) && h == null && i == null && k == 3) {
                return new Move1(3, 3);
            } else if (g == null && h == null && (i == z || i == null) && k == 4) {
                return new Move1(1, 3);
            } else if ((a == z || a == null) && d == null && g == null && k == 5) {
                return new Move1(1, 3);
            } else if ((g == z || g == null) && d == null && a == null && k == 6) {
                return new Move1(1, 1);
            } else if ((c == z || c == null) && f == null && i == null && k == 7) {
                return new Move1(3, 3);
            } else if ((i == z || i == null) && f == null && c == null && k == 8) {
                return new Move1(3, 1);
            }
        }
        return null;
    }

    private <T extends Slot> Move FindRandom(Board<T> board) {
        int x = 0, y = 0;
        do {
            x = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            y = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        } while (board.atByOne(x, y) != null);
        return new Move1(x, y);
    }
}
