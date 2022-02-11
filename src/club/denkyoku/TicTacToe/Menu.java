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
        String[] menuScreen = new String[this.menuItems.length + 2];
        menuScreen[0] = this.header_text;
        for (int i = 0; i < this.menuItems.length; i++) {
            if (i == this.cur_position) {
                menuScreen[i + 1] = "▶ " + this.menuItems[i].toUpperCase();
            } else {
                menuScreen[i + 1] = "  " + this.menuItems[i];
            }
        }
        menuScreen[menuScreen.length - 1] = this.footer_text;
        ConsoleHelper.printScreen(menuScreen);
    }

    public int start() {
        char[] buffer = new char[10];
        boolean redraw = true;

        while (true) {
            if (redraw) {
                this.printMenu();
                redraw = false;
            }

            Arrays.fill(buffer, '\0');
            KeyHandler.getKey(buffer);

//            DebugHelper.viewKeyBuffer(buffer);

            if (KeyHandler.isEsc(buffer) || KeyHandler.isKeyLeft(buffer)) {
                ConsoleHelper.bell();
                return -1;
            } else if (KeyHandler.isEnter(buffer)) {
                return this.cur_position;
            } else if (KeyHandler.isKeyUp(buffer)) {
                this.decrementPosition();
                ConsoleHelper.bell();
                redraw = true;
            } else if (KeyHandler.isKeyDown(buffer)) {
                this.incrementPosition();
                ConsoleHelper.bell();
                redraw = true;
            }
        }
    }
}
