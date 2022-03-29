package club.denkyoku.tictactoe.models.lmh.gameplay.mapitem;

import club.denkyoku.tictactoe.services.textgraphics.array.MonoTextArray;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;

public class Market extends MapItem {
    protected static String[] draw = new String[] {
            "/'''\\",
            "|_$_|"
    };

    public Market() {
        this.type = mapItemEnum.Market;
    }

    @Override
    public MonoTextArray toMonoArray() {
        return new MonoTextArray(draw);
    }

    @Override
    public TextArray toArray() {
        return this.toMonoArray();
    }
}
