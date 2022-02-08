package club.denkyoku.TicTacToe;


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
        // first we clear the screen
        ConsoleHelper.CleanConsole();

        this.printMenu();
        // wait for key press:

        return -1;
    }
}
