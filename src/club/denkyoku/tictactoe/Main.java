package club.denkyoku.tictactoe;

import club.denkyoku.tictactoe.models.Config;
import club.denkyoku.tictactoe.models.game.Mod;


public class Main {

    public static void main(String[] args) {
        while (!Config.doExitProgram) {
            // load the current Mod
            Mod mod = Config.modUUIDMap.get(Config.currentModUUID);
            mod.run();
        }
    }
}
