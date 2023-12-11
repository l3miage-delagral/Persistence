package fr.uga.miage.m1.shapes;

import fr.uga.miage.m1.persistence.Visitable;

import java.awt.*;

/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public interface SimpleShape extends Visitable {

    /**
     * Method to draw the shape of the extension.
     * @param g2 The graphics object used for painting.
     */
    void draw(Graphics2D g2);

    int getX();

    int getY();

    int getZ();

    String getShapeName();

    ShapeFactory.Shapes getShapeType();

    boolean contains(int x, int y);

    void move(int x, int y);

    void selected(boolean selected);

    void validerGroup(Color color);

}
