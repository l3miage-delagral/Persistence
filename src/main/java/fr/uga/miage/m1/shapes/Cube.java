package fr.uga.miage.m1.shapes;

import edu.uga.singleshape.CubePanel;
import fr.uga.miage.m1.persistence.Visitor;

import java.awt.*;

public class Cube implements SimpleShape {
    private int x;
    private int y;
    private int z;

    private final CubePanel cubePanel;

    public Cube(int x, int y, int z) {

        this.x = x - 25;
        this.y = y -25;
        this.z = z;
        cubePanel = new CubePanel(z, x, y);
    }


    @Override
    public Object accept(Visitor visitor) {

        visitor.visit(this);
        return null;
    }

    public void draw(Graphics2D g2) {
        cubePanel.paintComponent(g2);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String getShapeName() {
        return "cube";
    }

    @Override
    public ShapeFactory.Shapes getShapeType() {
        return ShapeFactory.Shapes.CUBE;
    }

    @Override
    public boolean contains(int x, int y) {
        return cubePanel.contains(x, y);
    }

    @Override
    public void move(int x, int y) {
        this.x = x - 25;
        this.y = y - 25;
    }

    @Override
    public void selected(boolean selected ) {
        // not yet implemented
    }
}
