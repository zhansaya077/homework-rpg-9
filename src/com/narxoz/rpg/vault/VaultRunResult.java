
package com.narxoz.rpg.vault;

import com.narxoz.rpg.artifact.AppraisalVisitor;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.HeroMemento;
import com.narxoz.rpg.memento.Caretaker;

import java.util.List;

public class ChronomancerEngine {


    public VaultRunResult runVault(List<Hero> party) {

        Caretaker caretaker = new Caretaker();

        AppraisalVisitor visitor =
                new AppraisalVisitor();

        int artifactsAppraised = 0;
        int mementosCreated = 0;
        int restoredCount = 0;

        System.out.println("=== Chronomancer's Vault ===");

        for (Hero hero : party) {

            System.out.println("\nHero enters vault:");
            System.out.println(hero);

            
            HeroMemento save =
                    hero.createMemento();

            caretaker.save(save);

            mementosCreated++;

            System.out.println("Hero state saved.");

            
            System.out.println("Appraising artifacts...");

            hero.getInventory().accept(visitor);

            artifactsAppraised +=
                    hero.getInventory()
                            .getArtifacts()
                            .size();

           
            System.out.println("Trap activated!");

            hero.takeDamage(150);

            System.out.println(
                    "Hero HP after trap: "
                            + hero.getHp()
            );

           
            if (!hero.isAlive()) {

                System.out.println(
                        "Rewinding time..."
                );

                hero.restoreFromMemento(
                        caretaker.undo()
                );

                restoredCount++;
            }

            System.out.println(
                    "Final hero state:"
            );

            System.out.println(hero);
        }

        return new VaultRunResult(
                artifactsAppraised,
                mementosCreated,
                restoredCount
        );
    }
}
