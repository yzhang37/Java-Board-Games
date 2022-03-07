package club.denkyoku.tictactoe.models;

import club.denkyoku.tictactoe.models.configuration.PlayerInfo;
import club.denkyoku.tictactoe.models.game.Mod;
import club.denkyoku.tictactoe.models.game.OrderAndChaosMod;
import club.denkyoku.tictactoe.models.game.ReversiMod;
import club.denkyoku.tictactoe.models.game.TicTacToeMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Config {
    // The main loop control, decides whether to exit the program.
    public static boolean doExitProgram = false;

    // The common setting for board size.
    // Some mod may ignore this setting.
    public static int boardSize = 3;

    public static boolean doReversiUseAnimation = true;
    public static int[] reversiSkirmishRoles = new int[]{1, 0};

    // The common setting for the number of players.
    // Some mod may ignore this setting.
    private static int playerCounts = 2;
    public static final ArrayList<PlayerInfo> playerInfos = new ArrayList<>();
    static {
        playerInfos.add(new PlayerInfo("Player 1", '✕'));
        playerInfos.add(new PlayerInfo("Player 2", '○'));
    }

    /**
     * Set the number of players.
     * @param playerCounts The number of players.
     */
    public static void setPlayerCounts(int playerCounts) {
        Config.playerCounts = playerCounts;
        while (playerInfos.size() < playerCounts) {
            char newSymbol = Resources.getRandomSymbol(
                    (int)(Math.random() * Resources.getRandomSymbolLength()));
            playerInfos.add(new PlayerInfo(
                    "Player " + (playerInfos.size() + 1), newSymbol));
        }
    }

    /**
     * Get the number of players.
     * @return The number of players.
     */
    public static int getPlayerCounts() {
        return playerCounts;
    }

    /**
     * A preset of the mod that will load.
     */
    public static final Mod[] modList = new Mod[] {
            new ReversiMod(),
            new TicTacToeMod(),
            new OrderAndChaosMod(),
    };

    // The UUID of the current game to load.
    public static String currentModUUID = modList[0].getUUID();

    public static final Map<String, Mod> modUUIDMap = new HashMap<>();
    static {
        for (Mod mod : modList) {
            modUUIDMap.put(mod.getUUID(), mod);
        }
    }
}
