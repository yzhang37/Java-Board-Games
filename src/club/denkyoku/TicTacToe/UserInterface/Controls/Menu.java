package club.denkyoku.TicTacToe.UserInterface.Controls;

import club.denkyoku.TicTacToe.ConsoleHelper;
import club.denkyoku.TicTacToe.KeyHandler;


public class Menu {
    protected String header_text, footer_text;
    protected int cur_position = 0;
    protected final String[] menuItems;

    /**
     * Create a convenient and practical simple menu control under Terminal.
     * @param menuItems: A List of menu item names
     * @param header: menu header text
     * @param footer: menu footer text
     */
    public Menu(String[] menuItems, String header, String footer) {
        this.header_text = (header != null) ? header + "\n" : "";
        this.footer_text = (footer != null) ? footer : "";
        this.menuItems = menuItems;
    }

    /**
     * If the menu has header text.
     * @return <code>true</code> if the menu has header text.
     */
    protected boolean getHasHeaderText() {
        return this.header_text.length() > 0;
    }

    /**
     * If the menu has footer text.
     * @return <code>true</code> if the menu has footer text.
     */
    protected boolean getHasFooterText() {
        return this.footer_text.length() > 0;
    }

    /**
     * Return the header text.
     * @return The content of header text.
     */
    public String getHeaderText() {
        return this.header_text;
    }

    /**
     * Set the header text.
     * @param headerText The content of header text.
     */
    public void setHeaderText(String headerText) {
        this.header_text = headerText;
    }

    /**
     * Return the footer text.
     * @return The content of footer text.
     */
    public String getFooterText() {
        return this.footer_text;
    }

    /**
     * Set the footer text.
     * @param footerText The content of footer text.
     */
    public void setFooterText(String footerText) {
        this.footer_text = footerText;
    }

    /**
     * Return the current selected item's index.
     * @return An integer starts from <code>0</code>,
     * representing the current selected item's index.
     */
    public int getCurrentPosition() {
        return this.cur_position;
    }

    /**
     * Set the current selected item's index.
     * @param position An integer starts from <code>0</code>,
     * the menu's current position will be changed.
     */
    public void setCurrentPosition(int position) {
        if (position < 0) {
            position = 0;
        } else if (position >= this.menuItems.length) {
            position = this.menuItems.length - 1;
        }
        this.cur_position = position;
    }

    /**
     * Simulate that the current position is incremented by 1.
     */
    public void incrementPosition() {
        this.cur_position++;
        this.cur_position %= this.menuItems.length;
    }

    /**
     * Simulate that the current position is decremented by 1.
     */
    public void decrementPosition() {
        if (this.cur_position == 0) {
            this.cur_position = this.menuItems.length;
        }
        this.cur_position--;
    }

    /**
     * Print the menu to the terminal.
     */
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

    /**
     * Begin the main routine of the menu.
     *
     * Now user can use [Up]/[Down] and [Enter] to
     * navigate the menu options.
     * @return The index of the selected menu item.
     * If the user press [Esc] to exit the menu,
     * it will return <code>-1</code>.
     */
    public int start() {
        this.printMenu();

        MenuHandlerDataSync dataSync = new MenuHandlerDataSync();
        KeyHandler keyHandler = new MenuKeyHandler(dataSync);

        while (dataSync.keepRun) {
            dataSync.reset();
            keyHandler.run();

            if (dataSync.doIncrement) {
                this.incrementPosition();
            } else if (dataSync.doDecrement) {
                this.decrementPosition();
            } else if (dataSync.doExecute) {
                return this.cur_position;
            }

            if (dataSync.redraw) {
                this.printMenu();
            }
        }

        keyHandler.exitInput();
        // the program has already exited
        return -1;
    }

    /**
     * The data synchronization between the menu and the key handler.
     */
    private static class MenuHandlerDataSync {
        public boolean redraw;
        public boolean keepRun;
        public boolean doIncrement;
        public boolean doDecrement;
        public boolean doExecute;

        public MenuHandlerDataSync() {
            this.reset();
        }

        public void reset() {
            this.redraw = false;
            this.keepRun = true;
            this.doIncrement = false;
            this.doDecrement = false;
            this.doExecute = false;
        }
    }

    /**
     * The customized key handler for the menu.
     */
    private static class MenuKeyHandler extends KeyHandler {
        MenuHandlerDataSync dataSync;

        public MenuKeyHandler(MenuHandlerDataSync sync) {
            this.dataSync = sync;
        }

        @Override
        protected void onKeyEsc() {
            ConsoleHelper.bell();
            this.dataSync.keepRun = false;
        }

        @Override
        protected void onKeyLeft() {
            this.onKeyEsc();
        }

        @Override
        protected void onKeyEnter() {
            this.dataSync.doExecute = true;
        }

        @Override
        protected void onKeyUp() {
            this.dataSync.doDecrement = true;
            ConsoleHelper.bell();
            this.dataSync.redraw = true;
        }

        @Override
        protected void onKeyDown() {
            this.dataSync.doIncrement = true;
            ConsoleHelper.bell();
            this.dataSync.redraw = true;
        }
    }
}
