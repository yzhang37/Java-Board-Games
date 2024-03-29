package club.denkyoku.tictactoe.models.gamemenu;

import club.denkyoku.tictactoe.models.Config;
import club.denkyoku.tictactoe.models.ModMenu;
import club.denkyoku.tictactoe.models.Resources;
import club.denkyoku.tictactoe.models.gameplay.GamePlay;
import club.denkyoku.tictactoe.models.gameplay.TicTacToeGamePlay;
import club.denkyoku.tictactoe.models.player.HumanPlayer;
import club.denkyoku.tictactoe.models.player.Player;
import club.denkyoku.tictactoe.models.player.TicTacToeAIPlayer;
import club.denkyoku.tictactoe.services.output.controls.Menu;
import club.denkyoku.tictactoe.services.output.controls.MessageDialog;
import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;

import java.util.Arrays;


/**
 * The Game Entry Menu for TicTacToe.
 */
public class TicTacToeMenu {
    protected static final String[] mainMenuItems = new String[]{
            "Single Player", "Multiplayer", "Settings", "Mods", "Exit"};
    protected static final String copyright = """

Copyright (c) 2007-2011, 2014, 2019-2022,
Denkyoku. All Rights Reserved.""";
    protected static final String[] exitQueryMessage = new String[]{
            "Are you sure you want to exit?"
    };
    protected static final String[] singleWarning = new String[] {
            "[Single Player] only supports",
            "classic Tic-Tac-Toe and 2 players.",
    };

    /**
     * The main entry point of the program.
     */
    public void start() {
        // When enter the game, we first run the bell once.
        ConsoleHelper.bell();

        Menu menu = new Menu(mainMenuItems,
                TicTacToeMenu.getCustomTicName(Config.boardSize), copyright);
        while (true) {

            int ret = menu.start();
            switch (ret) {
                case 0 -> singlePlayer();
                case 1 -> multiplayer();
                case 2 -> settings();
                case 3 -> {
                    if (ModMenu.chooseMod())
                        return;
                }
                case 4, -1 -> {
                    int retVal = MessageDialog.show(exitQueryMessage,
                            MessageDialog.getYesNo(), 1, 1);
                    if (retVal == 0) {
                        ConsoleHelper.println("See you~");
                        Config.doExitProgram = true;
                        return;
                    }
                }
            }
        }
    }

    /**
     * Do single player game.
     */
    protected void singlePlayer() {
        if (Config.boardSize != 3 || Config.getPlayerCounts() != 2) {
            MessageDialog.showOK(singleWarning);
            return;
        }

        Menu menu = new Menu(
                new String[]{"I can win", "Bring it on", "Hardcore", "Nightmare"},
                "Choose Difficulty", ""
        );

        int ret = menu.start();
        Player[] players;
        switch (ret) {
            case 0, 1, 2, 3 -> {
                double ai_prob = 1.0;
                if (ret == 0) {
                    ai_prob = 0.1;
                } else if (ret == 1) {
                    ai_prob = 0.4;
                } else if (ret == 2) {
                    ai_prob = 0.8;
                }
                players = new Player[]{
                        new HumanPlayer("Player", Config.playerInfos.get(0).symbol),
                        new TicTacToeAIPlayer(Config.playerInfos.get(1).symbol, ai_prob),
                };
                GamePlay gamePlay = new TicTacToeGamePlay(Config.boardSize, players);
                gamePlay.start();
            }
        }
    }

    /**
     * Do multiplayer game.
     */
    protected void multiplayer() {
        GamePlay gamePlay = new TicTacToeGamePlay(Config.boardSize,
                HumanPlayer.createHumanPlayers());
        gamePlay.start();
    }

