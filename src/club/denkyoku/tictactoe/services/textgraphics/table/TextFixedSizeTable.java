package club.denkyoku.tictactoe.services.textgraphics.table;

import java.util.Arrays;

public class TextFixedSizeTable extends TextTable {

    /**
     * Create a table with a fixed cell size
     * @param rowsNum number of rows
     * @param columnsNum number of columns
     * @param rowHeight height of a row
     * @param columnWidth width of a column
     * @param style style of the table
     */
    public TextFixedSizeTable(int rowsNum,
                              int columnsNum,
                              int rowHeight,
                              int columnWidth,
                              BoxStyle style) {
        super(null, null, style);
        int [] rowsHeight = new int[rowsNum];
        Arrays.fill(rowsHeight, rowHeight);
        int [] columnsWidth = new int[columnsNum];
        Arrays.fill(columnsWidth, columnWidth);
        setRowsHeight(rowsHeight);
        setColumnsWidth(columnsWidth);
    }
}
