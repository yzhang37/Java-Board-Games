package club.denkyoku.TicTacToe;


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

    private static class MenuKeyHandler extends KeyHandler {
        MenuHandlerDataSync dataSync;

        public MenuKeyHandler(MenuHandlerDataSync sync) {
            this.dataSync = sync;
        }

        void onKeyEsc() {
            ConsoleHelper.bell();
            this.dataSync.keepRun = false;
        }

        void onKeyLeft() {
            this.onKeyEsc();
        }

        void onKeyEnter() {
            this.dataSync.doExecute = true;
        }

        void onKeyUp() {
            this.dataSync.doDecrement = true;
            ConsoleHelper.bell();
            this.dataSync.redraw = true;
        }

        void onKeyDown() {
            this.dataSync.doIncrement = true;
            ConsoleHelper.bell();
            this.dataSync.redraw = true;
        }
    }
}
