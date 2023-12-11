package fr.uga.miage.m1.shapes;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import fr.uga.miage.m1.persistence.Visitor;
import fr.uga.miage.m1.shapes.ShapeFactory.Shapes;

public class Group implements SimpleShape{

    private final List<SimpleShape> groupList;

    public Group() {
        groupList = new ArrayList<>();
    }

    public Group(List<SimpleShape> listShapes) {
        this.groupList = listShapes;
    }

    public void remove(SimpleShape shape) {
        groupList.remove(shape);
        shape.selected(false);
    }

    public List<SimpleShape> getListGroup() {
        return groupList;
    }

    public void add(SimpleShape shape) {
        groupList.add(shape);
        shape.selected(true);
    }

    public void add(int index, SimpleShape shape) {
        groupList.add(index, shape);
        shape.selected(true);
    }

    public boolean isInGroup(SimpleShape shape) {
        for (SimpleShape shapeInGroup : groupList) {
            if (shapeInGroup.equals(shape)) {
                return true;
            }
        }
        return false;
    }

    

    @Override
    public Object accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }

    @Override
    public void draw(Graphics2D g2) {
        for (SimpleShape shape : groupList) {
            shape.draw(g2);
        }
    }

    @Override
    public int getX() {
        return -1;
    }

    @Override
    public int getY() {
        return -1;
    }

    @Override
    public int getZ() {
        return -1;
    }

    @Override
    public String getShapeName() {
        return "group";
    }

    @Override
    public Shapes getShapeType() {
        return ShapeFactory.Shapes.GROUP;
    }

    @Override
    public boolean contains(int x, int y) {
        for (SimpleShape shape : groupList) {
            if (shape.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(int x, int y) {
        for (SimpleShape shape : groupList) {
            shape.move(shape.getX() + x, shape.getY() + y);
        }
    }

    @Override
    public void selected(boolean selected) {
        for (SimpleShape shape : groupList) {
            shape.selected(selected);
        }
    }

    @Override
    public void validerGroup(Color color) {
        this.selected(false);
        for (SimpleShape shape : groupList) {
            shape.validerGroup(color);
        }
    }
}
