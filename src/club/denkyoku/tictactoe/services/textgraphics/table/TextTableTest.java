package club.denkyoku.tictactoe.services.textgraphics.table;

import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextTableTest {

    @Test
    void getMonoArray() {
        int[] widths = new int[]{3, 3, 3};
        int[] heights = new int[]{3, 3, 3};
        TextTable textTable = new TextTable(widths, heights, null);
        var monoArray = textTable.GetMonoArray();
        ConsoleHelper.printScreen(monoArray.toScreen());
    }
}