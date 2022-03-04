package club.denkyoku.tictactoe.models.gamemenu;

import club.denkyoku.tictactoe.models.Config;
import club.denkyoku.tictactoe.models.ModMenu;
import club.denkyoku.tictactoe.models.gameplay.GamePlay;
import club.denkyoku.tictactoe.models.gameplay.OrderAndChaosGamePlay;
import club.denkyoku.tictactoe.models.player.HumanPlayer;
import club.denkyoku.tictactoe.models.player.Player;
import club.denkyoku.tictactoe.services.output.controls.Menu;
import club.denkyoku.tictactoe.services.output.controls.MessageDialog;
import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;


/**
 * The Game Entry Menu for OrderAndChaos.
 */
public class OrderAndChaosMenu {
    protected static String[] mainMenuItems = new String[]{"Start Play!", "Mods", "Exit"};
    protected static String copyright = "\nCopyright (c) 2019-2022 Denkyoku. All Rights Reserved.";
    protected static String[] exitQueryMessage = new String[]{
            "Are you sure you want to exit?"
    };

    public void start() {
        while (true) {
            Menu menu = new Menu(mainMenuItems, "Order and Chaos", copyright);
            int ret = menu.start();
            switch (ret) {
                case 0 -> multiplayer();
                case 1 -> {
                    if (ModMenu.chooseMod())
                        return;
                }
                case 2, -1 -> {
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

    protected void multiplayer() {
        GamePlay gamePlay = new OrderAndChaosGamePlay(createPlayers());
        gamePlay.start();
    }

    protected Player[] createPlayers() {
        int playerMax = 2;
        Player[] players = new Player[playerMax];

        for (int i = 0; i < playerMax; i++) {
            var info = Config.playerInfos.get(i);
            players[i] = new HumanPlayer(info.name, info.symbol);
        }

        players[0].setName("Orders Team");
        players[1].setName("Chaos Team");

        return players;
    }
}

