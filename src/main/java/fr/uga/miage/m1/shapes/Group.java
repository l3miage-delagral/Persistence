package fr.uga.miage.m1.shapes;


import java.util.ArrayList;
import java.util.List;

public class Group {

    private final List<SimpleShape> groupList;
    public Group() {
        groupList = new ArrayList<>();
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

    public void moveThis(SimpleShape selectShape, int x, int y) {
        if(selectShape != null) {
            selectShape.move(x, y);
        }
    }
}
