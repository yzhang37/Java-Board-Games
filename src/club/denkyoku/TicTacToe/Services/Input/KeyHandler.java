package club.denkyoku.TicTacToe.Services.Input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import club.denkyoku.TicTacToe.Libraries.OS.RawConsoleInput;
import club.denkyoku.TicTacToe.Services.Debug.DebugHelper;
import club.denkyoku.TicTacToe.Libraries.OS.SttyHelper;


public abstract class KeyHandler {
    // The character array used for reading stream.
    public static char[] bufferUnix = new char[10];
    // the buffer reader used on Linux and Mac
    public static BufferedReader brUnix = new BufferedReader(new InputStreamReader(System.in));

    /**
     * The raw function for reading a key from
     * the console on Windows.
     * @param wait: type of <code>boolean</code>,
     *            if <code>true</code>,
     *            the function will wait until a key is pressed.
     * @return The raw input key's ASCII code (int).
     */
    private int rawGetKeyWindows(boolean wait) {
        try {
            return RawConsoleInput.read(wait);
        } catch (IOException e) {}
        return 0;
    }

    /**
     * The raw function for reading a key from
     * the console on Unix (Mac)/Linux.
     */
    private void rawReadKeyBuffer() {
        Arrays.fill(KeyHandler.bufferUnix, '\0');
        try {
            brUnix.read(KeyHandler.bufferUnix);
        } catch (IOException e) {}
    }

