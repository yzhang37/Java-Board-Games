package club.denkyoku.tictactoe.services.textgraphics.array;

/**
 * Create a monotone text array
 */
public class MonoTextArray extends TextArray implements IMonoTextArrayable {
    protected char[][] monoArray;

    /**
     * Create an empty Text Array based on size
     * @param height height of the array
     * @param width width of the array
     */
    public MonoTextArray(int height, int width) {
        super(height, width);
        this.monoArray = new char[height][width];
    }

    /**
     * Create a Text Array from an existing
     * Screen that matches its size
     * @param screens screen content
     */
    public MonoTextArray(String[] screens) {
        // Stupid Java that has to call the super
        // constructor on the first line. Silly language.
        super(0, 0);

        // Count the number of the widest rows
        int maxWidth = 0;
        for (String line : screens) {
            if (line.length() > maxWidth) {
                maxWidth = line.length();
            }
        }
        int height = screens.length;

        // refresh the size properties
        this.width = maxWidth;
        this.height = height;

        // allocate the array
        this.monoArray = new char[height][width];

        // Now we import the characters of the
        // text content into MonoArray.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < maxWidth &&
                    j < screens[i].length(); j++) {
                this.monoArray[i][j] = screens[i].charAt(j);
            }
        }
    }

    /**
     * Given a horizontal and vertical axis,
     * embed another Text Array in the specified position
     *
     * If the Array is placed beyond the bottom right
     * corner of the screen, it will be truncated.
     *  @param h the position of the array on the x-axis
     * @param w the position of the array on the y-axis
     * @param array the array to embed
     */
    public void insertArray(int h, int w, TextArray array) {
        // first compute the size of the target array
        int tgtHeight = array.getHeight();
        int tgtWidth = array.getWidth();

        for (int i = 0; i < tgtHeight; i++) {
            // compute the X, truncate if beyond the screen
            int newX = h + i;
            if (newX >= this.height) {
                break;
            }

            for (int j = 0; j < tgtWidth; j++) {
                // compute the Y, truncate if beyond the screen
                int newY = w + j;
                if (newY >= this.width) {
                    break;
                }
                this.monoArray[newX][newY] = array.getPointContent(i, j);
            }
        }
    }

    /**
     * Set the content of a point in a monotone text array
     * @param h x-coordinate
     * @param w y-coordinate
     * @param c char content
     */
    @Override
    public void setPointContent(int h, int w, char c) {
        this.monoArray[h][w] = c;
    }

    /**
     * Get the display content of a point
     * @param x x-coordinate
     * @param y y-coordinate
     * @return display content
     */
    @Override
    public char getPointContent(int x, int y) {
        return this.monoArray[x][y];
    }

    @Override
    public String[] toScreen() {
        // Iterate through each row of the monoArray in turn,
        // converting each row to a string
        //
        // and then return it as a series of arrays.
        String[] result = new String[this.height];
        for (int i = 0; i < this.height; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < this.width; j++) {
                sb.append(this.monoArray[i][j] >= 32 ? this.monoArray[i][j] : ' ');
            }
            result[i] = sb.toString();
        }
        return result;
    }

    @Override
    public MonoTextArray toMonoArray() {
        return this;
    }
}
