package club.denkyoku.TicTacToe;

import java.io.IOException;

import biz.source_code.utils.RawConsoleInput;


public class KeyHandler {
    /**
     * Read a raw input key from the console.
     * @return The raw input key's ASCII code (int).
     * value of <code>0</code> means nothing was pressed.
     */
    private int performGetKey() {
        try {
            return RawConsoleInput.read(true);
        } catch (IOException e) {}
        return 0;
    }

    public final int getKey() {
        int result = this.performGetKey();
        System.out.println(result);
        return result;
    }


    void onKeyEsc() {}
    void onKeyF1() {}
    void onKeyF2() {}
    void onKeyF3() {}
    void onKeyF4() {}
    void onKeyF5() {}
    void onKeyF6() {}
    void onKeyF7() {}
    void onKeyF8() {}
    void onKeyF9() {}
    void onKeyF10() {}
    void onKeyF11() {}
    void onKeyF12() {}
    void onKeyEnter() {}
    void onKeyUp() {}
    void onKeyDown() {}
    void onKeyLeft() {}
    void onKeyRight() {}

}

// TODO: isKeyEnter
// TODO: isKeyEsc
// TODO: isKeyUp
// TODO: isKeyDown
// TODO: isKeyLeft
// TODO: isKeyRight
