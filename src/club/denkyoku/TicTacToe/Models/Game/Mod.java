package club.denkyoku.TicTacToe.Models.Game;


import java.util.Objects;

public abstract class Mod {
    private final String displayName;
    private final String UUID;

    /**
     * The constructor of an Abstract Game Mod.
     * @param displayName The displayName of a Game Mod.
     */
    protected Mod(String displayName, String UUID) {
        this.displayName = displayName;
        this.UUID = UUID;
    }

    /**
     * @return The displayName of a Game Mod.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return The UUID of a Game Mod.
     */
    public String getUUID() {
        return UUID;
    }

    /**
     * Run the Game Mod.
     */
    abstract public void run();

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Mod)) {
            return false;
        }
        return Objects.equals(this.UUID, ((Mod) obj).getUUID());
    }
}
