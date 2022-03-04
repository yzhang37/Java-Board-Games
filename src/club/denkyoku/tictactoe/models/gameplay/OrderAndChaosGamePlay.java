package club.denkyoku.tictactoe.models.gameplay;

import club.denkyoku.tictactoe.models.board.Slot;
import club.denkyoku.tictactoe.models.player.Player;
import club.denkyoku.tictactoe.services.output.controls.MessageDialog;

public class OrderAndChaosGamePlay extends TicTacToeGamePlay{

    /**
     * Create a new Order And Chaos game
     * @param players The players of the game
     */
    public OrderAndChaosGamePlay(Player[] players) {
        super(6, players);
    }

    /**
     * Modified Enter method in Order And Chaos to allow for a player to enter a slot
     * @param curTurnPlayer The player who is currently taking a turn
     */
    @Override
    protected void humanSelectMove(Player curTurnPlayer) {
        MessageDialog.Button[] btns = new MessageDialog.Button[]{
                new MessageDialog.Button(String.valueOf(this.players[0].getSymbol()), '\0'),
                new MessageDialog.Button(String.valueOf(this.players[1].getSymbol()), '\0')
        };

        int ret = MessageDialog.show(new String[] {
                "You have selected a move.",
                "You want to place:"
        }, btns, 0, -1);
        this.board.put(this.cursor_x, this.cursor_y, new Slot(this.players[ret]));
    }

    @Override
    protected Player checkWinner()  {
        // Check if chaos wins
        if (this.board.isFull()){
            return this.players[1];
        }

        // TODO: p <= player counts
        for (int p = 0; p < 2; p++) {
            Player the_p = this.players[p];
            for (int x = 0; x < this.board.getWidth(); x++) {
                for (int y = 0; y < this.board.getHeight(); y++) {
                    //Check rows
                    if (x <= this.board.getWidth() - 5 && (
                            this.board.at(x, y) != null && this.board.at(x, y).getPlayer() == the_p &&
                            this.board.at(x + 1, y) != null && this.board.at(x + 1, y).getPlayer() == the_p &&
                            this.board.at(x + 2, y) != null && this.board.at(x + 2, y).getPlayer() == the_p &&
                            this.board.at(x + 3, y) != null && this.board.at(x + 3, y).getPlayer()  == the_p &&
                            this.board.at(x + 4, y) != null && this.board.at(x + 4, y).getPlayer() == the_p)
                    ) {
                        return this.players[0];
                    }

                    //Check columns
                    if (y <= this.board.getHeight() - 5 && (
                            this.board.at(x, y) != null && this.board.at(x, y).getPlayer() == the_p &&
                            this.board.at(x, y + 1) != null && this.board.at(x, y + 1).getPlayer() == the_p &&
                            this.board.at(x, y + 2) != null && this.board.at(x, y + 2).getPlayer() == the_p &&
                            this.board.at(x, y + 3) != null && this.board.at(x, y + 3).getPlayer() == the_p &&
                            this.board.at(x, y + 4) != null && this.board.at(x, y + 4).getPlayer() == the_p )
                    ) {
                        return this.players[0];
                    }

                    //Check diagonals
                    if (x >= 2 && x <= this.board.getWidth() - 3 &&
                            y >= 2 && y <= this.board.getHeight() - 3
                            && ((
                                this.board.at(x - 2, y - 2) != null && this.board.at(x - 2, y - 2).getPlayer() == the_p &&
                                this.board.at(x - 1, y - 1) != null && this.board.at(x - 1, y - 1).getPlayer() == the_p &&
                                this.board.at(x, y) != null && this.board.at(x, y).getPlayer() == the_p &&
                                this.board.at(x + 1, y + 1) != null && this.board.at(x + 1, y + 1).getPlayer() == the_p &&
                                this.board.at(x + 2, y + 2) != null && this.board.at(x + 2, y + 2).getPlayer() == the_p )
                            || (
                                this.board.at(x + 2, y - 2) != null && this.board.at(x + 2, y - 2).getPlayer() == the_p &&
                                this.board.at(x + 1, y - 1) != null && this.board.at(x + 1, y - 1).getPlayer() == the_p &&
                                this.board.at(x, y) != null && this.board.at(x, y).getPlayer() == the_p &&
                                this.board.at(x - 1, y + 1) != null && this.board.at(x - 1, y + 1).getPlayer() == the_p &&
                                this.board.at(x - 2, y + 2) != null && this.board.at(x - 2, y + 2).getPlayer() == the_p )
                    )) {
                        return this.players[0];
                    }
                }
            }
        }
        return null;
    }
}
