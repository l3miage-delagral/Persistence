package fr.uga.miage.m1.commands;

import fr.uga.miage.m1.exceptions.LocationException;

public abstract class Command{

    CommandHistory commandHist = new CommandHistory();

    // execute the command
    public void execute() throws LocationException {
        // implemented by children
    }

    // push to history
    public void push() {
        commandHist.push(this);
    }

    // remove from history
    public void remove() {
        commandHist.pop();
    }

}
