package club.denkyoku.TicTacToe.Services.Input;

/**
 * Abstract class for data synchronization in Key Handlers.
 */
public abstract class DataSync {
    public DataSync() {
        this.reset();
    }

    abstract public void reset();
}
