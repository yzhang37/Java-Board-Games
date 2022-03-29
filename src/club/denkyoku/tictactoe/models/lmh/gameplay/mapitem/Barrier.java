package club.denkyoku.tictactoe.models.lmh.gameplay.mapitem;

import club.denkyoku.tictactoe.services.textgraphics.array.MonoTextArray;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;

public class Barrier extends MapItem {
    protected static String[] draw = new String[] {
      "^^^^^",
      "^^^^^"
    };

    public Barrier() {
        this.type = mapItemEnum.Barrier;
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
