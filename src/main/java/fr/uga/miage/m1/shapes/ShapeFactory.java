package fr.uga.miage.m1.shapes;

import java.awt.*;

public class ShapeFactory {
    public enum Shapes {

        SQUARE, TRIANGLE, CIRCLE
    }

    private static ShapeFactory instance = null;

    private ShapeFactory() {
    }

    public static ShapeFactory getInstance() {
        if(instance == null) {
            instance = new ShapeFactory();
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

    public SimpleShape createSimpleShape(Shapes selected, int x, int y) {
        SimpleShape shape = null;

        switch (selected) {
            case CIRCLE:
                shape = new Circle(x, y);
                break;
            case TRIANGLE:
                shape = new Triangle(x, y);
                break;
            case SQUARE:
                shape = new Square(x, y);
                break;
            default:
                return null;
        }

        return shape;
    }

}
