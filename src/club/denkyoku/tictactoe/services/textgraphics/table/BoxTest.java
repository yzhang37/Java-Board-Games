package club.denkyoku.tictactoe.services.textgraphics.table;

import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;

class BoxTest {
    @org.junit.jupiter.api.Test
    public void printTest() {
        TextArray a = new Box(5, 5, BoxStyle.Engraved).toArray();
        String[] screen = a.toScreen();
        ConsoleHelper.printScreen(screen);
    }
}