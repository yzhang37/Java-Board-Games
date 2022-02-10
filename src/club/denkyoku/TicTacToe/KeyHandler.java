package club.denkyoku.TicTacToe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyHandler {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void getKey(char []buffer) {
        try {
            br.read(buffer);
        } catch (IOException e) {}
    }

    public static boolean isKeyUp(char []buffer) {
        if (buffer.length >= 3) {
            return buffer[0] == 27 &&
                    buffer[1] == 91 &&
                    buffer[2] == 65;
        }
        return false;
    }

    public static boolean isKeyLeft(char []buffer) {
        if (buffer.length >= 3) {
            return buffer[0] == 27 &&
                    buffer[1] == 91 &&
                    buffer[2] == 68;
        }
        return false;
    }

    public static boolean isKeyDown(char []buffer) {
        if (buffer.length >= 3) {
            return buffer[0] == 27 &&
                    buffer[1] == 91 &&
                    buffer[2] == 66;
        }
        return false;
    }

    public static boolean isKeyRight(char []buffer) {
        if (buffer.length >= 3) {
            return buffer[0] == 27 &&
                    buffer[1] == 91 &&
                    buffer[2] == 67;
        }
        return false;
    }

    public static boolean isEsc(char []buffer) {
        if (buffer.length >= 2) {
            return buffer[0] == 27 &&
                    buffer[1] == 0;
        }
        return false;
    }

    public static boolean isEnter(char []buffer) {
        if (buffer.length >= 2) {
            return buffer[0] == 10 &&
                    buffer[1] == 0;
        }
        return false;
    }

}
