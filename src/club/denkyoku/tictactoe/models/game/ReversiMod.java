package club.denkyoku.tictactoe.models.game;

import club.denkyoku.tictactoe.models.gamemenu.ReversiMenu;

public class ReversiMod extends Mod {
    public ReversiMod() {
        super("Reversi",
                "45213d75-103f-46d2-ad44-a06ff2793521");
    }

    @Override
    public void run() {
        (new ReversiMenu()).start();
    }
}
