package club.denkyoku.TicTacToe.Models.GamePlay;

import club.denkyoku.TicTacToe.Models.Player;

public abstract class GamePlay {
    // whether there's only one human player
    protected boolean onlyOneHuman;
    // represent whether there's AI in the game
    protected boolean hasAI = false;

    /**
     * Helper functions for computing the player
     * numbers, how many Human players and how many
     * AI players.
     * @param players A list of <code>Player</code> objects.
     */
    protected void doPlayerStatistics(Player[] players) {
        int humanCount = 0;
        for (var player: players) {
            if (player.isHumanPlayer()) {
                humanCount++;
            } else {
                this.hasAI = true;
            }
        }
        if (humanCount == 1) {
            this.onlyOneHuman = true;
        }
    }

    /**
     * The abstract function for playing the game.
     */
    abstract public void start();

}
