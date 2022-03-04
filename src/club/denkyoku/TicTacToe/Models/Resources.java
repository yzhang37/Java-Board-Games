package club.denkyoku.TicTacToe.Models;

public class Resources {
    // randomSymbol which can be used on Players >= 3
    protected static char[] RandomSymbol = new char[]{
            '◎', '●', '◇', '◆', '□', '■', '♧', '♣', '♤', '♠', '♡', '♥', '♢', '♦', '♪', '♫', '$', '·', '∞'};
    protected static char[] DefaultSymbol = new char[] {'○', '×'};
    protected static char[] AllSymbol = new char[RandomSymbol.length + 2];

    static {
        System.arraycopy(DefaultSymbol, 0, AllSymbol, 0, DefaultSymbol.length);
        System.arraycopy(RandomSymbol, 0, AllSymbol, DefaultSymbol.length, RandomSymbol.length);
    }

    /**
     * Get Random Symbols.
     * @return Random Symbols.
     */
    public static char getRandomSymbol(int index) {
        if (index < 0 || index >= RandomSymbol.length) {
            throw new IndexOutOfBoundsException("index must be between 0 and " + RandomSymbol.length);
        }
        return RandomSymbol[index];
    }

    /**
     * Get Random Symbol's Length.
     * @return Random Symbol's Length.
     */
    public static int getRandomSymbolLength() {
        return RandomSymbol.length;
    }

    /**
     * Get All Symbols.
     * @return All Symbols.
     */
    public static char getAllSymbol(int index) {
        if (index < 0 || index >= AllSymbol.length) {
            throw new IndexOutOfBoundsException("index must be between 0 and " + AllSymbol.length);
        }
        return AllSymbol[index];
    }

    /**
     * Get All Symbols' Length.
     * @return All Symbols' Length.
     */
    public static int getAllSymbolLength() {
        return AllSymbol.length;
    }
}
