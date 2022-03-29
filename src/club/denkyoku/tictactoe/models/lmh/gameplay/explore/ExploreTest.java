package club.denkyoku.tictactoe.models.lmh.gameplay.explore;

import org.junit.jupiter.api.Test;

class ExploreTest {

    @Test
    void drawFrame() {
        Explore explore = new Explore();
        explore.drawFrame();
    }

    @Test
    void start() {
        Explore explore = new Explore();
        explore.start();
    }
}