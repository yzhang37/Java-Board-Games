package club.denkyoku.TicTacToe.Models.Game;

import club.denkyoku.TicTacToe.Models.GameMenu.TicTacToeMenu;

public class TicTacToeMod extends Mod {
    public TicTacToeMod() {
        super("Tic-Tac-Toe",
                "cf28330e-9d0b-4ce2-b158-96d46fc397b2");
    }

    @Override
    public void Run() {
        TicTacToeMenu game = new TicTacToeMenu();
        game.start();
    }
}
