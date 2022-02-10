package club.denkyoku.TicTacToe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MessageDialog {
    public static class Button {
        private final String title;
        private final char accessKey;

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

        public String getTitle() {
            return this.title;
        }

        public char getAccessKey() {
            return this.accessKey;
        }
    }

    public static Button[] getOKCancel() {
        return new Button[]{
                new Button("OK", '\0'),
                new Button("Cancel", '\0')
        };
    }

    public static Button[] getYesNo() {
        return new Button[]{
                new Button("Yes", 'Y'),
                new Button("No", 'N')
        };
    }

    public static Button[] getYesNoCancel() {
        return new Button[]{
                new Button("Yes", 'Y'),
                new Button("No", 'N'),
                new Button("Cancel", '\0')
        };
    }

    public static Button[] getOK() {
        return new Button[]{
                new Button("OK", '\0')
        };
    }

    public static int show(String[] message) {
        return show(message, getOK(), 0, -1);
    }

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

        boolean redraw = true;
        int currentButton = defaultButton;

        char[] buffer = new char[10];

        int retvalue = -1;
        while (true) {
            if (redraw) {
                printDialog(message, buttons, currentButton, lastScreen);
                redraw = false;
            }
            Arrays.fill(buffer, '\0');
            KeyHandler.getKey(buffer);

            if (cancelButton >= 0 && KeyHandler.isEsc(buffer)) {
                retvalue = cancelButton;
                break;
            } else if (KeyHandler.isEnter(buffer)) {
                retvalue = currentButton;
                break;
            } else if (KeyHandler.isKeyUp(buffer)) {
                currentButton --;
                if (currentButton < 0) {
                    currentButton = buttons.length - 1;
                }
                redraw = true;
            } else if (KeyHandler.isKeyDown(buffer)) {
                currentButton ++;
                if (currentButton >= buttons.length) {
                    currentButton = 0;
                }
                redraw = true;
            }
            // test access key
            if (buffer.length > 2 && buffer[1] == '\0') {
                char key = buffer[0];
                if ('a' <= key && key <= 'z') {
                    key = (char) (key - ('a' - 'A'));
                }
                if (accessKeyIndex.containsKey(key)) {
                    ArrayList<Integer> indexList = accessKeyIndex.get(key);
                    // There happens to be a button, we just click it
                    if (indexList.size() == 1) {
                        retvalue = indexList.get(0);
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
                            redraw = true;
                        } else {
                            currentButton = indexList.get(0);
                            redraw = true;
                        }
                    }
                }
            }
        }
        ConsoleHelper.printScreen(lastScreen);
        return retvalue;
    }

    protected static void printDialog(String[] message, Button[] buttons, int currentButton, String[] lastScreen) {
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
