package club.denkyoku.TicTacToe;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToeAIPlayer extends Player {
    public TicTacToeAIPlayer(char symbol, int difficulty) {
        super();
        this.name = "Computer";
        this.symbol = symbol;
    }

    @Override
    public Move getMove(Board board, int myId, int[] otherIds) {
        if (board.getHeight() != 3 || board.getWidth() != 3) {
            throw new IllegalArgumentException("Board must be 3x3");
        }

        return smartAI(board, myId, otherIds);
    }

    private Move smartAI(Board board, int myId, int[] otherIds) {
        if (board.isEmpty()) {
            return new Move1(2, 2);
        }

        Move newMove;
        // 这一步先去寻找有没有自己或者别人的连子。如果有就优先连活/堵塞
        if ((newMove = findConsecutive(board, myId)) != null) {
            return newMove;
        }
        for (int id : otherIds) {
            if ((newMove = findConsecutive(board, id)) != null) {
                return newMove;
            }
        }

        // 没有的话，我们随机挑一个有大概率能胜利的（就是没被别人堵上的位置）
        if ((newMove = findPossible(board, myId)) != null) {
            return newMove;
        }

        // 再没有，我们只能随机挑一个了
        return FindRandom(board);
    }

    private Move findConsecutive(Board board, int z) {
        int a = board.atByOne(1, 1),
            b = board.atByOne(2, 1),
            c = board.atByOne(3, 1),
            d = board.atByOne(1, 2),
            e = board.atByOne(2, 2),
            f = board.atByOne(3, 2),
            g = board.atByOne(1, 3),
            h = board.atByOne(2, 3),
            i = board.atByOne(3, 3);

        if (a == b && a == z && c == 0) {
            return new Move1(3, 1);
        } else if (a == c && a == z && b == 0) {
            return new Move1(2, 1);
        } else if (b == c && b == z && a == 0) {
            return new Move1(1, 1);
        } else if (d == e && d == z && f == 0) {
            return new Move1(3, 2);
        } else if (d == f && d == z && e == 0) {
            return new Move1(2, 2);
        } else if (e == f && e == z && d == 0) {
            return new Move1(1, 2);
        } else if (g == h && g == z && i == 0) {
            return new Move1(3, 3);
        } else if (g == i && g == z && h == 0) {
            return new Move1(2, 3);
        } else if (h == i && h == z && g == 0) {
            return new Move1(1, 3);
        } else if (a == d && a == z && g == 0) {
            return new Move1(1, 3);
        } else if (a == g && a == z && d == 0) {
            return new Move1(1, 2);
        } else if (d == g && d == z && a == 0) {
            return new Move1(1, 1);
        } else if (b == e && b == z && h == 0) {
            return new Move1(2, 3);
        } else if (b == h && b == z && e == 0) {
            return new Move1(2, 2);
        } else if (e == h && e == z && b == 0) {
            return new Move1(2, 1);
        } else if (c == f && c == z && i == 0) {
            return new Move1(3, 3);
        } else if (c == i && c == z && f == 0) {
            return new Move1(3, 2);
        } else if (f == i && f == z && c == 0) {
            return new Move1(3, 1);
        } else if (a == e && a == z && i == 0) {
            return new Move1(3, 3);
        } else if (a == i && a == z && e == 0) {
            return new Move1(2, 2);
        } else if (e == i && e == z && a == 0) {
            return new Move1(1, 1);
        } else if (c == e && c == z && g == 0) {
            return new Move1(1, 3);
        } else if (c == g && c == z && e == 0) {
            return new Move1(2, 2);
        } else if (e == g && e == z && c == 0) {
            return new Move1(3, 1);
        } else {
            return null;
        }
    }

    private Move findPossible(Board board, int z) {
        int a = board.atByOne(1, 1),
            b = board.atByOne(2, 1),
            c = board.atByOne(3, 1),
            d = board.atByOne(1, 2),
            e = board.atByOne(2, 2),
            f = board.atByOne(3, 2),
            g = board.atByOne(1, 3),
            h = board.atByOne(2, 3),
            i = board.atByOne(3, 3);

        int []randoms = {1, 2, 3, 4, 5, 6, 7, 8};
        ShuffleArray(randoms);

        for (int k: randoms) {
            if ((a == z || a == 0) && b == 0 && c == 0 && k == 1) {
                return new Move1(3, 1);
            } else if (a == 0 && b == 0 && (c == z || c == 0) && k == 2) {
                return new Move1(1, 1);
            } else if ((g == z || g == 0) && h == 0 && i == 0 && k == 3) {
                return new Move1(3, 3);
            } else if (g == 0 && h == 0 && (i == z || i == 0) && k == 4) {
                return new Move1(1, 3);
            } else if ((a == z || a == 0) && d == 0 && g == 0 && k == 5) {
                return new Move1(1, 3);
            } else if ((g == z || g == 0) && d == 0 && a == 0 && k == 6) {
                return new Move1(1, 1);
            } else if ((c == z || c == 0) && f == 0 && i == 0 && k == 7) {
                return new Move1(3, 3);
            } else if ((i == z || i == 0) && f == 0 && c == 0 && k == 8) {
                return new Move1(3, 1);
            }
        }
        return null;
    }

    static void ShuffleArray(int[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private Move FindRandom(Board board) {
        int x = 0, y = 0;
        do {
            x = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            y = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        } while (board.atByOne(x, y) != 0);
        return new Move1(x, y);
    }
}
