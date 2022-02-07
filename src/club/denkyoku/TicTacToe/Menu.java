package club.denkyoku.TicTacToe;

import org.jetbrains.annotations.Nullable;

public class Menu {
    private final String header_text, footer_text;
    private int cur_position = 0;
    private final String[] menuItems;

    public Menu(String[] menuItems, @Nullable String header, @Nullable String footer) {
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
        System.out.println(this.header_text);
        for (int i = 0; i < this.menuItems.length; i++) {
            if (i == this.cur_position) {
                System.out.println("~ " + this.menuItems[i].toUpperCase());
            } else {
                System.out.println("  " + this.menuItems[i]);
            }
        }
        System.out.println(this.footer_text);
    }

    public void start() {
        // first we clear the screen
        System.out.println("haha");
        ConsoleHelper.CleanConsole();
        this.printMenu();
    }
}
