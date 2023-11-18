package fr.uga.miage.m1.commands;

import fr.uga.miage.m1.JDrawingFrame;
import fr.uga.miage.m1.shapes.Group;
import fr.uga.miage.m1.shapes.ShapeFactory;
import fr.uga.miage.m1.shapes.SimpleShape;

import java.util.ArrayList;
import java.util.List;

public class MoveShape implements Command{

    private final JDrawingFrame jf;

    private final int deltaX;
    private final int deltaY;

    private final int evtX;
    private final int evtY;

    private final ArrayList<SimpleShape> startingShapeList;

    private final Group selectedGroup;

    private List<SimpleShape> listShapes;


    public MoveShape(JDrawingFrame jf, List<SimpleShape> startingShapeList, Group selectedGroup, int deltaX, int deltaY, int evtX, int evtY) {
        this.jf = jf;
        this.startingShapeList = new ArrayList<>(startingShapeList);
        this.selectedGroup = selectedGroup;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.evtX = evtX;
        this.evtY = evtY;
        this.listShapes = jf.getListShapes();
    }
    @Override
    public void execute() {
        int compteur = 0;
        for (SimpleShape startShape : startingShapeList) {
            if(compteur >= selectedGroup.getListGroup().size()){
                break;
            }
            SimpleShape selectShape = selectedGroup.getListGroup().get(compteur);

            if (startShape.contains(evtX, evtY) && selectShape.contains(evtX, evtY)) {
                return;
            }
            jf.removeShape(selectShape);
            selectedGroup.add(compteur,selectShape);
            selectedGroup.moveThis(selectShape, startShape.getX() + deltaX, startShape.getY() + deltaY);
            jf.addShape(selectedGroup.getListGroup().get(compteur));

            compteur++;
        }
        jf.paintComponents(jf.getGraphics());

    }

    @Override
    public void undo() {

        for (SimpleShape startShape : startingShapeList) {
            if(selectedGroup.getListGroup().isEmpty()){
                break;
            }

            SimpleShape selectShape = selectedGroup.getListGroup().get(0);

            jf.removeShape(selectShape);

            selectShape.move(startShape.getX() +25, startShape.getY() + 25);
            selectShape.selected(true);
            jf.addShape(selectShape);

        }

        jf.paintComponents(jf.getGraphics());
    }
}
