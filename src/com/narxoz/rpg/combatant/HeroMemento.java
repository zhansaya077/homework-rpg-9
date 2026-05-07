
package com.narxoz.rpg.combatant;

import com.narxoz.rpg.artifact.Artifact;
import com.narxoz.rpg.artifact.Inventory;

public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private int mana;
    private int gold;
    private final int attackPower;
    private final int defense;
    private Inventory inventory;

    public Hero(String name, int hp, int attackPower, int defense) {
        this(name, hp, 0, attackPower, defense, 0, new Inventory());
    }

    public Hero(String name,
                int hp,
                int mana,
                int attackPower,
                int defense,
                int gold,
                Inventory inventory) {

        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.mana = mana;
        this.gold = gold;
        this.attackPower = attackPower;
        this.defense = defense;
        this.inventory = inventory == null
                ? new Inventory()
                : inventory;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getMana() {
        return mana;
    }

    public int getGold() {
        return gold;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public void restoreMana(int amount) {
        mana += Math.max(0, amount);
    }

    public boolean spendMana(int amount) {

        if (amount < 0 || amount > mana) {
            return false;
        }

        mana -= amount;
        return true;
    }

    public void addGold(int amount) {
        gold += Math.max(0, amount);
    }

    public boolean spendGold(int amount) {

        if (amount < 0 || amount > gold) {
            return false;
        }

        gold -= amount;
        return true;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory == null
                ? new Inventory()
                : inventory;
    }

    // MEMENTO

    public HeroMemento createMemento() {

        return new HeroMemento(
                name,
                hp,
                mana,
                gold,
                maxHp,
                attackPower,
                defense,
                inventory.getArtifacts()
        );
    }

    public void restoreFromMemento(HeroMemento memento) {

        if (memento == null) {
            return;
        }

        this.hp = memento.getHp();
        this.mana = memento.getMana();
        this.gold = memento.getGold();

        Inventory restoredInventory = new Inventory();

        for (Artifact artifact
                : memento.getInventorySnapshot()) {

            restoredInventory.addArtifact(artifact);
        }

        this.inventory = restoredInventory;
    }

    @Override
    public String toString() {

        return "Hero{"
                + "name='" + name + '\''
                + ", hp=" + hp
                + ", mana=" + mana
                + ", gold=" + gold
                + ", attackPower=" + attackPower
                + ", defense=" + defense
                + '}';
    }
}