    /**
     * Do settings.
     */
    protected void settings() {
        String[] basicSettings = new String[]{
                "Board size",
                "Player counts"
        };

        while (true) {
            String[] advSettings = Arrays.copyOf(basicSettings,
                    basicSettings.length + Config.getPlayerCounts());
            for (int i = 0; i < Config.getPlayerCounts(); i++) {
                advSettings[basicSettings.length + i] =
                        String.format("Player %d settings: %c", (i + 1),
                                Config.playerInfos.get(i).symbol);
            }

            Menu menu = new Menu(advSettings,
                    TicTacToeMenu.getCustomTicName(Config.boardSize) + " Settings", "");
            int ret = menu.start();

            switch (ret) {
                case 0:
                    settingsBoardSize();
                    break;
                case 1:
                    settingsPlayerCounts();
                    break;
                case -1:
                    return;
                default:
                    playerFancySettings(ret - basicSettings.length);
            }
        }
    }

    /**
     * Change player id settings
     * @param id the player id
     */
    protected void playerFancySettings(int id) {
        String[] settings = new String[]{
                "Change your name",
                "Customize symbol"
        };
        String str1 = String.format("Player %d settings", (id + 1));
        String str2 = "═".repeat(str1.length() + 2);
        while (true) {

            String title = String.format("%s\n%s\nName: %s\nSymbol: %c", str1, str2,
                    Config.playerInfos.get(id).name,
                    Config.playerInfos.get(id).symbol);
            Menu menu = new Menu(settings, title, "");
            int ret = menu.start();
            switch (ret) {
                case 0:
                    MessageDialog.showOK(new String[]{
                            "Under Construction",
                            "Sorry, this feature is not available yet.",
                            "Due to the lack of TextInput Control in Terminal.",
                            "It will be available in the future."
                    });
                    break;
                case 1:
                    settingsPlayerSymbol(id);
                    break;
                case -1:
                    return;
            }
        }
    }

    protected void settingsPlayerSymbol(int id) {
        String [] symbols = new String[Resources.getAllSymbolLength()];
        for (int i = 0; i < symbols.length; i++) {
            symbols[i] = String.format("%c", Resources.getAllSymbol(i));
        }
        Menu menu = new Menu(symbols,"Player " + (id + 1) + " symbol", "");
        int ret = menu.start();
        if (ret >= 0) {
            Config.playerInfos.get(id).symbol = Resources.getAllSymbol(ret);
        }
    }

    /**
     * Do change player counts
     */
    protected void settingsPlayerCounts() {
        Menu menu = new Menu(
                new String[]{"2", "3", "4", "5", "6", "7", "8", "9"},
                "Choose Player Counts", ""
        );
        int ret = menu.start();
        switch (ret) {
            case 0 -> Config.setPlayerCounts(2);
            case 1 -> Config.setPlayerCounts(3);
            case 2 -> Config.setPlayerCounts(4);
            case 3 -> Config.setPlayerCounts(5);
            case 4 -> Config.setPlayerCounts(6);
            case 5 -> Config.setPlayerCounts(7);
            case 6 -> Config.setPlayerCounts(8);
            case 7 -> Config.setPlayerCounts(9);
            default -> {
            }
        }
    }

    /**
     * Do change the Board Size
     */
    protected void settingsBoardSize() {
        Menu menu = new Menu(
                new String[]{"3✕3", "4✕4", "5✕5", "6✕6", "7✕7", "8✕8", "9✕9"},
                "Choose Board Size", ""
        );
        int ret = menu.start();
        switch (ret) {
            case 0 -> Config.boardSize = 3;
            case 1 -> Config.boardSize = 4;
            case 2 -> Config.boardSize = 5;
            case 3 -> Config.boardSize = 6;
            case 4 -> Config.boardSize = 7;
            case 5 -> Config.boardSize = 8;
            case 6 -> Config.boardSize = 9;
        }
    }

    /**
     * Helper function to show different Game title for TicTacToe
     * @param boardSize board size
     * @return The title of the game based on board size.
     */
    public static String getCustomTicName(int boardSize) {
        if (boardSize == 3) {
            return "Tic-Tac-Toe";
        } else {
            return String.format("%d-In-a-Row", boardSize);
        }
    }
}
