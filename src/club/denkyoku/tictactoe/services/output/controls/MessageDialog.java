package club.denkyoku.tictactoe.services.output.controls;

import club.denkyoku.tictactoe.services.input.DataSync;
import club.denkyoku.tictactoe.services.input.KeyHandler;
import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class MessageDialog {
    public static class Button {
        private final String title;
        private final char accessKey;

        /**
         * Create a new Button, with optional access key.
         * @param title The caption of the button.
         * @param accessKey The access key of the button.
         *                 If <code>'\0'</code>, the button has no access key.
         *                 Else, the MessageDialog will try to allocate
         *                 this button an access key.
         */
        public Button(String title, char accessKey) {
            this.title = title;
            if ('0' <= accessKey && accessKey <= '9' ||
                    'A' <= accessKey && accessKey <= 'Z') {
                this.accessKey = accessKey;
            } else if ('a' <= accessKey && accessKey <= 'z') {
                this.accessKey = (char) (accessKey - ('a' - 'A'));
            } else {
                this.accessKey = '\0';
            }
        }

        /**
         * Get the caption of the button.
         * @return The caption of the button.
         */
        public String getTitle() {
            return this.title;
        }

        /**
         * Get the access key of the button.
         * @return The access key of the button.
         */
        public char getAccessKey() {
            return this.accessKey;
        }
    }

    /**
     * Helper method to get the default OK and Cancel buttons.
     * @return An array of two buttons, OK and Cancel.
     *         Both buttons have no access key.
     *         You have to define the primary button and
     *         the cancel button yourself.
     */
    public static Button[] getOKCancel() {
        return new Button[]{
                new Button("OK", '\0'),
                new Button("Cancel", '\0')
        };
    }

    /**
     * Helper method to get the default Yes and No buttons.
     * @return An array of two buttons, Yes and No.
     *         Yes button has access key <code>'Y'</code>.
     *         No button has access key <code>'N'</code>.
     *         You have to define the primary button and
     *         the cancel button yourself.
     */
    public static Button[] getYesNo() {
        return new Button[]{
                new Button("Yes", 'Y'),
                new Button("No", 'N')
        };
    }

    /**
     * Helper method to get the default three
     * Yes, No and Cancel buttons.
     * @return An array of two buttons, Yes and No.
     *         Yes button has access key <code>'Y'</code>.
     *         No button has access key <code>'N'</code>.
     *         Cancel button don't have an access key.
     *         You have to define the primary button and
     *         the cancel button yourself.
     */
    public static Button[] getYesNoCancel() {
        return new Button[]{
                new Button("Yes", 'Y'),
                new Button("No", 'N'),
                new Button("Cancel", '\0')
        };
    }

    /**
     * Helper method to get the only one OK button.
     *
     * Pretty useful when you just want to show a message,
     * and let the user confirm it.
     * @return An array of one button, OK. No access key.
     */
    public static Button[] getOK() {
        return new Button[]{
                new Button("OK", '\0')
        };
    }

    /**
     * Helper method to show the only one OK button message.
     * @param message A list of strings, representing the message to show.
     * @return The index of the button pressed.
     */
    public static int show(String[] message) {
        return show(message, getOK(), 0, -1);
    }

    /**
     * The data synchronization between the Message Dialog and the key handler.
     */
    private static class ShowDataSync extends DataSync {
        public boolean redraw;
        public boolean keepRun;

        public boolean doDecrement;
        public boolean doIncrement;
        public boolean doExecute;
        public char accessKey;

        @Override
        public void reset() {
            this.redraw = false;
            this.keepRun = true;

            this.doDecrement = false;
            this.doIncrement = false;
            this.doExecute = false;
            this.accessKey = '\0';
        }
    }

    /**
     * The customized key handler for the Message Dialog.
     */
    private static class ShowDialogKeyHandler extends KeyHandler {
        final ShowDataSync dataSync;

        public ShowDialogKeyHandler(ShowDataSync showDataSync) {
            dataSync = showDataSync;
        }

        protected void onKeyEsc() {
            dataSync.keepRun = false;
        }

        @Override
        protected void onKeyEnter() {
            dataSync.doExecute = true;
        }

        @Override
        protected void onKeyUp() {
            dataSync.doDecrement = true;
        }

        @Override
        protected void onKeyDown() {
            dataSync.doIncrement = true;
        }

        @Override
        protected void onNormalKey(char key) {
            if ('a' <= key && key <= 'z') {
                key = (char) (key - ('a' - 'A'));
            }
            if ('0' <= key && key <= '9' || 'A' <= key && key <= 'Z') {
                dataSync.accessKey = key;
            }
        }
    }

    /**
     * Begin the Message Dialog session.
     *
     * The Message Dialog will preserve the current screen state,
     * and when user exit, it will restore the last screen state.
     * @param message A list of strings, representing the message to show.
     * @param buttons An array of <code>MessageDialog.Button</code>,
     *                representing the buttons to show.
     * @param defaultButton The index of the default button.
     *                      The user can press the Enter key to select this button.
     *                      If <code>-1</code>, then there's no default button.
     * @param cancelButton The index of the cancel button.
     *                     The user can press the Esc key to select this button.
     *                     If <code>-1</code>, then there's no cancel button.
     * @return The index of the button pressed.
     */
    public static int show(String[] message, Button[] buttons, int defaultButton, int cancelButton) {
        // If the button has an access key, index it once
        HashMap<Character, ArrayList<Integer>> accessKeyIndex = new HashMap<>();
        for (int i = 0; i < buttons.length; i++) {
            char key = buttons[i].getAccessKey();
            if (key != '\0') {
                if (!accessKeyIndex.containsKey(key)) {
                    accessKeyIndex.put(key, new ArrayList<>());
                }
                accessKeyIndex.get(key).add(i);
            }
        }

        String[] lastScreen = ConsoleHelper.GetLastScreen();
        int ret_value = -1;
        int currentButton = defaultButton;

        ShowDataSync dataSync = new ShowDataSync();
        // draw the first time
        dataSync.redraw = true;
        KeyHandler keyHandler = new ShowDialogKeyHandler(dataSync);

        while (dataSync.keepRun) {
            if (dataSync.redraw) {
                printDialog(message, buttons, currentButton, lastScreen);
            }

            dataSync.reset();
            keyHandler.run();

            if (!dataSync.keepRun && cancelButton >= 0) {
                ret_value = cancelButton;
                break;
            } else if (dataSync.doDecrement) {
                currentButton --;
                if (currentButton < 0) {
                    currentButton = buttons.length - 1;
                }
                dataSync.redraw = true;
            } else if (dataSync.doIncrement) {
                currentButton ++;
                if (currentButton >= buttons.length) {
                    currentButton = 0;
                }
                dataSync.redraw = true;
            } else if (dataSync.doExecute) {
                ret_value = currentButton;
                break;
            } else if (dataSync.accessKey != '\0') {
                char key = dataSync.accessKey;

                if (accessKeyIndex.containsKey(key)) {
                    ArrayList<Integer> indexList = accessKeyIndex.get(key);

                    // There happens to be a button, we just click it
                    if (indexList.size() == 1) {
                        ret_value = indexList.get(0);
                        break;
                    } else if (indexList.size() > 1) {

                        // If there are multiple buttons, we cycle through them
                        if (indexList.contains(currentButton)) {
                            int curIndex = indexList.indexOf(currentButton);
                            curIndex ++;
                            if (curIndex >= indexList.size()) {
                                curIndex = 0;
                            }
                            currentButton = indexList.get(curIndex);
                            dataSync.redraw = true;
                        } else {
                            currentButton = indexList.get(0);
                            dataSync.redraw = true;
                        }
                    }
                }
            }
        }
        keyHandler.exitInput();

        ConsoleHelper.printScreen(lastScreen);
        return ret_value;
    }

    /**
     * Functions for internal use
     *
     * Print the Message Dialog to the terminal.
     * @param message The message to be displayed.
     * @param buttons The buttons to be displayed.
     * @param currentButton The index of the current chosen button.
     * @param lastScreen The last screen before the dialog was displayed.
     *                   It's used to recovered to the last screen stack.
     */
    protected static void printDialog(String[] message, Button[] buttons, int currentButton, String[] lastScreen) {
        // TODO: I want to make the buttons with
        //       access key to be highlighted (or at least with an underline)

        // First get the width and height of the current window
        int height = ConsoleHelper.GetConsoleHeight(),
                width = ConsoleHelper.GetConsoleWidth();

        // Calculate the required height first
        int neededHeight = buttons.length + 1 + message.length;
        boolean showTopBottomBorder = false, showTopBottomMargin = false;

        // If adding a border can be displayed, increase the height of the border up and down
        if (neededHeight + 2 < height) {
            neededHeight += 2;
            showTopBottomBorder = true;
        }
        if (neededHeight + 2 < height) {
            neededHeight += 2;
            showTopBottomMargin = true;
        }

        // Get the widest of all texts
        int neededWidth = 0;
        boolean showLeftRightBorder = false, showLeftRightMargin = false;
        for (String s : message) {
            if (s.length() > neededWidth) {
                neededWidth = s.length();
            }
        }
        for (Button b : buttons) {
            int length = b.getTitle().length() + 2;
            if (length > neededWidth) {
                neededWidth = length;
            }
        }
        // If adding a border can be displayed, increase the border width on the left and right sides
        if (neededWidth + 2 < width) {
            neededWidth += 2;
            showLeftRightBorder = true;
        }
        if (neededWidth + 2 < width) {
            neededWidth += 2;
            showLeftRightMargin = true;
        }

        // Get the actual upper left corner coordinates of the Dialog
        int dialog_Y = Math.max(0, (width - neededWidth) / 2),
                dialog_X = Math.max(0, (height - neededHeight) / 2);

        // Get the previous screen first, then modify it.
        ArrayList<String> newScreen = new ArrayList<>();
        int lines = 0;
        while (lines < dialog_X) {
            if (lastScreen.length <= lines)
                newScreen.add("");
            else
                newScreen.add(lastScreen[lines]);
            lines++;
        }

        // replace text with window
        int dialogLines = 0;
        String leftTopCorner = "", rightTopCorner = "",
                leftBottomCorner = "", rightBottomCorner = "",
                leftMargin = "", rightMargin = "";

        if (showLeftRightBorder) {
            leftTopCorner = "┌";
            rightTopCorner = "┒";
            leftMargin = "│";
            rightMargin = "┃";
            leftBottomCorner = "┕";
            rightBottomCorner = "┛";
        }
        if (showLeftRightMargin) {
            leftTopCorner += "─";
            rightTopCorner = "─" + rightTopCorner;
            leftMargin += " ";
            rightMargin = " " + rightMargin;
            leftBottomCorner += "━";
            rightBottomCorner = "━" + rightBottomCorner;
        }
        if (dialog_Y > 0) {
            leftTopCorner = " ".repeat(dialog_Y) + leftTopCorner;
            leftMargin = " ".repeat(dialog_Y) + leftMargin;
            leftBottomCorner = " ".repeat(dialog_Y) + leftBottomCorner;
        }

        int curMessage = 0;
        int curButton = 0;
        while (dialogLines < neededHeight) {
            if (showTopBottomBorder && dialogLines == 0) {
                if (showLeftRightBorder && showLeftRightMargin) {
                    newScreen.add(leftTopCorner + "─".repeat(neededWidth - 4) + rightTopCorner);
                } else if (showLeftRightBorder) {
                    newScreen.add(leftTopCorner + "─".repeat(neededWidth - 2) + rightTopCorner);
                } else {
                    newScreen.add("─".repeat(neededWidth));
                }
            } else if (showTopBottomMargin && dialogLines == 1) {
                if (showLeftRightBorder && showLeftRightMargin) {
                    newScreen.add(leftMargin + " ".repeat(neededWidth - 4) + rightMargin);
                } else if (showLeftRightBorder) {
                    newScreen.add(leftMargin + " ".repeat(neededWidth - 2) + rightMargin);
                } else {
                    newScreen.add(" ".repeat(neededWidth));
                }
            } else if (showTopBottomMargin && dialogLines == neededHeight - 2) {
                if (showLeftRightBorder && showLeftRightMargin) {
                    newScreen.add(leftMargin + " ".repeat(neededWidth - 4) + rightMargin);
                } else if (showLeftRightBorder) {
                    newScreen.add(leftMargin + " ".repeat(neededWidth - 2) + rightMargin);
                } else {
                    newScreen.add(" ".repeat(neededWidth));
                }
            } else if (showTopBottomBorder && dialogLines == neededHeight - 1) {
                if (showLeftRightBorder && showLeftRightMargin) {
                    newScreen.add(leftBottomCorner + "━".repeat(neededWidth - 4) + rightBottomCorner);
                } else if (showLeftRightBorder) {
                    newScreen.add(leftBottomCorner + "━".repeat(neededWidth - 2) + rightBottomCorner);
                } else {
                    newScreen.add("━".repeat(neededWidth));
                }
            } else {
                if (curMessage < message.length) {
                    String s = message[curMessage];
                    if (showLeftRightBorder && showLeftRightMargin) {
                        newScreen.add(leftMargin + s + " ".repeat(Math.max(0, neededWidth - 4 - s.length())) + rightMargin);
                    } else if (showLeftRightBorder) {
                        newScreen.add(leftMargin + s + " ".repeat(Math.max(0, neededWidth - 2 - s.length())) + rightMargin);
                    } else {
                        newScreen.add(s);
                    }
                    curMessage++;
                } else if (curMessage == message.length) {
                    if (showLeftRightBorder && showLeftRightMargin) {
                        newScreen.add(leftMargin + "─".repeat(neededWidth - 4) + rightMargin);
                    } else if (showLeftRightBorder) {
                        newScreen.add(leftMargin + "─".repeat(neededWidth - 2) + rightMargin);
                    } else {
                        newScreen.add("─".repeat(neededWidth));
                    }
                    curMessage++;
                } else if (curButton < buttons.length) {
                    String prefix = "  ";
                    if (currentButton == curButton) {
                        prefix = "▶ ";
                    }
                    String s = buttons[curButton].getTitle();
                    if (showLeftRightBorder && showLeftRightMargin) {
                        newScreen.add(leftMargin + prefix + s + " ".repeat(Math.max(0, neededWidth - 2 - 4 - s.length())) + rightMargin);
                    } else if (showLeftRightBorder) {
                        newScreen.add(leftMargin + prefix + s + " ".repeat(Math.max(0, neededWidth - 2 - 2 - s.length())) + rightMargin);
                    } else {
                        newScreen.add(prefix + s);
                    }
                    curButton++;
                }
            }

            dialogLines++;
            lines++;
        }

        while (lines < lastScreen.length) {
            newScreen.add(lastScreen[lines]);
            lines++;
        }

        String[] newScreenArray = new String[newScreen.size()];
        newScreen.toArray(newScreenArray);
        ConsoleHelper.printScreen(newScreenArray);
    }

}
