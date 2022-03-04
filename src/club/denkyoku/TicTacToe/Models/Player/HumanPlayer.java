package club.denkyoku.TicTacToe.Models.Player;


import club.denkyoku.TicTacToe.Models.Board.Board;
import club.denkyoku.TicTacToe.Models.Board.Slot;
import club.denkyoku.TicTacToe.Models.Config;
import club.denkyoku.TicTacToe.Models.Configuration.PlayerInfo;

// Human player, press the keyboard to play the game.
public class HumanPlayer extends Player {
    public HumanPlayer() {
        super();
        this.isHuman = true;
    }

    public HumanPlayer(String name, char symbol) {
        super();
        this.isHuman = true;
        this.name = name;
        this.symbol = symbol;
    }

    /**
     * This function is not used.
     */
    @Override
    public <T extends Slot> Move getMove(
            Board<T> board, Player[] playerLists) {
        return new Move(0, 0);
    }

    /**
     * Helper functions to Create a List of HumanPlayer
     * @return List of HumanPlayer
     */
    public static Player[] createHumanPlayers() {
        Player[] players = new Player[Config.getPlayerCounts()];
        for (int i = 0; i < Config.getPlayerCounts(); i++) {
            PlayerInfo info = Config.playerInfos.get(i);
            players[i] = new HumanPlayer(info.name, info.symbol);
        }
        return players;
    }
}
