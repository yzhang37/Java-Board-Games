package club.denkyoku.tictactoe.models.gamemenu;

import club.denkyoku.tictactoe.libraries.math.StdRandom;
import club.denkyoku.tictactoe.models.Config;
import club.denkyoku.tictactoe.models.ModMenu;
import club.denkyoku.tictactoe.models.configuration.PlayerInfo;
import club.denkyoku.tictactoe.models.gameplay.ReversiGamePlay;
import club.denkyoku.tictactoe.models.player.HumanPlayer;
import club.denkyoku.tictactoe.models.player.ReversiPlayer;
import club.denkyoku.tictactoe.services.output.controls.Menu;
import club.denkyoku.tictactoe.services.output.controls.MessageDialog;


public class ReversiMenu {
    protected static final String[] mainMenuItems = new String[]{
            "Single Player", "Multiplayer", "Skirmish", "Settings", "Mods", "Exit"};

    protected static final String[][] exitQueryMessage = new String[][] {
            new String[]{ "Ready to get back to work?" },
            new String[] { "I wouldn't leave if I were you.", "", "Work is much worse." },
            new String[] { "I'm not leaving.", "I'm not leaving.", "I'm not leaving. <333333" },
            new String[] { "Are you sure you want to quit", "this great game? "},
            new String[] { "Go ahead and leave.", "", "See if I care." }
    };

    protected static final MessageDialog.Button[] exitButtons = new MessageDialog.Button[] {
            new MessageDialog.Button("Leave Anyway", '\0'),
            new MessageDialog.Button("No, I'm not leaving", '\0'),
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

    protected void singlePlayer() {
        ReversiPlayer[] players = new ReversiPlayer[2];
        for (int i = 0; i < 2; i++) {
            PlayerInfo info = Config.playerInfos.get(i);
            players[i] = new ReversiPlayer(false, 1.0, info.name, info.symbol);
        }
        players[0].setSymbol('●');
        players[1].setSymbol('○');

        ReversiGamePlay gamePlay = new ReversiGamePlay(players, true);
        gamePlay.start();
    }

    protected void multiPlayer() {
        ReversiPlayer[] players = new ReversiPlayer[2];
        for (int i = 0; i < 2; i++) {
            PlayerInfo info = Config.playerInfos.get(i);
            players[i] = new ReversiPlayer(true, 1.0, info.name, info.symbol);
        }
        players[0].setSymbol('●');
        players[1].setSymbol('○');

        ReversiGamePlay gamePlay = new ReversiGamePlay(players, true);
        gamePlay.start();
    }

    protected void skirmish() {

    }

    protected void settings() {

    }

}
