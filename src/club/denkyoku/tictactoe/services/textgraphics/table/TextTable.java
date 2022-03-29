package club.denkyoku.tictactoe.services.textgraphics.table;

import club.denkyoku.tictactoe.services.textgraphics.array.IMonoTextArrayable;
import club.denkyoku.tictactoe.services.textgraphics.array.ITextArrayable;
import club.denkyoku.tictactoe.services.textgraphics.array.MonoTextArray;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;

import java.util.Objects;

/**
 *
 */
public class TextTable implements ITextArrayable, IMonoTextArrayable {
    protected int[] rowsHeight;
    public int[] getRowsHeight() {
        return rowsHeight;
    }
    protected int[] columnsWidth;
    public int[] getColumnsWidth() {
        return columnsWidth;
    }
    protected BoxStyle style;
    public BoxStyle getStyle() {
        return style;
    }
    public void setBoxStyle(BoxStyle style) {
        this.style = style;
    }

    public TextTable(int[] rowsHeight,
                     int[] columnsWidth,
                     BoxStyle style) {
        setRowsHeight(rowsHeight);
        setColumnsWidth(columnsWidth);
        this.style = Objects.requireNonNullElse(style, BoxStyle.Default);
    }

    public void setRowsHeight(int[] rowsHeight) {
        if (rowsHeight == null) {
            this.rowsHeight = new int[0];
        } else {
            for (int j : rowsHeight) {
                if (j < 1) {
                    throw new IllegalArgumentException("Row height must be positive");
                }
            }
            this.rowsHeight = rowsHeight;
        }
    }

    public void setColumnsWidth(int[] columnsWidth) {
        if (columnsWidth == null) {
            this.columnsWidth = new int[0];
        } else {
            for (int j : columnsWidth) {
                if (j < 1) {
                    throw new IllegalArgumentException("Column width must be positive");
                }
            }
            this.columnsWidth = columnsWidth;
        }
    }

    @Override
    public MonoTextArray toMonoArray() {
        if (this.rowsHeight.length == 0 || this.columnsWidth.length == 0) {
            return new MonoTextArray(0, 0);
        }

        // Create a helper box to generate the table
        Box box = new Box(1, 1, BoxStyle.Default);

        // Calculate the total width and height
        int totalHeight = 0;
        int totalWidth = 0;
        for (int height: this.rowsHeight) {
            totalHeight += height;
        }
        totalHeight += (this.rowsHeight.length + 1);
        for (int width: this.columnsWidth) {
            totalWidth += width;
        }
        totalWidth += (this.columnsWidth.length + 1);

        MonoTextArray result = new MonoTextArray(totalHeight, totalWidth);

        // Add the box line by line and place it in the correct position
        int currentLeft = 0, currentTop = 0;
        MonoTextArray tempArray = new MonoTextArray(0, 0);

        // Iterate through each row
        for (int h = 0; h < this.rowsHeight.length; h++) {
            int height = this.rowsHeight[h];

            box.setHeight(height + 2);

            // Iterate through each column
            for (int width: this.columnsWidth) {
                box.setWidth(width + 2);

                tempArray = box.toMonoArray();
                result.insertArray(currentTop, currentLeft, tempArray);

                if (currentLeft > 0 && currentTop > 0) {
                    // fix the inner cross
                    result.setPointContent(currentTop, currentLeft, '┼');
                } else if (currentLeft > 0) {
                    // Repair the upper left corner
                    result.setPointContent(currentTop, currentLeft, '┬');
                } else if (currentTop > 0) {
                    // Repair of the upper edge intersection
                    result.setPointContent(currentTop, currentLeft, '├');
                }

                // if the last line, repair the lower edge intersection
                if (h == this.rowsHeight.length - 1 && currentLeft > 0) {
                    result.setPointContent(currentTop + tempArray.getHeight() - 1,
                            currentLeft, '┴');
                }
                currentLeft += tempArray.getWidth() - 1;

                // fix the right edge intersection
                if (h > 0) {
                    result.setPointContent(currentTop, currentLeft, '┤');
                }
            }
            currentTop += tempArray.getHeight() - 1;

            // Horizontal coordinate reset to 0
            currentLeft = 0;
        }

        // After drawing the table, we reapply the style to the table
        if (this.style == BoxStyle.Emboss) {
            char []cc = "┐┒│┃┘┛┤┨┴┷└┕─━".toCharArray();
            int i, j;
            j = result.getWidth() - 1;
            for (i = 0; i < result.getHeight(); i++) {
                repairMonoHelper(cc, i, j, result);
            }
            i = result.getHeight() - 1;
            for (j = 0; j < result.getWidth() - 1; j++) {
                repairMonoHelper(cc, i, j, result);
            }
        } else if (this.style == BoxStyle.Engraved) {
            char []cc = "┌┏─━┐┑┬┯│┃├┠└┖".toCharArray();
            int i, j;
            j = 0;
            for (i = 0; i < result.getHeight(); i++) {
                repairMonoHelper(cc, i, j, result);
            }
            i = 0;
            for (j = 1; j < result.getWidth(); j++) {
                repairMonoHelper(cc, i, j, result);
            }
        }

        return result;
    }

    private void repairMonoHelper(
            char[] cc, int i, int j, MonoTextArray result
    ) {
        char c = result.getPointContent(i, j);
        for (int x = 0; x < cc.length; x += 2) {
            if (c == cc[x]) {
                result.setPointContent(i, j, cc[x + 1]);
                break;
            }
        }
    }

    @Override
    public TextArray toArray() {
        return this.toMonoArray();
    }
}
