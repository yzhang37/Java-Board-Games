package club.denkyoku.tictactoe.models.lmh.role;

/**
 * Define the basic abstract class for roles.
 */
public abstract class BaseRole {
    // An abstract attribute that returns the name of a character.
    public abstract String getName();

    // An abstract function that returns Hero's temporary blood level
    public abstract long getHealthPower();

    // specify the level of the role
    protected long level;

    // get the level of the role
    public long getLevel() {
        return this.level;
    }
}
