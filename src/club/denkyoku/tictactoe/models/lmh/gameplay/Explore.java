package club.denkyoku.tictactoe.models.lmh.gameplay;

import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;
import club.denkyoku.tictactoe.services.textgraphics.array.MonoTextArray;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;
import club.denkyoku.tictactoe.services.textgraphics.table.Box;
import club.denkyoku.tictactoe.services.textgraphics.table.BoxStyle;
import club.denkyoku.tictactoe.services.textgraphics.table.TextFixedSizeTable;


public class Explore {
    protected static int mapCellHeight = 2;
    protected static int mapCellWidth = 5;
    protected static int mapHeight = 8;
    protected static int mapWidth = 8;

    protected MapItem[][] map;

    public Explore() {
        this.createNewMap();
    }

    public void createNewMap() {
        this.map = new MapItem[mapHeight][mapWidth];
    }

    public void drawFrame() {
        // 创建一个屏幕
        TextArray fullScreen = new MonoTextArray(36, 80);

        // 创建屏幕边缘边框
        var border = new Box(36, 80, BoxStyle.Default);
        fullScreen.insertArray(0, 0, border.toMonoArray());

        // 创建一个地图区域
        var mapArea = new TextFixedSizeTable(mapHeight, mapWidth, mapCellHeight, mapCellWidth, BoxStyle.Emboss);
        fullScreen.insertArray(3, 3, mapArea.toMonoArray());

        ConsoleHelper.printScreen(fullScreen.toScreen());
    }
}
