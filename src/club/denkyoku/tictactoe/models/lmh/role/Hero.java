package club.denkyoku.tictactoe.models.lmh.role;

/**
 * Hero's generic class
 */
public abstract class Hero extends BaseRole implements IReadonlyHero {
    // store the name of the hero
    protected String name;
    // store the hero's strength
    protected long strength;
    // store the hero's dexterity
    protected long dexterity;
    // store the hero's agility
    protected long agility;
    // store the full mana of the hero
    protected long mana;
    // store the hero's healthPower
    protected long healthPower;
    // buffered experience point, used for leveling up
    protected long bufferedExp;
    // total experience point
    protected long experience;
    // this money that a player has
    protected long money;

    public Hero(String name,
                long mana,
                long strength,
                long dexterity,
                long money,
                long exp) {
        this.name = name;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.bufferedExp = 0;
        this.level = 1;
        this.healthPower = this.getDefaultHealthPower();
        this.money = money;
        this.addExperience(exp);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getStrength() {
        return this.strength;
    }

    @Override
    public long getDexterity() {
        return this.dexterity;
    }

    @Override
    public long getAgility() {
        return this.agility;
    }

    @Override
    public long getMana() {
        return this.mana;
    }

    @Override
    public long getHealthPower() {
        return this.healthPower;
    }

    /**
     * Keep calling getLevelUp until the level reaches the desired requirement.
     * @param newLevel The desired level
     */
    protected void setLevel(int newLevel) {
        if (newLevel < 1) {
            throw new IllegalArgumentException("Level must be greater than 0");
        }
        while (this.level < newLevel) {
            this.getLevelUp();
        }
    }

    /**
     * Performs heroes' property update, when the heroes upgrade.
     */
    public void getLevelUp() {
        // 1. Update the mana
        this.mana += Math.max(1, this.getIncreaseMana());

        // 2. Updating other like strength, dexterity, agility, etc.
        this.strength += Math.max(1, this.getIncreaseStrength());
        this.dexterity += Math.max(1, this.getIncreaseDexterity());
        this.agility += Math.max(1, this.getIncreaseAgility());
        this.healthPower = this.getDefaultHealthPower();

        // Finally, update the level
        this.level ++;
    }

    // Get how much Mana value to add next time.
    protected long getIncreaseMana() {
        return Math.round(this.mana * 0.1);
    }

    // Get how much Strength value to add next time.
    protected abstract long getIncreaseStrength();

    // Get how much Dexterity value to add next time.
    protected abstract long getIncreaseDexterity();

    // Get how much Agility value to add next time.
    protected abstract long getIncreaseAgility();

    // Get how much experience point to add to level up.
    protected long getLevelUpExpRequirement() {
        return 10 * this.level;
    }

    // Get how much HP value to add next time.
    protected long getDefaultHealthPower() {
        return 100 * this.level;
    }

    /**
     * Add a certain amount of experience value to a hero at once.
     * @param exp Amount of experience value
     */
    public void addExperience(long exp) {
        this.bufferedExp += exp;
        // Keep calculating upgrades until
        // bufferedExp is not enough to upgrade.
        while (this.bufferedExp >= this.getLevelUpExpRequirement()) {
            // first remove the corresponding amount of experience
            this.bufferedExp -= this.getLevelUpExpRequirement();
            // then upgrade the hero
            this.getLevelUp();
        }
        this.experience += exp;
    }

}
