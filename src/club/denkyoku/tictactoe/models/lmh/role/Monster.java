package club.denkyoku.tictactoe.models.lmh.role;

public class Monster extends BaseRole implements IMonster{
    // store the name of the monster
    protected String name;
    // store damage
    protected long damage;
    // store defense
    protected long defense;
    // store dodge
    protected long dodge;

    public Monster(String name,
                   long level,
                   long damage,
                   long defense,
                   long dodge) {
        this.name = name;
        this.level = level;
        this.damage = damage;
        this.defense = defense;
        this.dodge = dodge;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Monster's blood is fixed at level * 100
     */
    @Override
    public long getHealthPower() {
        return this.level * 100;
    }

    @Override
    public long getDamage() {
        return this.damage;
    }

    @Override
    public long getDefence() {
        return this.defense;
    }

    @Override
    public long getDodge() {
        return this.dodge;
    }
}
