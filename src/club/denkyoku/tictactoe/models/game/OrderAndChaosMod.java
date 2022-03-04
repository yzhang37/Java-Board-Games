package club.denkyoku.tictactoe.models.game;

import club.denkyoku.tictactoe.models.gamemenu.OrderAndChaosMenu;

public class OrderAndChaosMod extends Mod {
    public OrderAndChaosMod() {
        super("Order and Chaos",
                "9c9b05cc-20bc-40f9-93dc-048bef65d51f");
    }

    @Override
    public void run() {
        OrderAndChaosMenu game = new OrderAndChaosMenu();
        game.start();
    }
}
