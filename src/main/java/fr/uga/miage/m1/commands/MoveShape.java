package fr.uga.miage.m1.commands;

import fr.uga.miage.m1.JDrawingFrame;
import fr.uga.miage.m1.shapes.SimpleShape;

public class MoveShape implements Command{

    private JDrawingFrame jf;

    private SimpleShape shapeStart;

    private SimpleShape shapeEnd;

    public MoveShape(JDrawingFrame jf, SimpleShape shapeStart, SimpleShape shapeEnd) {
        this.jf = jf;
        this.shapeStart = shapeStart;
        this.shapeEnd = shapeEnd;
    }
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
