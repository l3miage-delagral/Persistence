package fr.uga.miage.m1.commands;

import fr.uga.miage.m1.JDrawingFrame;
import fr.uga.miage.m1.shapes.SimpleShape;

public class AddShape implements Command{

    private final JDrawingFrame jf;

    private final SimpleShape shape;

    public AddShape(JDrawingFrame jf, SimpleShape shape) {
        this.jf = jf;
        this.shape = shape;
    }
    @Override
    public void execute() {
        this.jf.addShape(shape);
    }

    @Override
    public void undo() {
        this.jf.removeShape(shape);
    }
}
