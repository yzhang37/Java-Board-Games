package club.denkyoku.tictactoe.models.lmh.role;

/**
 * Interface which defines all the fields required by the basic hero.
 */
public interface IReadonlyHero {
    // An abstract property that returns the Strength value the hero has.
    public long getStrength();

    // An abstract property that returns the Dexterity value the hero has.
    public long getDexterity();

    // An abstract property that returns the Agility value the hero has.
    public long getAgility();
    
    // An abstract property that returns the Full Mana value the hero has.
    public long getMana();
}
