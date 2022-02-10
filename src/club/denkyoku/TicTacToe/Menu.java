package club.denkyoku.TicTacToe;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.Arrays;


public class Menu {
    private final String header_text, footer_text;
    private int cur_position = 0;
    private final String[] menuItems;

    public Menu(String[] menuItems, String header, String footer) {
        this.header_text = (header != null) ? header + "\n" : "";
        this.footer_text = (footer != null) ? footer : "";
        this.menuItems = menuItems;
    }

    protected boolean getHasHeaderText() {
        return this.header_text.length() > 0;
    }

    protected boolean getHasFooterText() {
        return this.footer_text.length() > 0;
    }

    public int getCurrentPosition() {
        return this.cur_position;
    }

    public int setCurrentPosition(int position) {
        if (position < 0) {
            position = 0;
        } else if (position >= this.menuItems.length) {
            position = this.menuItems.length - 1;
        }
        return this.cur_position = position;
    }

    protected void printMenu() {
        ConsoleHelper.println(this.header_text);
        for (int i = 0; i < this.menuItems.length; i++) {
            if (i == this.cur_position) {
                ConsoleHelper.println("▶ " + this.menuItems[i]);
            } else {
                ConsoleHelper.println("  " + this.menuItems[i]);
            }
        }
        ConsoleHelper.println(this.footer_text);
    }

    public int start() {
        ConsoleHelper.CleanConsole();
        this.printMenu();

        SttyHelper.disableEcho();
        SttyHelper.bufferByCharacter();

        int i = 0;
        String line = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] buffer = new char[10];

        while (i <= 10000){
            try {
                br.read(buffer);
            } catch (IOException e) {}
            System.out.print("\"\"");
            for (int c : buffer) {
                if (c != '\0') {
                    System.out.print(c);
                } else
                    break;
            }
            System.out.print("\"\"");

            Arrays.fill(buffer, '\0');
            ++i;
        }

        return -1;
    }
}
