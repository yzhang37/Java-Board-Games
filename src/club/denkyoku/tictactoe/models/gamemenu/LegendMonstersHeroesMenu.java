package club.denkyoku.tictactoe.models.gamemenu;

import club.denkyoku.tictactoe.models.Config;
import club.denkyoku.tictactoe.models.ModMenu;
import club.denkyoku.tictactoe.services.output.controls.Menu;
import club.denkyoku.tictactoe.services.output.controls.MessageDialog;
import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;

/**
 * Legend Monsters & Heroes 的游戏界面进入主菜单。
 */
public class LegendMonstersHeroesMenu {
    protected static final String[] legendMonstersHeroesMenu = {
        "Start Game",
        "Mods",
        "Exit",
    };
    protected static final String[] quitLMHMessage = {
        "Are you sure to quit",
        "Legend: Monsters & Heroes?",
    };

    /**
     * 进入游戏界面的函数。
     */
    public void start() {
        Menu menu = new Menu(legendMonstersHeroesMenu,
                "Legends: Monsters And Heroes\nA game of strategy and luck.", null);

        while (true) {
            int ret = menu.start();
            switch (ret) {
                case 0 -> {
                    // TODO: begin the game
                }
                case 1 -> {
                    if (ModMenu.chooseMod())
                        return;
                }
                case 2, -1 -> {
                    int retVal = MessageDialog.show(quitLMHMessage,
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
}
