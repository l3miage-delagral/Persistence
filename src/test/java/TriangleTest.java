import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.Triangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.*;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TriangleTest {
    // Test JSON
    // test des fonctions Triangle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Triangle JSON")
    void testCircleJSON() {
        Triangle c = new Triangle(1, 2);
        JSonVisitor json = new JSonVisitor();;
        c.accept(json);
        String excpectedRes = "\t{\n\t\t\"type\": \"triangle\",\n\t\t\"x\": " + c.getX() + ",\n\t\t\"y\": " + c.getY() + "\n\t}";

        assertEquals(excpectedRes, json.getRepresentation());
    }

    // Test XML
    // test des fonctions Triangle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Triangle XML")
    void testCircleXML() {
        Triangle c = new Triangle(1, 2);
        XMLVisitor xml = new XMLVisitor();;
        c.accept(xml);
        String excpectedRes = "\t\t<shape>\n\t\t\t<type>triangle</type>\n\t\t\t<x>" + c.getX() + "</x>\n\t\t\t<y>" + c.getY() + "</y>\n\t\t</shape>";

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
        Triangle c = new Triangle(1, 2);
        c.draw(graphics);

        // verifier que graphics a bien executé setRenderingHint
        verify(graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // pareil pour setColor
        verify(graphics).setColor(Color.black);
    }
}
