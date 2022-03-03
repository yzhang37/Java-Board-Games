package club.denkyoku.TicTacToe;

import club.denkyoku.TicTacToe.Library.Terminal.ConsoleHelper;
import club.denkyoku.TicTacToe.UserInterface.Controls.Menu;
import club.denkyoku.TicTacToe.UserInterface.Controls.MessageDialog;

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
                    config.getCustomTicName(config.boardSize), copyright);
            int ret = menu.start();
            switch (ret) {
                case 0:
                    singlePlayer();
                    break;
                case 1:
                    multiplayer();
                    break;
                case 2:
                    settings();
                    break;
                case 3:
                case -1:
                    int retVal = MessageDialog.show(exitQueryMessage,
                            MessageDialog.getYesNo(), 1, 1);
                    if (retVal == 0) {
                        ConsoleHelper.println("See you~");
                        return;
                    }
            }
        }
    }

    protected void singlePlayer() {
        if (config.boardSize != 3 || config.getPlayerCounts() != 2) {
            MessageDialog.show(singleWarning);
            return;
        }

        Menu menu = new Menu(
                new String[]{"I can win", "Bring it on", "Hardcore", "Nightmare"},
                "Choose Difficulty", ""
        );

        int ret = menu.start();
        Board board = new NInRowBoard(config.boardSize);
        Player[] players;
        switch (ret) {
            case 0:
            case 1:
            case 2:
            case 3:
                double ai_prob = 1.0;
                if (ret == 0) {
                    ai_prob = 0.1;
                } else if (ret == 1) {
                    ai_prob = 0.4;
                } else if (ret == 2) {
                    ai_prob = 0.8;
                }
                players = new Player[]{
                        new HumanPlayer("Player", config.playerInfos.get(0).symbol),
                        new TicTacToeAIPlayer(config.playerInfos.get(1).symbol, ai_prob),
                };
                Game game = new Game(board, players);
                game.start();
                break;
        }
    }

    protected void multiplayer() {
        Board board = new NInRowBoard(config.boardSize);
        Game game = new Game(board, createPlayers());
        game.start();
    }

    protected Player[] createPlayers() {
        Player[] players = new Player[config.getPlayerCounts()];
        for (int i = 0; i < config.getPlayerCounts(); i++) {
            config.PlayerInfo info = config.playerInfos.get(i);
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
                    basicSettings.length + config.getPlayerCounts());
            for (int i = 0; i < config.getPlayerCounts(); i++) {
                advSettings[basicSettings.length + i] =
                        String.format("Player %d settings: %c", (i + 1),
                                config.playerInfos.get(i).symbol);
            }

            Menu menu = new Menu(advSettings,
                    config.getCustomTicName(config.boardSize) + " Settings", "");
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
                    config.playerInfos.get(id).name,
                    config.playerInfos.get(id).symbol);
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
        String [] symbols = new String[config.allSymbol.length];
        for (int i = 0; i < symbols.length; i++) {
            symbols[i] = String.format("%c", config.allSymbol[i]);
        }
        Menu menu = new Menu(symbols,"Player " + (id + 1) + " symbol", "");
        int ret = menu.start();
        if (ret >= 0) {
            config.playerInfos.get(id).symbol = config.allSymbol[ret];
        }
    }

    protected void settingsPlayerCounts() {
        Menu menu = new Menu(
                new String[]{"2", "3", "4", "5", "6", "7", "8", "9"},
                "Choose Player Counts", ""
        );
        int ret = menu.start();
        switch (ret) {
            case 0:
                config.setPlayerCounts(2);
                break;
            case 1:
                config.setPlayerCounts(3);
                break;
            case 2:
                config.setPlayerCounts(4);
                break;
            case 3:
                config.setPlayerCounts(5);
                break;
            case 4:
                config.setPlayerCounts(6);
                break;
            case 5:
                config.setPlayerCounts(7);
                break;
            case 6:
                config.setPlayerCounts(8);
                break;
            case 7:
                config.setPlayerCounts(9);
                break;
            default:
        }
    }

    protected void settingsBoardSize() {
        Menu menu = new Menu(
                new String[]{"3✕3", "4✕4", "5✕5", "6✕6", "7✕7", "8✕8", "9✕9"},
                "Choose Board Size", ""
        );
        int ret = menu.start();
        switch (ret) {
            case 0:
                config.boardSize = 3;
                break;
            case 1:
                config.boardSize = 4;
                break;
            case 2:
                config.boardSize = 5;
                break;
            case 3:
                config.boardSize = 6;
                break;
            case 4:
                config.boardSize = 7;
                break;
            case 5:
                config.boardSize = 8;
                break;
            case 6:
                config.boardSize = 9;
                break;
        }
    }
}
