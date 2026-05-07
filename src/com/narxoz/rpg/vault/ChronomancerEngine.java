
package com.narxoz.rpg.vault;

import com.narxoz.rpg.artifact.AppraisalVisitor;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.HeroMemento;
import com.narxoz.rpg.memento.Caretaker;

import java.util.List;

public class ChronomancerEngine {

    /**
     * Runs the vault sequence for the supplied party.
     *
     * @param party the heroes entering the vault
     * @return vault result
     */
    public VaultRunResult runVault(List<Hero> party) {

        Caretaker caretaker = new Caretaker();

        AppraisalVisitor visitor =
                new AppraisalVisitor();

        int totalGold = 0;
        int restoredHeroes = 0;
        int defeatedHeroes = 0;

        System.out.println("=== Chronomancer's Vault ===");

        for (Hero hero : party) {

            System.out.println("\nHero enters vault:");
            System.out.println(hero);

            // SAVE STATE
            HeroMemento save =
                    hero.createMemento();

            caretaker.save(save);

            System.out.println("State saved.");

            // VISITOR
            System.out.println("Appraising artifacts...");

            hero.getInventory().accept(visitor);

            // TRAP
            System.out.println("Trap activated!");

            hero.takeDamage(150);

            System.out.println(
                    "HP after trap: "
                            + hero.getHp()
            );

            // RESTORE
            if (!hero.isAlive()) {

                System.out.println(
                        "Hero defeated. Rewinding time..."
                );

                hero.restoreFromMemento(
                        caretaker.undo()
                );

                restoredHeroes++;
            }

            if (!hero.isAlive()) {
                defeatedHeroes++;
            }

            totalGold += hero.getGold();

            System.out.println("Final hero state:");
            System.out.println(hero);
        }

        return new VaultRunResult(
                totalGold,
                restoredHeroes,
                defeatedHeroes
        );
    }
}
