package club.denkyoku.tictactoe.libraries.os;

public class Time {
    /**
     * The helper function for waiting some time.
     * @param milliseconds The time to wait.
     */
    public static void waitMilliseconds(int milliseconds) {
        try {
            java.lang.Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
