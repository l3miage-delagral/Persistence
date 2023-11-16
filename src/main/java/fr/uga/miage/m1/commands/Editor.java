package fr.uga.miage.m1.commands;

import java.util.ArrayList;
import java.util.List;

public class Editor {

    protected List<Command> commands;

    private List<Command> history;

    public Editor() { // empty constructor

        commands = new ArrayList<>();
        history = new ArrayList<>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void play() {
        for (Command command : commands) {
            command.execute();
            history.add(command);
        }
        commands.clear();
    }

    public Command getLastCommand() {
        return history.get(history.size()-1);
    }
}
