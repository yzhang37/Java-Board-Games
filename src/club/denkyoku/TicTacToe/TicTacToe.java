package club.denkyoku.TicTacToe;

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
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                players = new Player[]{
                        new TicTacToeAIPlayer(config.playerInfos.get(0).symbol, 3),
                        new HumanPlayer("Player", config.playerInfos.get(1).symbol)
                };
                Game game = new Game(board, players);
                game.start();
                break;
            default:
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
            var info = config.playerInfos.get(i);
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
            }
        }
    }

    private void settingsPlayerCounts() {
        Menu menu = new Menu(
                new String[]{"2", "3", "4", "5", "6", "7", "8", "9"},
                "Choose Player Counts", ""
        );
        int ret = menu.start();
        switch (ret) {
            case 0 -> config.setPlayerCounts(2);
            case 1 -> config.setPlayerCounts(3);
            case 2 -> config.setPlayerCounts(4);
            case 3 -> config.setPlayerCounts(5);
            case 4 -> config.setPlayerCounts(6);
            case 5 -> config.setPlayerCounts(7);
            case 6 -> config.setPlayerCounts(8);
            case 7 -> config.setPlayerCounts(9);
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
            case 0 -> config.boardSize = 3;
            case 1 -> config.boardSize = 4;
            case 2 -> config.boardSize = 5;
            case 3 -> config.boardSize = 6;
            case 4 -> config.boardSize = 7;
            case 5 -> config.boardSize = 8;
            case 6 -> config.boardSize = 9;
            default -> {
            }
        }
    }
}
