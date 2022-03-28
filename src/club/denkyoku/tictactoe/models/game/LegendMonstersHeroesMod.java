package club.denkyoku.tictactoe.models.game;

import club.denkyoku.tictactoe.models.gamemenu.LegendMonstersHeroesMenu;

public class LegendMonstersHeroesMod extends Mod {
    public LegendMonstersHeroesMod() {
        super("Legend: Monsters & Heroes",
                "ec29bf1b-34b4-4e62-8d0c-9ac1578c10c5");
    }

    @Override
    public void run() {
        (new LegendMonstersHeroesMenu()).start();
    }
}
