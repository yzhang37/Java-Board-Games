package club.denkyoku.tictactoe.models.lmh.gameplay.mapitem;

import club.denkyoku.tictactoe.services.textgraphics.array.IMonoTextArrayable;
import club.denkyoku.tictactoe.services.textgraphics.array.ITextArrayable;

public abstract class MapItem implements ITextArrayable, IMonoTextArrayable {
    protected mapItemEnum type;

    public mapItemEnum getType() {
        return type;
    }
}
