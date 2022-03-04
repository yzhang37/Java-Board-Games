package club.denkyoku.TicTacToe.Models;

import club.denkyoku.TicTacToe.Models.GamePlay.GamePlay;
import club.denkyoku.TicTacToe.Models.GamePlay.TicTacToeGamePlay;
import club.denkyoku.TicTacToe.Services.Output.Terminal.ConsoleHelper;
import club.denkyoku.TicTacToe.Services.Output.Controls.Menu;
import club.denkyoku.TicTacToe.Services.Output.Controls.MessageDialog;

import java.util.Arrays;


public class TicTacToe {
    protected static String[] mainMenuItems = new String[]{"Single Player", "Multiplayer", "Settings", "Exit"};
    protected static String copyright = "\nCopyright (c) 2022 Denkyoku. All Rights Reserved.";
    protected static String[] exitQueryMessage = new String[]{
            "Are you sure you want to exit?"
    };
    protected static String[] singleWarning = new String[] {
            "[Single Player] only supports",
            "classic Tic-Tac-Toe and 2 players.",
    };

    public void start() {
        while (true) {
            Menu menu = new Menu(mainMenuItems,
                    Config.getCustomTicName(Config.boardSize), copyright);
            int ret = menu.start();
            switch (ret) {
                case 0 -> singlePlayer();
                case 1 -> multiplayer();
                case 2 -> settings();
                case 3, -1 -> {
                    int retVal = MessageDialog.show(exitQueryMessage,
                            MessageDialog.getYesNo(), 1, 1);
                    if (retVal == 0) {
                        ConsoleHelper.println("See you~");
                        return;
                    }
                }
            }
        }
    }

    protected void singlePlayer() {
        if (Config.boardSize != 3 || Config.getPlayerCounts() != 2) {
            MessageDialog.show(singleWarning);
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

    protected void multiplayer() {
        GamePlay gamePlay = new TicTacToeGamePlay(Config.boardSize, createPlayers());
        gamePlay.start();
    }

    protected Player[] createPlayers() {
        Player[] players = new Player[Config.getPlayerCounts()];
        for (int i = 0; i < Config.getPlayerCounts(); i++) {
            Config.PlayerInfo info = Config.playerInfos.get(i);
            players[i] = new HumanPlayer(info.name, info.symbol);
        }
        return players;
    }

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
                    Config.getCustomTicName(Config.boardSize) + " Settings", "");
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
                    MessageDialog.show(new String[]{
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
        String [] symbols = new String[Config.allSymbol.length];
        for (int i = 0; i < symbols.length; i++) {
            symbols[i] = String.format("%c", Config.allSymbol[i]);
        }
        Menu menu = new Menu(symbols,"Player " + (id + 1) + " symbol", "");
        int ret = menu.start();
        if (ret >= 0) {
            Config.playerInfos.get(id).symbol = Config.allSymbol[ret];
        }
    }

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
}
