package fr.uga.miage.m1.shapes;

import java.awt.*;

public class ShapeFactory {
    public enum Shapes {

        SQUARE, TRIANGLE, CIRCLE
    }

    private static ShapeFactory instance = null;

    private ShapeFactory() {
        instance = this;
    }

    public static ShapeFactory getInstance() {
        if(instance == null) {
            new ShapeFactory();
        }

        return instance;
    }

    public SimpleShape createSquare(int x, int y) {
        return new Square(x, y);
    }

    public SimpleShape createCircle(int x, int y) {
        return new Circle(x, y);
    }

    public SimpleShape createTriangle(int x, int y) {
        return new Triangle(x, y);
    }

    public SimpleShape createSimpleShape(Shapes selected, int x, int y, Graphics2D g2) {
        SimpleShape shape = null;

        switch (selected) {
            case CIRCLE:
                shape = getInstance().createCircle(x, y);
                shape.draw(g2);
                break;
            case TRIANGLE:
                shape = getInstance().createTriangle(x, y);
                shape.draw(g2);
                break;
            case SQUARE:
                shape = getInstance().createSquare(x, y);
                shape.draw(g2);
                break;
            default:
                return null;
        }

        return shape;
    }

}
