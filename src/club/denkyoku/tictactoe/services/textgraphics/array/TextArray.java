package club.denkyoku.tictactoe.services.textgraphics.array;

public abstract class TextArray implements ITextArrayable {
    // the scale of the text array.
    protected int width;
    protected int height;

    public TextArray(int height, int width) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Set the content of a point in a monotone text array
     * @param h x-coordinate
     * @param w y-coordinate
     * @param c char content
     */
    public abstract void setPointContent(int h, int w, char c);

    /**
     * Helper function to set a point with a String
     */
    public void setPointContent(int x, int y, String c) {
        setPointContent(x, y, c.charAt(0));
    }

    /**
     * Get the display content of a point
     * @param x x-coordinate
     * @param y y-coordinate
     * @return display content
     */
    public abstract char getPointContent(int x, int y);

    // Define a function that returns the
    // Screen content calculated by the array.
    public abstract String[] toScreen();

    // a shadow function which return itself
    public TextArray toArray() {
        return this;
    }

    /**
     * Given a horizontal and vertical axis,
     * embed another Text Array in the specified position
     *
     *  @param h the position of the array on the x-axis
     * @param w the position of the array on the y-axis
     * @param array the array to embed
     */
    public abstract void insertArray(int h, int w, TextArray array);
}
