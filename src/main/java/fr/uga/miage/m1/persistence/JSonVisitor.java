package fr.uga.miage.m1.persistence;

import fr.uga.miage.m1.shapes.Circle;
import fr.uga.miage.m1.shapes.Square;
import fr.uga.miage.m1.shapes.Triangle;


/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor {

    private String representation = null;

    private static final String JSON_TYPE_OPEN = "\t{\n\t\t\"type\": ";

    private static final String JSON_X_OPEN = ",\n\t\t\"x\": ";

    private static final String JSON_Y_OPEN = ",\n\t\t\"y\": ";

    private static final String JSON_END = "\n\t}";

    public JSonVisitor() { // default implementation ignored
    }

    @Override
    public void visit(Circle circle) {
        int x = circle.getX();
        int y = circle.getY();
        
        this.representation = JSON_TYPE_OPEN + "\"circle\"" + JSON_X_OPEN + x + JSON_Y_OPEN + y + JSON_END;
    }

    @Override
    public void visit(Square square) {
        int x = square.getX();
        int y = square.getY();

        this.representation = JSON_TYPE_OPEN + "\"square\"" + JSON_X_OPEN + x + JSON_Y_OPEN + y + JSON_END;
    }

    @Override
    public void visit(Triangle triangle) {
        int x = triangle.getX();
        int y = triangle.getY();

        this.representation = JSON_TYPE_OPEN + "\"triangle\"" + JSON_X_OPEN + x + JSON_Y_OPEN + y + JSON_END;
    }

    /**
     * @return the representation in JSon example for a Circle
     *
     *         <pre>
     * {@code
     *  {
     *     "shape": {
     *     	  "type": "circle",
     *        "x": -25,
     *        "y": -25
     *     }
     *  }
     * }
     *         </pre>
     */
    public String getRepresentation() {
        return representation;
    }
}
