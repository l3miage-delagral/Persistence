package fr.uga.miage.m1.commands;

import fr.uga.miage.m1.JDrawingFrame;
import fr.uga.miage.m1.exceptions.LocationException;
import fr.uga.miage.m1.shapes.SimpleShape;

import javax.swing.*;
import java.util.List;

public class Undo extends Command {

    private final JDrawingFrame frame;

    public Undo(JDrawingFrame frame) {
        this.frame = frame;
    }

    // execute the command
    @Override
    public void execute () throws LocationException {
        // undo
        frame.undo();
    }

}
