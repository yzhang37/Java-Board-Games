package club.denkyoku.tictactoe.models.board;

import club.denkyoku.tictactoe.models.player.Player;

public class Slot {
    protected final Player player;

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
