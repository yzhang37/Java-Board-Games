package club.denkyoku.tictactoe.libraries.os;

import java.io.IOException;

public class SttyHelper {
    private static final String STTY = "stty";
    private static final String SHELL = "/bin/sh";

    /** enables echoing characters as they are typed, if this system supports STTY. */
    static public void enableEcho() {
        SttyHelper.execute("echo");
    }

    /** disables echoing characters as they are typed, if this system supports STTY. */
    static public void disableEcho() {
        SttyHelper.execute("-echo");
    }

    /** Configures the console to buffer by character. */
    static public void bufferByCharacter() {
        SttyHelper.execute("-icanon min 1");
    }

    /** Executes Stty command with given argument string. */
    static public void execute(String args) throws UnsupportedOperationException {
        try {
            String command = String.format("%s %s < /dev/tty", STTY, args);
            Process process = Runtime.getRuntime().exec(
                    new String[]{SHELL, "-c", command}, null, null);
            int ret = process.waitFor();
            if (ret != 0) {
//                throw new UnsupportedOperationException(
//                        String.format("Calling Stty failed\nCalled: %s\nReturned: %d", command, ret));
            }
        } catch (IOException | InterruptedException e) {}
    }
}
