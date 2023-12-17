package fr.uga.miage.m1.persistence;

import fr.uga.miage.m1.shapes.SimpleShape;
import fr.uga.miage.m1.shapes.Group;

/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

    private String representation = "";
    private static final String XML_SHAPE_OPEN = "\t\t<shape>\n";
    private static final String XML_TYPE_OPEN = "\t\t\t<type>";
    private static final String XML_X_OPEN = "\t\t\t<x>";
    private static final String XML_Y_OPEN = "\t\t\t<y>";

    private static final String XML_Z_OPEN = "\t\t\t<z>";

    private static final String XML_X_END = "</x>\n";

    private  static final String XML_Y_END = "</y>\n";

    private static final String XML_Z_END = "</z>\n";
    private static final String XML_TYPE_END = "</type>\n";

    private static final String XML_SHAPE_END = "\t\t</shape>\n";

    public XMLVisitor() { // default implementation ignored
    }

    @Override
    public void visit(SimpleShape shape) {

        if (shape.getShapeName().contains("group")) {
            this.representation += "\t\t<group>\n";

            for (SimpleShape s : ((Group) shape).getListGroup()) {
                getRepresentationOfShape(s);
            }
            this.representation += "\t\t</group>\n";

        } else {
            getRepresentationOfShape(shape);
        }
    }

    private void getRepresentationOfShape(SimpleShape shape) {
        this.representation += XML_SHAPE_OPEN + XML_TYPE_OPEN + shape.getShapeName() + XML_TYPE_END + XML_X_OPEN + shape.getX() + XML_X_END + XML_Y_OPEN + shape.getY() + XML_Y_END;
        
        if(shape.getZ() != 0) {
            this.representation += XML_Z_OPEN + shape.getZ() + XML_Z_END;
        }
        this.representation += XML_SHAPE_END;
    }

    /**
     * @return the representation in JSon example for a Triangle:
     *
     *         <pre>
     * {@code
     *  <shape>
     *    <type>triangle</type>
     *    <x>-25</x>
     *    <y>-25</y>
     *  </shape>
     * }
     * </pre>
     */
    public String getRepresentation() {
        return representation;
    }
}
