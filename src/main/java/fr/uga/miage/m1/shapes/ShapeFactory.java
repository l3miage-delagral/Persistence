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

    public SimpleShape createSimpleShape(Shapes selected, int x, int y, Graphics2D g2) {
        SimpleShape shape = null;

        switch (selected) {
            case CIRCLE:
                Circle c = new Circle(x, y);
                c.draw(g2);
                shape = c;
                break;
            case TRIANGLE:
                Triangle t = new Triangle(x, y);
                t.draw(g2);
                shape = t;
                break;
            case SQUARE:
                Square s = new Square(x, y);
                s.draw(g2);
                shape = s;
                break;
            default:
                return null;
        }

        return shape;
    }

}
