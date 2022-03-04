package club.denkyoku.TicTacToe.Models.Game;

import club.denkyoku.TicTacToe.Models.GameMenu.TicTacToeMenu;

public class OrderAndChaosMod extends Mod {
    public OrderAndChaosMod() {
        super("Order and Chaos",
                "9c9b05cc-20bc-40f9-93dc-048bef65d51f");
    }

    @Override
    public void Run() {
        TicTacToeMenu game = new TicTacToeMenu();
        game.start();
    }
}
