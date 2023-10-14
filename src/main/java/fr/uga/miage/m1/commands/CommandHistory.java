package fr.uga.miage.m1.commands;

import java.util.ArrayList;

public class CommandHistory {
    // history of commands
    private final ArrayList<Command> history;

    public CommandHistory() {
        history = new ArrayList<>();
    }

    // add a command to the history
    public void push(Command c) {
        history.add(c);
    }

    // remove the last command from the history
    public void pop() {
        history.remove(history.size() - 1);
    }

}
