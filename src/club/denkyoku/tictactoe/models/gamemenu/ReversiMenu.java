package club.denkyoku.tictactoe.models.gamemenu;

import club.denkyoku.tictactoe.libraries.math.StdRandom;
import club.denkyoku.tictactoe.models.Config;
import club.denkyoku.tictactoe.models.ModMenu;
import club.denkyoku.tictactoe.models.configuration.PlayerInfo;
import club.denkyoku.tictactoe.models.gameplay.ReversiGamePlay;
import club.denkyoku.tictactoe.models.player.HumanPlayer;
import club.denkyoku.tictactoe.models.player.ReversiPlayer;
import club.denkyoku.tictactoe.services.input.DataSync;
import club.denkyoku.tictactoe.services.input.KeyHandler;
import club.denkyoku.tictactoe.services.output.controls.Menu;
import club.denkyoku.tictactoe.services.output.controls.MessageDialog;
import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;


public class ReversiMenu {
    protected final String[] mainMenuItems = new String[]{
            "Single Player", "Multiplayer", "Skirmish", "Settings", "Mods", "Exit"};

    protected final String[][] exitQueryMessage = new String[][] {
            new String[]{ "Ready to get back to work?" },
            new String[] { "I wouldn't leave if I were you.", "", "Work is much worse." },
            new String[] { "I'm not leaving.", "I'm not leaving.", "I'm not leaving. <333333" },
            new String[] { "Are you sure you want to quit", "this great game? "},
            new String[] { "Go ahead and leave.", "", "See if I care." }
    };

    protected final MessageDialog.Button[] exitButtons = new MessageDialog.Button[] {
            new MessageDialog.Button("Leave Anyway", 'Y'),
            new MessageDialog.Button("No, I'm not leaving", 'N'),
    };

    protected final String[] skirmishRoleNames = new String[] {
            "Human", "AI: Beginner", "AI: Novice", "AI: Expert", "AI: Master"
    };

    protected final MessageDialog.Button[] skirmishRoleMenus = new MessageDialog.Button[] {
            new MessageDialog.Button("Human", 'H'),
            new MessageDialog.Button("AI: Beginner", 'B'),
            new MessageDialog.Button("AI: Novice", 'N'),
            new MessageDialog.Button("AI: Expert", 'E'),
            new MessageDialog.Button("AI: Master", 'M'),
    };

    // begin the main menu
    public void start() {
        Menu menu = new Menu(mainMenuItems,
                "🔴🔵Reversi",
                """

Written by John Yin, 2022
📧johnyin@bu.edu

AI part was inspired by the Windows 3.1 Reversi.
Copyright © 1987-1990 Microsoft Corp.""");

        while (true) {
            int ret = menu.start();

            switch (ret) {
                case 0 -> singlePlayer();
                case 1 -> multiPlayer();
                case 2 -> skirmish();
                case 3 -> settings();
                case 4 -> {
                    if (ModMenu.chooseMod())
                        return;
                }
                case 5, -1 -> {
                    int choose = StdRandom.uniform(0, exitQueryMessage.length);

                    int retVal = MessageDialog.show(exitQueryMessage[choose],
                            exitButtons, 1, 1);
                    if (retVal == 0) {
                        Config.doExitProgram = true;
                        return;
                    }
                }
            }
        }
    }

    /**
     * Helper function for get smart prob
     * @param level the level of the AI (0 for human)
     * @return  the smart prob
     */
    protected double getSmartProb(int level) {
        switch (level) {
            case 0 -> {
                return 1.0;
            }
            case 1 -> {
                return 0.3;
            }
            case 2 -> {
                return 0.5;
            }
            case 3 -> {
                return 0.7;
            }
            case 4 -> {
                return 0.9;
            }
        }
        return 1.0;
    }

    protected int getDepth(int level) {
        switch (level) {
            case 0, 3 -> {
                return 4;
            }
            case 1, 2 -> {
                return 2;
            }
            case 4 -> {
                return 6;
            }
        }
        return 4;
    }

    protected void singlePlayer() {
        Menu menu = new Menu(
                new String[]{"Beginner", "Novice", "Expert", "Master"}, "Choose Difficulty", null
        );
        ReversiPlayer[] players = new ReversiPlayer[2];

        while (true) {
            int ret = menu.start();

            switch (ret) {
                case 0, 1, 2, 3 -> {
                    players[1] = new ReversiPlayer(false, getSmartProb(ret + 1), getDepth(ret + 1), "Computer", '○');
                    players[0] = new ReversiPlayer(true, getSmartProb(0), getDepth(0), Config.playerInfos.get(0).name, '●');
                    ReversiGamePlay gamePlay = new ReversiGamePlay(players, Config.doReversiUseAnimation);
                    gamePlay.start();
                    return;
                }
                case -1 -> {
                    return;
                }
            }
        }
    }

