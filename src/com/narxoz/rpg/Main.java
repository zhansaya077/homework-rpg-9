package com.narxoz.rpg;

import com.narxoz.rpg.artifact.*;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.memento.Caretaker;
import com.narxoz.rpg.vault.ChronomancerEngine;
import com.narxoz.rpg.vault.VaultRunResult;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 9 Demo: Visitor + Memento ===");

        Hero warrior = new Hero("Arthur", 100, 50, 100);
        Hero mage = new Hero("Merlin", 70, 150, 300);

        List<Artifact> vaultItems = new ArrayList<>();
        vaultItems.add(new Weapon("SunSlayer", 150, 12, 45));
        vaultItems.add(new Potion("Mana Ether", 40, 1, "Mana"));
        vaultItems.add(new Armor("Crystal Mail", 250, 30, 55));

        VaultAppraiser appraiser = new VaultAppraiser();
        for (Artifact item : vaultItems) {
            item.accept(appraiser);
        }
        System.out.println("Inventory Appraisal: " + appraiser.getTotalValue() + " gold");

        Caretaker caretaker = new Caretaker();
        caretaker.save(warrior);
        
        System.out.println("Pre-Trap State: HP=" + warrior.getHp() + ", Gold=" + warrior.getGold());
        warrior.takeDamage(60);
        warrior.spendGold(50);
        System.out.println("Post-Trap State: HP=" + warrior.getHp() + ", Gold=" + warrior.getGold());

        caretaker.undo(warrior);
        System.out.println("Rewind State: HP=" + warrior.getHp() + ", Gold=" + warrior.getGold());

        ChronomancerEngine engine = new ChronomancerEngine();
        VaultRunResult result = engine.runVault(mage, vaultItems);
        
        System.out.println("Vault Run Summary: " + result.getSummary());
        System.out.println("Final Gold Balance: " + result.getFinalGold());
    }
}
