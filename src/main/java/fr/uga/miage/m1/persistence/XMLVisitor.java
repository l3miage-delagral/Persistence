package fr.uga.miage.m1.persistence;

import fr.uga.miage.m1.shapes.SimpleShape;


/**
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class XMLVisitor implements Visitor {

    private String representation = null;
    private static final String XML_SHAPE_OPEN = "\t\t<shape>\n";
    private static final String XML_TYPE_OPEN = "\t\t\t<type>";
    private static final String XML_X_OPEN = "\t\t\t<x>";
    private static final String XML_Y_OPEN = "\t\t\t<y>";

    private static final String XML_X_END = "</x>\n";
    private static final String XML_TYPE_END = "</type>\n";

    private  static final String XML_Y_END = "</y>\n";

    private static final String XML_SHAPE_END = "\t\t</shape>";
    public XMLVisitor() { // default implementation ignored
    }

    @Override
    public void visit(SimpleShape shape) {
        int x = shape.getX();
        int y = shape.getY();

        this.representation = XML_SHAPE_OPEN + XML_TYPE_OPEN + shape.getShapeName() + XML_TYPE_END + XML_X_OPEN + x + XML_X_END + XML_Y_OPEN + y + XML_Y_END + XML_SHAPE_END;
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
