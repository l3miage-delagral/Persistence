package fr.uga.miage.m1.commands;

import fr.uga.miage.m1.exceptions.LocationException;
import fr.uga.miage.m1.shapes.SimpleShape;

import java.util.List;

public class Undo extends Command {

    private final List<SimpleShape> listShapes;

    public Undo(List<SimpleShape> listShapes) {
        this.listShapes = listShapes;
    }

    // execute the command
    @Override
    public void execute () throws LocationException {
        // remove the last Shape from the list
        if (!listShapes.isEmpty()) {
            listShapes.remove(listShapes.size() - 1);
        }
    }

}
