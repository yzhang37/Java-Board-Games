package club.denkyoku.tictactoe.models.lmh.role;

/**
 * Warrior type of heroes.
 */
public class WarriorHero extends Hero {
    public WarriorHero(String name, long mana, long strength, long dexterity, long money, long exp) {
        super(name, mana, strength, dexterity, money, exp);
    }

    @Override
    protected long getIncreaseStrength() {
        return Math.round(this.strength * 0.1);
    }

    @Override
    protected long getIncreaseDexterity() {
        return Math.round(this.strength * 0.05);
    }

    @Override
    protected long getIncreaseAgility() {
        return Math.round(this.strength * 0.1);
    }
}
