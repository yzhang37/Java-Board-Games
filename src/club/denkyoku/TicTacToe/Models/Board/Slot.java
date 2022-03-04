package club.denkyoku.TicTacToe.Models.Board;

import club.denkyoku.TicTacToe.Models.Player.Player;

public class Slot {
    protected Player player;

    /**
     * Create a new slot, with a given player
     * @param player the player to be put in the slot
     */
    public Slot(Player player) {
        this.player = player;
    }

    /**
     * @return the player item in the slot
     */
    public Player getPlayer() {
        return player;
    }
}
