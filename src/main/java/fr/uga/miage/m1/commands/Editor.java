package fr.uga.miage.m1.commands;

import java.util.ArrayList;
import java.util.List;

public class Editor {

    protected List<Command> commands;

    public Editor() { // empty constructor
        commands = new ArrayList<>();
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void play() {
        for (Command command : commands) {

            command.execute();

        }
    }
}
