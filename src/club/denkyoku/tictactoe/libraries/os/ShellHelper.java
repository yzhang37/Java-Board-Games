package club.denkyoku.tictactoe.libraries.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellHelper {
    private static final String SHELL = "/bin/sh";

    public static class Result {
        public final String output;
        public final String error;
        public final int ret;

        public Result(String output, String error, int ret) {
            this.output = output;
            this.error = error;
            this.ret = ret;
        }
    }

    public static Result shell(String command) throws IOException {
        Runtime rt = Runtime.getRuntime();
        int ret = 1;

        Process proc = rt.exec(new String[]{SHELL, "-c", command});

        BufferedReader stdOutput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        try {
            ret = proc.waitFor();
        } catch (InterruptedException e) {}

        // Read the output from the command
        StringBuilder sbOutput = new StringBuilder();
        String s;
        while ((s = stdOutput.readLine()) != null) {
            if (sbOutput.length() > 0) {
                sbOutput.append("\n");
            }
            sbOutput.append(s);
        }

        // Read any errors from the attempted command
        StringBuilder sbError = new StringBuilder();
        while ((s = stdError.readLine()) != null) {
            if (sbError.length() > 0) {
                sbError.append("\n");
            }
            sbError.append(s);
        }

        return new Result(sbOutput.toString(), sbError.toString(), ret);
    }
}
