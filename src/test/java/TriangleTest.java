import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.ShapeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TriangleTest {

    private final int X = 1;
    private final int Y = 2;

    private final int MX = X -25;
    private final int MY = Y -25;

    // Test JSON
    // test des fonctions Triangle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Triangle JSON")
    void testCircleJSON() {

        JSonVisitor json = new JSonVisitor();;
        ShapeFactory.getInstance().createTriangle(X, Y).accept(json);
        String excpectedRes = "\t{\n\t\t\"type\": \"triangle\",\n\t\t\"x\": " + MX + ",\n\t\t\"y\": " + MY + "\n\t}";

        assertEquals(excpectedRes, json.getRepresentation());
    }

    // Test XML
    // test des fonctions Triangle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Triangle XML")
    void testCircleXML() {

        XMLVisitor xml = new XMLVisitor();;
        ShapeFactory.getInstance().createTriangle(X, Y).accept(xml);
        String excpectedRes = "\t\t<shape>\n\t\t\t<type>triangle</type>\n\t\t\t<x>" + MX + "</x>\n\t\t\t<y>" + MY + "</y>\n\t\t</shape>";

        assertEquals(excpectedRes, xml.getRepresentation());
    }


    // test de draw()
    @Mock
    private Graphics2D graphics;

    // test de la fonction draw()
    @Test
    @DisplayName("Test triangle draw")
    void testTriangleDraw() {
        graphics = mock(Graphics2D.class);
        ShapeFactory.getInstance().createTriangle(X, Y).draw(graphics);

        // verifier que graphics a bien executé setRenderingHint
        verify(graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // pareil pour fill
        verify(graphics).fill(Mockito.any());
        // pareil pour setColor
        verify(graphics).setColor(Color.black);
        // pareil pour draw
        verify(graphics).draw(Mockito.any());
    }
}
