package club.denkyoku.TicTacToe.Models;

import java.util.ArrayList;

public class config {
    static char[] randomSymbol = new char[]{
            '◎', '●', '◇', '◆', '□', '■', '♧', '♣', '♤', '♠', '♡', '♥', '♢', '♦', '♪', '♫', '$', '·', '∞'};
    static char[] allSymbol = new char[]{'✕', '○',
            '◎', '●', '◇', '◆', '□', '■', '♧', '♣', '♤', '♠', '♡', '♥', '♢', '♦', '♪', '♫', '$', '·', '∞'};

    static class PlayerInfo {
        public String name;
        public char symbol;
        public PlayerInfo(String name, char symbol) {
            this.name = name;
            this.symbol = symbol;
        }
    }

    static int boardSize = 3;
    private static int playerCounts = 2;
    static ArrayList<PlayerInfo> playerInfos = new ArrayList<>();

    static void setPlayerCounts(int playerCounts) {
        config.playerCounts = playerCounts;
        while (playerInfos.size() < playerCounts) {
            char newSymbol = randomSymbol[(int) (Math.random() * randomSymbol.length)];
            playerInfos.add(new PlayerInfo(
                    "Player " + (playerInfos.size() + 1), newSymbol));
        }
    }

    static int getPlayerCounts() {
        return playerCounts;
    }

    static {
        playerInfos.add(new PlayerInfo("Player 1", '✕'));
        playerInfos.add(new PlayerInfo("Player 2", '○'));
    }

    static String getCustomTicName(int boardSize) {
        if (boardSize == 3) {
            return "Tic-Tac-Toe";
        } else {
            return String.format("%d-In-a-Row", boardSize);
        }
    }
}
