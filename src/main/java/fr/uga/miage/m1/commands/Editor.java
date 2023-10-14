package fr.uga.miage.m1.commands;

import fr.uga.miage.m1.exceptions.LocationException;
import fr.uga.miage.m1.shapes.SimpleShape;

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

    public void play() throws LocationException {
        for (Command command : commands) {
            try {
                command.execute();
            }catch (LocationException e){
                throw new LocationException(e.getMessage());
            }
        }
    }

    public void reset() {
        commands.clear();
    }

    public void undo() throws LocationException {
        commands.remove(commands.size() - 1);
        play();
    }
}
