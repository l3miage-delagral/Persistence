package fr.uga.miage.m1.commands;

import fr.uga.miage.m1.JDrawingFrame;
import fr.uga.miage.m1.shapes.SimpleShape;

public class MoveShape implements Command{

    private final JDrawingFrame jf;


    private final int deltaX;
    private final int deltaY;

    private final SimpleShape shape;


    public MoveShape(JDrawingFrame jf, SimpleShape shape, int deltaX, int deltaY) {
        this.jf = jf;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.shape = shape;
    }

    @Override
    public void execute() {
        this.shape.move(deltaX, deltaY);
        jf.paintComponents(jf.getGraphics());
    }

    @Override
    public void undo() {
        this.shape.move(deltaX * -1, deltaY * -1);
        jf.paintComponents(jf.getGraphics());
    }
}
