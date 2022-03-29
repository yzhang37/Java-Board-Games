package club.denkyoku.tictactoe.models.lmh.gameplay.explore;

import club.denkyoku.tictactoe.services.input.KeyHandler;

public class ExploreKeyHandler extends KeyHandler {
    protected ExploreDataSync dataSync;

    public ExploreKeyHandler(ExploreDataSync dataSync) {
        super();
        this.dataSync = dataSync;
    }

    @Override
    public void onKeyUp() {
        dataSync.doMoveUp = true;
    }

    @Override
    public void onKeyDown() {
        dataSync.doMoveDown = true;
    }

    @Override
    public void onKeyLeft() {
        dataSync.doMoveLeft = true;
    }

    @Override
    public void onKeyRight() {
        dataSync.doMoveRight = true;
    }

    @Override
    public void onKeyEsc() {
        dataSync.doEsc = true;
    }

    @Override
    public void onNormalKey(char charCode) {
        charCode = Character.toLowerCase(charCode);
        if (charCode == 'a') {
            dataSync.doMoveLeft = true;
        } else if (charCode == 's') {
            dataSync.doMoveDown = true;
        } else if (charCode == 'd') {
            dataSync.doMoveRight = true;
        } else if (charCode == 'w') {
            dataSync.doMoveUp = true;
        } else if (charCode == 'e') {
            dataSync.doOpenInventory = true;
        } else if (charCode == 'i') {
            dataSync.doShowInfo = true;
        }
    }
}
