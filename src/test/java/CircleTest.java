import fr.uga.miage.m1.persistence.JSonVisitor;
import fr.uga.miage.m1.persistence.XMLVisitor;
import fr.uga.miage.m1.shapes.Circle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CircleTest {

    // Test JSON
    // test des fonctions Circle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Circle JSON")
    void testCircleJSON() {
        Circle c = new Circle(1, 2);
        JSonVisitor json = new JSonVisitor();;
        c.accept(json);
        String excpectedRes = "\t{\n\t\t\"type\": \"circle\",\n\t\t\"x\": " + c.getX() + ",\n\t\t\"y\": " + c.getY() + "\n\t}";

        assertEquals(excpectedRes, json.getRepresentation());
    }

    // Test XML
    // test des fonctions Circle(), getX(), getY(), accept()
    @Test
    @DisplayName("Test Circle XML")
    void testCircleXML() {
        Circle c = new Circle(1, 2);
        XMLVisitor xml = new XMLVisitor();;
        c.accept(xml);
        String excpectedRes = "\t\t<shape>\n\t\t\t<type>circle</type>\n\t\t\t<x>" + c.getX() + "</x>\n\t\t\t<y>" + c.getY() + "</y>\n\t\t</shape>";

        assertEquals(excpectedRes, xml.getRepresentation());
    }

    // test de draw

    @Mock
    private Graphics2D graphics;

    @Test
    @DisplayName("Test Circle draw")
    void testCircleDraw() {
        graphics = mock(Graphics2D.class);
        Circle c = new Circle(1, 2);
        c.draw(graphics);

        // verifier que graphics a bien executé setRenderingHint
        verify(graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // pareil pour fill
        verify(graphics).fill(new Ellipse2D.Double(c.getX(), c.getY(), 50, 50));
        // pareil pour setColor
        verify(graphics).setColor(Color.black);
        // pareil pour draw
        verify(graphics).draw(new Ellipse2D.Double(c.getX(), c.getY(), 50, 50));
    }

}
