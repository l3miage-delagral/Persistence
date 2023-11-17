package fr.uga.miage.m1.shapes;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<SimpleShape> groupList;
    public Group() {
        groupList = new ArrayList<>();
    }

    public void remove(SimpleShape shape) {
        groupList.remove(shape);
        shape.selected(false);
    }

    public void setGroupList(List<SimpleShape> groupList) {
        this.groupList = groupList;
    }
    public List<SimpleShape> getListGroup() {
        return groupList;
    }

    public void add(SimpleShape shape) {
        groupList.add(shape);
        shape.selected(true);
    }

    public boolean isInGroup(SimpleShape shape) {
        for (SimpleShape shapeInGroup : groupList) {
            if (shape.contains(shapeInGroup.getX(), shapeInGroup.getY())) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        groupList.clear();
    }

}
