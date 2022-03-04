package club.denkyoku.TicTacToe.Models;

import club.denkyoku.TicTacToe.Services.Output.Controls.Menu;

public class ModMenu {
    public static boolean chooseMod() {
        String[] modItemList = new String[Config.modList.length];
        String[] modUuidList = new String[Config.modList.length];

        for (int i = 0; i < Config.modList.length; i++) {
            modItemList[i] = Config.modList[i].getDisplayName();
            modUuidList[i] = Config.modList[i].getUUID();
        }

        Menu menu = new Menu(modItemList, "Mods", null);
        int ret = menu.start();

        if (ret == -1) {
            return false;
        } else {
            Config.currentModUUID = modUuidList[ret];
            return true;
        }
    }
}
