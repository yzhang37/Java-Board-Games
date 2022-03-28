package club.denkyoku.tictactoe.models.lmh.role;

/**
 * Interface which defines all the fields needed for the basic monster.
 */
public interface IMonster {
    // Abstract property that returns the value of the damage dealt by a monster.
    public long getDamage();

    // Abstract property that returns the defense value of a monster.
    public long getDefence();

    // An abstract property that returns a base value for monster dodge.
    public long getDodge();
}
