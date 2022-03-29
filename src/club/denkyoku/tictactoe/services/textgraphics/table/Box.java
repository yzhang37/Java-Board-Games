package club.denkyoku.tictactoe.services.textgraphics.table;

import club.denkyoku.tictactoe.services.textgraphics.array.ITextArrayable;
import club.denkyoku.tictactoe.services.textgraphics.array.IMonoTextArrayable;
import club.denkyoku.tictactoe.services.textgraphics.array.MonoTextArray;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;

import java.util.Objects;

/**
 * Utility function to create a box shape.
 */
public class Box implements ITextArrayable, IMonoTextArrayable {
    protected int height;
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        if (height < 0) {
            throw new IllegalArgumentException("height must be positive");
        }
        this.height = height;
    }
    protected int width;
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        if (width < 0) {
            throw new IllegalArgumentException("width must be positive");
        }
        this.width = width;
    }
    public BoxStyle style;

    public Box(int height, int width, BoxStyle style) {
        setHeight(height);
        setWidth(width);
        this.style = Objects.requireNonNullElse(style, BoxStyle.Default);
    }

    @Override
    public MonoTextArray toMonoArray() {
        MonoTextArray array = new MonoTextArray(this.height, this.width);
        // Top left corner
        array.setPointContent(0, 0, BoxDrawingHelper.getLeftTop(this.style));
        // Lower left corner
        array.setPointContent(this.height - 1, 0, BoxDrawingHelper.getLeftBottom(this.style));
        // Lower right corner
        array.setPointContent(this.height - 1, this.width - 1, BoxDrawingHelper.getRightBottom(this.style));
        // Upper right corner
        array.setPointContent(0, this.width - 1, BoxDrawingHelper.getRightTop(this.style));
        // Up and down
        for (int i = 1; i < this.width - 1; i++) {
            array.setPointContent(0, i, BoxDrawingHelper.getTop(this.style));
            array.setPointContent(this.height - 1, i, BoxDrawingHelper.getBottom(this.style));
        }
        // left and right
        for (int i = 1; i < this.height - 1; i++) {
            array.setPointContent(i, 0, BoxDrawingHelper.getLeft(this.style));
            array.setPointContent(i, this.width - 1, BoxDrawingHelper.getRight(this.style));
        }
        return array;
    }

    @Override
    public TextArray toArray() {
        return this.toMonoArray();
    }
}
