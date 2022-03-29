package club.denkyoku.tictactoe.models.lmh.gameplay.mapitem;

import club.denkyoku.tictactoe.services.textgraphics.array.MonoTextArray;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;

public class Heroes extends MapItem {
    protected final int num;

    public Heroes(int num) {
        this.type = mapItemEnum.Other;
        this.num = num;
    }

    @Override
    public MonoTextArray toMonoArray() {
        MonoTextArray array = new MonoTextArray(2, 5);
        if (this.num >= 1) {
            array.setPointContent(1, 2, 'o');
        }
        if (this.num >= 2) {
            array.setPointContent(0, 4, 'o');
        }
        if (this.num >= 3) {
            array.setPointContent(0, 0, 'o');
        }
        return array;
    }

    @Override
    public TextArray toArray() {
        return this.toMonoArray();
    }
}
