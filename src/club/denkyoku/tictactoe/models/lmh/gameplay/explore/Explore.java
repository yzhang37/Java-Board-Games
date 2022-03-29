package club.denkyoku.tictactoe.models.lmh.gameplay.explore;

import club.denkyoku.tictactoe.libraries.math.StdRandom;
import club.denkyoku.tictactoe.models.lmh.gameplay.mapitem.*;
import club.denkyoku.tictactoe.services.output.terminal.ConsoleHelper;
import club.denkyoku.tictactoe.services.textgraphics.array.MonoTextArray;
import club.denkyoku.tictactoe.services.textgraphics.array.TextArray;
import club.denkyoku.tictactoe.services.textgraphics.table.Box;
import club.denkyoku.tictactoe.services.textgraphics.table.BoxStyle;
import club.denkyoku.tictactoe.services.textgraphics.table.TextFixedSizeTable;
import club.denkyoku.tictactoe.services.textgraphics.text.MonoText;

import java.util.StringJoiner;


public class Explore {
    protected static int mapCellHeight = 2;
    protected static int mapCellWidth = 5;
    protected static int mapHeight = 8;
    protected static int mapWidth = 8;
    protected static int mapLeft = 2;
    protected static int mapTop = 3;

    //
    protected MapItem[][] map;
    protected int hero_top;
    protected int hero_left;
    protected MapItem heroes;
    protected Box heroBox;

    public Explore() {
        this.createNewMap();
        this.heroes = new Heroes(3);
        this.heroBox = new Box(mapCellHeight + 2, mapCellWidth + 2, BoxStyle.Engraved);
    }

    public void createNewMap() {
        this.map = new MapItem[mapHeight][mapWidth];
        this.hero_top = 0;
        this.hero_left = 0;
        // Randomly generated maps
        // Randomly generated, with a 25% chance of not passing
        for (int i = 0; i < mapHeight; ++i) {
            for (int j = 0; j < mapWidth; ++j) {
                if (StdRandom.bernoulli(0.25)) {
                    this.map[i][j] = new Barrier();
                } else {
                    this.map[i][j] = new Wild();
                }
            }
        }
        // The top left corner and the two surrounding must be Wild
        this.map[0][1] = new Wild();
        this.map[1][0] = new Wild();

        // Then we generate the store and 40% will become a store
        for (int i = 0; i < mapHeight; ++i) {
            for (int j = 0; j < mapWidth; ++j) {
                if (this.map[i][j].getType() == mapItemEnum.Wild) {
                    if (StdRandom.bernoulli(0.4)) {
                        this.map[i][j] = new Market();
                    }
                }
            }
        }
        this.map[0][0] = new Wild();
    }

    // Calculate coordinates for on-screen map drawing.
    private int clx(int h) {
        return mapTop + h * (mapCellHeight + 1) + 1;
    }
    private int cly(int w) {
        return mapLeft + w * (mapCellWidth + 1) + 1;
    }

    // Calculate coordinates for map border drawing.
    private int tbx(int h) {
        return mapTop + h * (mapCellHeight + 1);
    }
    private int tby(int w) {
        return mapLeft + w * (mapCellWidth + 1);
    }

    protected void drawFrame() {
        // Create a screen
        TextArray fullScreen = new MonoTextArray(36, 80);

        // Create screen edge borders
        var border = new Box(36, 80, BoxStyle.Default);
        fullScreen.insertArray(0, 0, border.toArray());

        fullScreen.insertArray(1, mapLeft, new MonoText("Explore the map").toArray());

        // Create a map area
        var mapArea = new TextFixedSizeTable(mapHeight, mapWidth, mapCellHeight, mapCellWidth, BoxStyle.Emboss);
        fullScreen.insertArray(mapTop, mapLeft, mapArea.toArray());

        // Preliminary mapping of the map area
        for (int i = 0; i < mapHeight; ++i) {
            for (int j = 0; j < mapWidth; ++j) {
                fullScreen.insertArray(clx(i), cly(j), this.map[i][j].toArray());
            }
        }

        // Draw the box where the player is locked in position
        fullScreen.insertArray(clx(hero_top), cly(hero_left), this.heroes.toArray());
        fullScreen.insertArray(tbx(hero_top), tby(hero_left), this.heroBox.toArray());

        // Print all key prompt messages
        StringJoiner sJoiner = new StringJoiner("\n");
        sJoiner.add("asdw/←↓→↑: to move");
        sJoiner.add("[i]: show info");
        sJoiner.add("[e]: open inventory");
        sJoiner.add("esc: pause / exit");

        fullScreen.insertArray(clx(mapHeight), mapLeft, new MonoText(sJoiner.toString()).toArray());

        ConsoleHelper.printScreen(fullScreen.toScreen());
    }

    /**
     * Start the map explore process
     */
    public void start() {
        ExploreDataSync dataSync = new ExploreDataSync();
        ExploreKeyHandler keyHandler = new ExploreKeyHandler(dataSync);
        boolean redraw = true;

        while (true) {
            if (redraw) {
                this.drawFrame();
                redraw = false;
            }

            keyHandler.run();

            if (dataSync.doMoveUp) {
                redraw = moveUp();
            } else if (dataSync.doMoveDown) {
                redraw = moveDown();
            } else if (dataSync.doMoveLeft) {
                redraw = moveLeft();
            } else if (dataSync.doMoveRight) {
                redraw = moveRight();
            } else if (dataSync.doEsc) {
                return;
            }

            dataSync.reset();
        }
    }

    /**
     * Attempts to move up and returns whether the move was successful.
     * @return Whether the move was successful.
     */
    protected boolean moveUp() {
        if (hero_top > 0) {
            if (this.map[hero_top - 1][hero_left].getType() == mapItemEnum.Barrier) {
                return false;
            }
            this.hero_top--;
            return true;
        }
        return false;
    }

    /**
     * Attempts to move down and returns whether the move was successful.
     * @return Whether the move was successful.
     */
    protected boolean moveDown() {
        if (hero_top < mapHeight - 1) {
            if (this.map[hero_top + 1][hero_left].getType() == mapItemEnum.Barrier) {
                return false;
            }
            this.hero_top++;
            return true;
        }
        return false;
    }

    /**
     * Attempts to move left and returns whether the move was successful.
     * @return Whether the move was successful.
     */
    protected boolean moveLeft() {
        if (hero_left > 0) {
            if (this.map[hero_top][hero_left - 1].getType() == mapItemEnum.Barrier) {
                return false;
            }
            this.hero_left--;
            return true;
        }
        return false;
    }

    /**
     * Attempts to move right and returns whether the move was successful.
     * @return Whether the move was successful.
     */
    protected boolean moveRight() {
        if (hero_left < mapWidth - 1) {
            if (this.map[hero_top][hero_left + 1].getType() == mapItemEnum.Barrier) {
                return false;
            }
            this.hero_left++;
            return true;
        }
        return false;
    }
}