    /**
     * Begin one Raw key input routine.
     * The function will wait until a key is pressed. And
     * it will automatically call the corresponding callback.
     */
    public final void run() {
        if (RawConsoleInput.getIsWindows()) {
            int key = this.rawGetKeyWindows(true);

            if (key == 57416)
                this.onKeyUp();
            else if (key == 57424)
                this.onKeyDown();
            else if (key == 57421)
                this.onKeyRight();
            else if (key == 57419)
                this.onKeyLeft();
            else if (key == 57403)
                this.onKeyF1();
            else if (key == 57404)
                this.onKeyF2();
            else if (key == 57405)
                this.onKeyF3();
            else if (key == 57406)
                this.onKeyF4();
            else if (key == 57407)
                this.onKeyF5();
            else if (key == 57408)
                this.onKeyF6();
            else if (key == 57409)
                this.onKeyF7();
            else if (key == 57410)
                this.onKeyF8();
            else if (key == 57411)
                this.onKeyF9();
            else if (key == 57412)
                this.onKeyF10();
            else if (key == 57477)
                this.onKeyF11();
            else if (key == 57478)
                this.onKeyF12();
            else if (key == 57415)
                this.onKeyHome();
            else if (key == 57423)
                this.onKeyEnd();
            else if (key == 57417)
                this.onKeyPgUp();
            else if (key == 57425)
                this.onKeyPgDown();
            else if (key == 57426)
                this.onKeyInsert();
            else if (key == 57427)
                this.onKeyDelete();
            else if (key == 27)
                this.onKeyEsc();
            else if (key == 13)
                this.onKeyEnter();
            else if (key == 8)
                this.onKeyBackspace();
            else if (key == 9)
                this.onKeyTab();
            else if (32 <= key && key <= 126)
                this.onNormalKey((char) key);
            else
                System.out.println(key);
        } else {
            // begin the unix raw input mode
            SttyHelper.disableEcho();
            SttyHelper.bufferByCharacter();

            // make call to read key stream
            this.rawReadKeyBuffer();

            var bf = KeyHandler.bufferUnix;
            if (bf[0] == 27 && bf[1] == 91 && bf[2] == 65 && bf[3] == 0)
                this.onKeyUp();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 66 && bf[3] == 0)
                this.onKeyDown();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 67 && bf[3] == 0)
                this.onKeyRight();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 68 && bf[3] == 0)
                this.onKeyLeft();
            else if (bf[0] == 27 && bf[1] == 79 && bf[2] == 80 && bf[3] == 0)
                this.onKeyF1();
            else if (bf[0] == 27 && bf[1] == 79 && bf[2] == 81 && bf[3] == 0)
                this.onKeyF2();
            else if (bf[0] == 27 && bf[1] == 79 && bf[2] == 82 && bf[3] == 0)
                this.onKeyF3();
            else if (bf[0] == 27 && bf[1] == 79 && bf[2] == 83 && bf[3] == 0)
                this.onKeyF4();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 49 && bf[3] == 53 && bf[4] == 126 && bf[5] == 0)
                this.onKeyF5();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 49 && bf[3] == 55 && bf[4] == 126 && bf[5] == 0)
                this.onKeyF6();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 49 && bf[3] == 56 && bf[4] == 126 && bf[5] == 0)
                this.onKeyF7();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 49 && bf[3] == 57 && bf[4] == 126 && bf[5] == 0)
                this.onKeyF8();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 50 && bf[3] == 48 && bf[4] == 126 && bf[5] == 0)
                this.onKeyF9();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 50 && bf[3] == 49 && bf[4] == 126 && bf[5] == 0)
                this.onKeyF10();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 50 && bf[3] == 51 && bf[4] == 126 && bf[5] == 0)
                this.onKeyF11();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 50 && bf[3] == 52 && bf[4] == 126 && bf[5] == 0)
                this.onKeyF12();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 51 && bf[3] == 49 && bf[4] == 126 && bf[5] == 0)
                this.onKeyHome();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 51 && bf[3] == 53 && bf[4] == 126 && bf[5] == 0)
                this.onKeyEnd();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 51 && bf[3] == 55 && bf[4] == 126 && bf[5] == 0)
                this.onKeyPgUp();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 51 && bf[3] == 56 && bf[4] == 126 && bf[5] == 0)
                this.onKeyPgDown();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 52 && bf[3] == 55 && bf[4] == 126 && bf[5] == 0)
                this.onKeyInsert();
            else if (bf[0] == 27 && bf[1] == 91 && bf[2] == 52 && bf[3] == 56 && bf[4] == 126 && bf[5] == 0)
                this.onKeyDelete();
            else if (bf[0] == 27 && bf[1] == 0)
                this.onKeyEsc();
            else if (bf[0] == 10 && bf[1] == 0)
                this.onKeyEnter();
            else if (bf[0] >= 32 && bf[0] <= 126 && bf[1] == 0)
                this.onNormalKey(bf[0]);
            else if (bf[0] == 127 && bf[1] == 0)
                this.onKeyBackspace();
            else if (bf[0] == 9 && bf[1] == 0)
                this.onKeyTab();
            else
                DebugHelper.viewKeyBuffer(bf);
        }
    }

    protected void onKeyEsc() {}
    protected void onKeyF1() {}
    protected void onKeyF2() {}
    protected void onKeyF3() {}
    protected void onKeyF4() {}
    protected void onKeyF5() {}
    protected void onKeyF6() {}
    protected void onKeyF7() {}
    protected void onKeyF8() {}
    protected void onKeyF9() {}
    protected void onKeyF10() {}
    protected void onKeyF11() {}
    protected void onKeyF12() {}
    protected void onKeyEnter() {}
    protected void onKeyUp() {}
    protected void onKeyDown() {}
    protected void onKeyLeft() {}
    protected void onKeyRight() {}
    protected void onKeyHome() {}
    protected void onKeyEnd() {}
    protected void onKeyPgUp() {}
    protected void onKeyPgDown() {}
    protected void onKeyInsert() {}
    protected void onKeyDelete() {}
    protected void onKeyBackspace() {}
    protected void onKeyTab() {}
    protected void onNormalKey(char key) {}

    /**
     * Exit the Raw key input status.
     */
    public void exitInput() {
        try {
            RawConsoleInput.resetConsoleMode();

            // Restore the original console mode
            SttyHelper.enableEcho();
        } catch (IOException e) {}
    }
}
