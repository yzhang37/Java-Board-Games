package club.denkyoku.TicTacToe;

import java.util.ArrayList;

public class config {
    static class PlayerInfo {
        public String name;
        public char symbol;
        public PlayerInfo(String name, char symbol) {
            this.name = name;
            this.symbol = symbol;
        }
    }

    static int boardSize = 3;
    private static int playerCounts;
    static ArrayList<PlayerInfo> playerInfos = new ArrayList<>();

    static void setPlayerCounts(int playerCounts) {
        config.playerCounts = playerCounts;
        while (playerInfos.size() < playerCounts) {
            playerInfos.add(new PlayerInfo(
                    "Player " + (playerInfos.size() + 1), '✕'));
        }
    }

    static int getPlayerCounts() {
        return playerCounts;
    }

    static {
        setPlayerCounts(2);
        playerInfos.add(new PlayerInfo("Player 1", '✕'));
        playerInfos.add(new PlayerInfo("Player 2", '○'));
    }

    static String getCustomTicName(int boardSize) {
        if (boardSize == 3) {
            return "TicTacToe";
        } else {
            return String.format("%d-In-a-Row", boardSize);
        }
    }
}
