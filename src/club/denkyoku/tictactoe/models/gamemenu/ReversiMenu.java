package club.denkyoku.tictactoe.models.gamemenu;

import club.denkyoku.tictactoe.models.Config;
import club.denkyoku.tictactoe.models.ModMenu;
import club.denkyoku.tictactoe.services.output.controls.Menu;

public class ReversiMenu {
    protected static final String[] mainMenuItems = new String[]{
            "Single Player", "Multiplayer", "Skirmish", "Settings", "Mods", "Exit"};

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
                case 0:
                    singlePlayer();
                    break;
                case 1:
                    multiPlayer();
                    break;
                case 2:
                    skirmish();
                    break;
                case 3:
                    settings();
                    break;
                case 4:
                    if (ModMenu.chooseMod())
                        return;
                    break;
                case 5:
                case -1:
                    Config.doExitProgram = true;
                    return;
            }
        }
    }

    protected void singlePlayer() {

    }

    protected void multiPlayer() {

    }

    protected void skirmish() {

    }

    protected void settings() {

    }

}
