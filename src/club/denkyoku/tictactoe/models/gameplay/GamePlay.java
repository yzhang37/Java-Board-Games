package club.denkyoku.tictactoe.models.gameplay;

import club.denkyoku.tictactoe.models.player.Player;
import club.denkyoku.tictactoe.services.output.controls.MessageDialog;

public abstract class GamePlay {
    // whether there's only one human player
    protected boolean onlyOneHuman;
    // represent whether there's AI in the game
    protected boolean hasAI = false;
    protected boolean hasHuman;

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
        this.onlyOneHuman = humanCount == 1;
        this.hasHuman = humanCount > 0;
    }

    /**
     * Helper functions to make statistics.
     * @param players A list of <code>Player</code> objects.
     * @param winner The winner of the game. <code>null</code> if there's no winner.
     */
    public static void doGameStatistics(Player[] players, Player winner) {
        if (winner != null) {
            for (var player: players) {
                if (player == winner) {
                    player.wins += 1;
                } else {
                    player.losses += 1;
                }
            }
        } else {
            for (var player: players) {
                player.ties += 1;
            }
        }
    }

    public static void showGameStatistics(Player[] players) {
        String[] messages = new String[players.length + 2];
        messages[0] = "Game Statistics:";
        messages[1] = "";
        for (int i = 0; i < players.length; i++) {
            messages[i + 2] = String.format("%s: %d wins, %d losses, %d ties", players[i].getName(), players[i].wins, players[i].losses, players[i].ties);
        }
        MessageDialog.showOK(messages);
    }

    /**
     * The abstract function for playing the game.
     */
    abstract public void start();

}
