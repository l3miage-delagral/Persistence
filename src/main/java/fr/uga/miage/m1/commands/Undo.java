package fr.uga.miage.m1.commands;

import fr.uga.miage.m1.JDrawingFrame;

public class Undo implements Command {

    private final JDrawingFrame frame;

    public Undo(JDrawingFrame frame) {
        this.frame = frame;
    }

    // execute the command
    @Override
    public void execute () {
        // undo
        frame.undo();
    }

}