    protected void multiPlayer() {
        ReversiPlayer[] players = new ReversiPlayer[2];
        for (int i = 0; i < 2; i++) {
            PlayerInfo info = Config.playerInfos.get(i);
            players[i] = new ReversiPlayer(true, 1.0, 4, info.name, info.symbol);
        }
        players[0].setSymbol('●');
        players[1].setSymbol('○');

        ReversiGamePlay gamePlay = new ReversiGamePlay(players, Config.doReversiUseAnimation);
        gamePlay.start();
    }

    protected static class SkirmishDataSync extends DataSync {
        public boolean doSelect;
        public boolean doLeft;
        public boolean doUp;
        public boolean doDown;
        public boolean doEsc;
        public boolean keepRun;

        protected SkirmishDataSync() {
            reset();
        }

        @Override
        public void reset() {
            doSelect = doLeft = doUp = doDown = doEsc = false;
            keepRun = true;
        }
    }

    protected static class SkirmishKeyHandler extends KeyHandler {
        public SkirmishDataSync dataSync;

        public SkirmishKeyHandler(SkirmishDataSync dataSync) {
            this.dataSync = dataSync;
        }

        @Override
        protected void onKeyEnter() {
            dataSync.doSelect = true;
        }

        @Override
        protected void onKeyLeft() {
            dataSync.doLeft = true;
        }

        @Override
        protected void onKeyUp() {
            dataSync.doUp = true;
        }

        @Override
        protected void onKeyDown() {
            dataSync.doDown = true;
        }

        @Override
        protected void onKeyEsc() {
            dataSync.doEsc = true;
        }
    }

    protected void skirmish() {
        int line = 0;
        SkirmishDataSync dataSync = new SkirmishDataSync();
        SkirmishKeyHandler keyHandler = new SkirmishKeyHandler(dataSync);
        boolean redraw = true;

        while (dataSync.keepRun) {
            dataSync.reset();
            if (redraw) {
                printSkirmishUI(line, skirmishRoleNames[Config.reversiSkirmishRoles[0]],
                        skirmishRoleNames[Config.reversiSkirmishRoles[1]]);
                redraw = false;
            }

            keyHandler.run();
            if (dataSync.doEsc) {
                dataSync.keepRun = false;
            } else if (dataSync.doDown) {
                if (line < 2) {
                    line ++;
                    redraw = true;
                }
            } else if (dataSync.doUp) {
                if (line > 0) {
                    line --;
                    redraw = true;
                }
            } else if (dataSync.doSelect) {
                if (line == 2) {
                    ReversiPlayer[] players = new ReversiPlayer[2];
                    for (int i = 0; i < 2; i++) {
                        PlayerInfo info = Config.playerInfos.get(i);
                        int role = Config.reversiSkirmishRoles[i];
                        players[i] = new ReversiPlayer(role == 0, getSmartProb(role), getDepth(role), info.name, info.symbol);
                    }
                    players[0].setSymbol('●');
                    players[1].setSymbol('○');

                    ReversiGamePlay gamePlay = new ReversiGamePlay(players, Config.doReversiUseAnimation);
                    gamePlay.start();

                    redraw = true;
                } else if (line == 1 || line == 0) {
                    int cur = Config.reversiSkirmishRoles[line];
                    int ret = MessageDialog.show(new String[]{ "Select Role" },
                            skirmishRoleMenus, cur, -1);
                    if (ret >= 0) {
                        Config.reversiSkirmishRoles[line] = ret;
                        redraw = true;
                    }
                }
            }
        }
    }

    protected void printSkirmishUI(int line, String player1Role, String player2Role) {
        String[] screens = new String[7];
        screens[0] = "Customize your play";
        screens[1] = "Player 1: " + player1Role;
        screens[2] = "Player 2: " + player2Role;
        screens[3] = "Play";
        for (int i = 1; i <= 3; i++) {
            if (i - 1 == line) {
                screens[i] = "▶ " + screens[i];
            } else {
                screens[i] = "  " + screens[i];
            }
        }
        screens[4] = "";
        screens[5] = "Enter: Select";
        screens[6] = "←/Esc: Back";
        ConsoleHelper.printScreen(screens);
    }

    protected void settings() {
        Menu menu = new Menu(new String[]{
                "Use Animation"
        }, "Reversi Settings", null);
        while (true) {
            int ret = menu.start();
            switch (ret) {
                case 0 -> {
                    settingsUseAnimation();
                }
                case -1 -> { return; }
            }
        }
    }

    protected void settingsUseAnimation() {
        Menu menu = new Menu(new String[]{
                "Yes", "No"
        }, "Use Animation", null);
        while (true) {
            int ret = menu.start();
            switch (ret) {
                case 0 -> {
                    Config.doReversiUseAnimation = true;
                    return;
                }
                case 1 -> {
                    Config.doReversiUseAnimation = false;
                    return;
                }
                case -1 -> { return; }
            }
        }
    }
}
