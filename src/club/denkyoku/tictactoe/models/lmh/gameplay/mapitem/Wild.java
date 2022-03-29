package club.denkyoku.tictactoe.models.lmh.gameplay.mapitem;

import club.denkyoku.tictactoe.services.textgraphics.array.MonoTextArray;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;

public class Wild extends MapItem {
    protected static String[] draw = new String[] {
            " ~  ~",
            "~  ~ "
    };

    public Wild() {
        this.type = mapItemEnum.Wild;
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
