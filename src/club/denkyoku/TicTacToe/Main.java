package club.denkyoku.TicTacToe;

import club.denkyoku.TicTacToe.Libraries.OS.SttyHelper;
import club.denkyoku.TicTacToe.Models.Config;
import club.denkyoku.TicTacToe.Models.Game.Mod;


public class Main {

    public static void main(String[] args) {
        while (!Config.doExitProgram) {
            // load the current Mod
            Mod mod = Config.modUUIDMap.get(Config.currentModUUID);
            mod.run();
        }

        // TODO: don't leave it here
        // enable echo before exit
        SttyHelper.enableEcho();
    }
}
