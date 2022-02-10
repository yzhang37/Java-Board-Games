package club.denkyoku.TicTacToe;


import java.util.Arrays;


public class Menu {
    protected String header_text, footer_text;
    protected int cur_position = 0;
    protected final String[] menuItems;

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

    public String getHeaderText() {
        return this.header_text;
    }

    public void setHeaderText(String headerText) {
        this.header_text = headerText;
    }

    public String getFooterText() {
        return this.footer_text;
    }

    public void setFooterText(String headerText) {
        this.footer_text = headerText;
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

    public void incrementPosition() {
        this.cur_position++;
        this.cur_position %= this.menuItems.length;
    }

    public void decrementPosition() {
        if (this.cur_position == 0) {
            this.cur_position = this.menuItems.length;
        }
        this.cur_position--;
    }

    protected void printMenu() {
        ConsoleHelper.println(this.header_text);
        for (int i = 0; i < this.menuItems.length; i++) {
            if (i == this.cur_position) {
                ConsoleHelper.println("▶ " + this.menuItems[i].toUpperCase());
            } else {
                ConsoleHelper.println("  " + this.menuItems[i]);
            }
        }
        ConsoleHelper.println(this.footer_text);
    }

    public int start() {
        char[] buffer = new char[10];
        boolean redraw = true;

        while (true) {
            if (redraw) {
                ConsoleHelper.CleanConsole();
                this.printMenu();
                redraw = false;
            }

            Arrays.fill(buffer, '\0');
            KeyHandler.getKey(buffer);

//            DebugHelper.viewKeyBuffer(buffer);

            if (KeyHandler.isEsc(buffer)) {
                ConsoleHelper.print('\7');
                return -1;
            } else if (KeyHandler.isEnter(buffer)) {
                return this.cur_position;
            } else if (KeyHandler.isKeyUp(buffer)) {
                this.decrementPosition();
                ConsoleHelper.print('\7');
                redraw = true;
            } else if (KeyHandler.isKeyDown(buffer)) {
                this.incrementPosition();
                ConsoleHelper.print('\7');
                redraw = true;
            }
        }
    }
}
