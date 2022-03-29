package club.denkyoku.tictactoe.models.lmh.gameplay.explore;

import club.denkyoku.tictactoe.services.input.DataSync;

public class ExploreDataSync extends DataSync {

    public boolean doMoveUp;
    public boolean doMoveDown;
    public boolean doMoveLeft;
    public boolean doMoveRight;
    public boolean doOpenInventory;
    public boolean doShowInfo;
    public boolean doEsc;

    public ExploreDataSync() {
        super();
        reset();
    }

    @Override
    public void reset() {
        doMoveUp = false;
        doMoveDown = false;
        doMoveLeft = false;
        doMoveRight = false;
        doEsc = false;
        doShowInfo = false;
        doOpenInventory = false;
    }
}
