package club.denkyoku.tictactoe.services.textgraphics.text;

import club.denkyoku.tictactoe.services.textgraphics.array.IMonoTextArrayable;
import club.denkyoku.tictactoe.services.textgraphics.array.ITextArrayable;
import club.denkyoku.tictactoe.services.textgraphics.array.MonoTextArray;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;

/**
 * Creates a monochrome text output.
 * Where all control characters are filtered out except for \n.
 */
public class MonoText implements ITextArrayable, IMonoTextArrayable {
    protected String[] splitText;
    protected int maxWidth;

    public MonoText(String text) {
        this.splitText = text.split("\n");

        // Filter all invisible characters
        this.maxWidth = 0;
        for (int i = 0; i < this.splitText.length; i++) {
            this.splitText[i] = this.splitText[i].replaceAll("[\u0000-\u001F]", "");
            this.maxWidth = Math.max(this.maxWidth, this.splitText[i].length());
        }
    }

    @Override
    public MonoTextArray toMonoArray() {
        MonoTextArray array = new MonoTextArray(this.splitText.length, this.maxWidth);
        for (int i = 0; i < this.splitText.length; i++) {
            for (int j = 0; j < this.splitText[i].length(); j++) {
                array.setPointContent(i, j, this.splitText[i].charAt(j));
            }
        }
        return array;
    }

    @Override
    public TextArray toArray() {
        return this.toMonoArray();
    }
}
