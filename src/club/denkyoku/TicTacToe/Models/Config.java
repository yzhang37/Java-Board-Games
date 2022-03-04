package club.denkyoku.TicTacToe.Models;

import java.util.ArrayList;

public class Config {
    public static char[] randomSymbol = new char[]{
            '◎', '●', '◇', '◆', '□', '■', '♧', '♣', '♤', '♠', '♡', '♥', '♢', '♦', '♪', '♫', '$', '·', '∞'};
    public static char[] allSymbol = new char[]{'✕', '○',
            '◎', '●', '◇', '◆', '□', '■', '♧', '♣', '♤', '♠', '♡', '♥', '♢', '♦', '♪', '♫', '$', '·', '∞'};

    public static class PlayerInfo {
        public String name;
        public char symbol;
        public PlayerInfo(String name, char symbol) {
            this.name = name;
            this.symbol = symbol;
        }
    }

    public static int boardSize = 3;
    private static int playerCounts = 2;
    public static ArrayList<PlayerInfo> playerInfos = new ArrayList<>();

    public static void setPlayerCounts(int playerCounts) {
        Config.playerCounts = playerCounts;
        while (playerInfos.size() < playerCounts) {
            char newSymbol = randomSymbol[(int) (Math.random() * randomSymbol.length)];
            playerInfos.add(new PlayerInfo(
                    "Player " + (playerInfos.size() + 1), newSymbol));
        }
    }

    public static int getPlayerCounts() {
        return playerCounts;
    }

    static {
        playerInfos.add(new PlayerInfo("Player 1", '✕'));
        playerInfos.add(new PlayerInfo("Player 2", '○'));
    }

    public static String getCustomTicName(int boardSize) {
        if (boardSize == 3) {
            return "Tic-Tac-Toe";
        } else {
            return String.format("%d-In-a-Row", boardSize);
        }
    }
}
