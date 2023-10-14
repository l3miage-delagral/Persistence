package fr.uga.miage.m1.persistence;

import fr.uga.miage.m1.shapes.Circle;
import fr.uga.miage.m1.shapes.Square;
import fr.uga.miage.m1.shapes.Triangle;


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
    public void visit(Circle circle) {
        int x = circle.getX();
        int y = circle.getY();

        this.representation = XML_SHAPE_OPEN + XML_TYPE_OPEN + "circle" + XML_TYPE_END + XML_X_OPEN + x + XML_X_END + XML_Y_OPEN + y + XML_Y_END + XML_SHAPE_END;
    }

    @Override
    public void visit(Square square) {
        int x = square.getX();
        int y = square.getY();

        this.representation = XML_SHAPE_OPEN + XML_TYPE_OPEN + "square" + XML_TYPE_END + XML_X_OPEN + x + XML_X_END + XML_Y_OPEN + y + XML_Y_END + XML_SHAPE_END;
    }   

    @Override
    public void visit(Triangle triangle) {
        int x = triangle.getX();
        int y = triangle.getY();

        this.representation = XML_SHAPE_OPEN + XML_TYPE_OPEN + "triangle" + XML_TYPE_END + XML_X_OPEN + x + XML_X_END + XML_Y_OPEN + y + XML_Y_END + XML_SHAPE_END;
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
