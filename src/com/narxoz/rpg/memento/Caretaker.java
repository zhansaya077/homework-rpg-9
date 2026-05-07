
package com.narxoz.rpg.memento;

import com.narxoz.rpg.combatant.HeroMemento;

import java.util.Stack;

public class Caretaker {

    private final Stack<HeroMemento> history =
            new Stack<>();

    /**
     * Saves a snapshot to the caretaker history.
     *
     * @param memento the snapshot to store
     */
    public void save(HeroMemento memento) {

        if (memento != null) {
            history.push(memento);
        }
    }

    /**
     * Removes and returns the most recent snapshot.
     *
     * @return the latest stored snapshot
     */
    public HeroMemento undo() {

        if (history.isEmpty()) {
            return null;
        }

        return history.pop();
    }

    public HeroMemento peek() {

        if (history.isEmpty()) {
            return null;
        }

        return history.peek();
    }

    
    public int size() {
        return history.size();
    }
}
