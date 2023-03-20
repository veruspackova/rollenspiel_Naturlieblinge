package backend.character;

//-- Hit Points (HP): 8
//-- Armor Class (AC): 10
//-- Attack/Damage: d8

import backend.artifacts.items.Item;
import backend.artifacts.weapons.WeaponBase;
import backend.artifacts.weapons.melee.NoWeapon;
import backend.enums.Race;
import backend.enums.Spells;
import backend.enums.Stat;
import backend.logic.Dice;

import java.util.ArrayList;

public class Wizard extends Character {

    private int slotsHeal;
    private int slotsRayOfFrost;
    private int slotsBurningHands;

    // Beginning items: dagger
    public Wizard(Race race, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, ArrayList<Item> items, WeaponBase selectedWeapon, ArrayList<WeaponBase> weapons) {
        super(race, name, strength, dexterity, constitution, intelligence, wisdom, items, selectedWeapon, weapons);
        initialiseSpellSlots();

        setHitPoints(8 + getStatModifier(Stat.CON));
        setHitDiceAvailable(1);
        setHitDice(new Dice(6));

        setArmourClass(10);
        setAttackDice(new Dice(8));
    }

    public Wizard(Race race, String name) {
        this(race, name, 10, 10, 10, 10, 10, new ArrayList<>(), new NoWeapon(), new ArrayList<>());
    }

    public int getSpellDC() {
        return 8 + getStatModifier(Stat.INT) + getProficiencyBonus();
    }

    public void setSelectedWeapon(WeaponBase selectedWeapon) {
        if (getStat(Stat.STR) >= selectedWeapon.getRequiredStrength()) {
            super.selectedWeapon = selectedWeapon;
        }
    }

    public void initialiseSpellSlots() {
        slotsHeal = 2;
        slotsRayOfFrost = 1;
        slotsBurningHands = 1;
    }

    public int getSpellSlotsAvailable(Spells spell) {
        int value = 0;

        switch (spell) {
            case BURNING_HANDS -> value = slotsBurningHands;
            case RAY_OF_FROST -> value = slotsRayOfFrost;
            case HEAL -> value = slotsHeal;
        }

        return value;
    }

    /**
     * 1d8 + your spellcasting ability modifier
     *
     * @param target Character to be healed
     */
    public void castHeal(Character target) {
        if (slotsHeal > 0) {
            Dice d8 = new Dice(8);

            int healAmount = d8.roll() + getStatModifier(Stat.INT);
            target.setHitPoints(target.getHitPoints() + healAmount);

            slotsHeal -= 1;
        }
    }

    public int getSlotsHeal() {
        return slotsHeal;
    }

    public void setSlotsHeal(int slotsHeal) {
        this.slotsHeal = slotsHeal;
    }

    public int getSlotsRayOfFrost() {
        return slotsRayOfFrost;
    }

    public void setSlotsRayOfFrost(int slotsRayOfFrost) {
        this.slotsRayOfFrost = slotsRayOfFrost;
    }

    public int getSlotsBurningHands() {
        return slotsBurningHands;
    }

    public void setSlotsBurningHands(int slotsBurningHands) {
        this.slotsBurningHands = slotsBurningHands;
    }
}
