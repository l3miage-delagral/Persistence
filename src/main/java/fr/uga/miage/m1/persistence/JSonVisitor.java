package fr.uga.miage.m1.persistence;

import fr.uga.miage.m1.shapes.SimpleShape;

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
    public void visit(SimpleShape shape) {
        int x = shape.getX();
        int y = shape.getY();

        this.representation = JSON_TYPE_OPEN + "\""+shape.getShapeName()+"\"" + JSON_X_OPEN + x + JSON_Y_OPEN + y + JSON_END;
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
